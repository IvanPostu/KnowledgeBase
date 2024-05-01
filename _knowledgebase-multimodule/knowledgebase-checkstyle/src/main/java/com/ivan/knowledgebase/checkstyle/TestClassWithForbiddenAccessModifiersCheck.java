package com.ivan.knowledgebase.checkstyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestClassWithForbiddenAccessModifiersCheck extends AbstractCheck {
    private static final String TEST_ANNOTATION = "Test";

    private Set<String> forbiddenAccessModifiers = new HashSet<>();

    @Override
    public void beginTree(DetailAST rootAst) {
        if (forbiddenAccessModifiers.isEmpty()) {
            throw new IllegalStateException(
                "The 'forbiddenAccessModifiers' property must have at least one element");
        }
        super.beginTree(rootAst);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        String className = getName(ast);
        if (containsAtLeastOneTestAnnotation(ast)) {
            List<Error> errors = new LinkedList<>();

            validateClassAccessModifiers(ast).ifPresent(errors::add);
            errors.addAll(validateClassMembersAccessModifiers(ast));

            if (!errors.isEmpty()) {
                log(ast.getLineNo(), String.format("Class "
                    + className
                    + " contains forbidden access modifiers: %s",
                    errors.stream()
                        .map(error -> String.format("%s:%s:%s", error.getAccessModifier(),
                            error.getEntityType(), error.getEntityName()))
                        .collect(Collectors.joining(", ", "[", "]"))));
            }

        }
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    private List<Error> validateClassMembersAccessModifiers(DetailAST classDefAst) {
        List<Error> result = new LinkedList<>();
        DetailAST objBlockAst = classDefAst.findFirstToken(TokenTypes.OBJBLOCK);

        if (objBlockAst == null) {
            return Collections.unmodifiableList(result);
        }

        DetailAST memberAst = objBlockAst.getFirstChild();
        while (memberAst != null) {
            int memberType = memberAst.getType();
            if (memberType == TokenTypes.VARIABLE_DEF) {
                String modifierAsString = getMethodAccessModifier(memberAst);
                if (forbiddenAccessModifiers.contains(modifierAsString)) {
                    result.add(new Error(getName(memberAst), EntityType.PROPERTY, modifierAsString));
                }

            } else if (memberType == TokenTypes.METHOD_DEF) {
                String modifierAsString = getMethodAccessModifier(memberAst);
                if (forbiddenAccessModifiers.contains(modifierAsString)) {
                    result.add(new Error(getName(memberAst), EntityType.METHOD, modifierAsString));
                }

            }
            memberAst = memberAst.getNextSibling();
        }
        return Collections.unmodifiableList(result);
    }

    private Optional<Error> validateClassAccessModifiers(DetailAST classDefAst) {
        String className = getName(classDefAst);

        DetailAST classModifiersAst = classDefAst.findFirstToken(TokenTypes.MODIFIERS);
        if (classModifiersAst != null) {
            DetailAST modifier = classModifiersAst.getFirstChild();
            if (modifier != null) {
                if (forbiddenAccessModifiers.contains(modifier.getText())) {
                    return Optional.of(new Error(className, EntityType.CLASS, modifier.getText()));
                }
            }
        }

        return Optional.empty();
    }

    private boolean containsAtLeastOneTestAnnotation(DetailAST classDefAst) {
        DetailAST objBlock = classDefAst.findFirstToken(TokenTypes.OBJBLOCK);

        if (objBlock == null) {
            return false;
        }

        DetailAST methodDef = objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        while (methodDef != null) {
            DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers != null) {
                for (DetailAST modifier = modifiers.getFirstChild(); modifier != null; modifier =
                    modifier.getNextSibling()) {

                    DetailAST annotationIdent = modifier.findFirstToken(TokenTypes.IDENT);
                    if (annotationIdent == null) {
                        continue;
                    }

                    if (modifier.getType() == TokenTypes.ANNOTATION) {
                        String annotationName = annotationIdent.getText();
                        return TEST_ANNOTATION.equals(annotationName);
                    }

                }
            }
            methodDef = methodDef.getNextSibling();
        }
        return false;
    }

    public void setForbiddenAccessModifiers(String[] forbiddenAccessModifiers) {
        Objects.requireNonNull(forbiddenAccessModifiers);
        for (String forbiddenAccessModifier : forbiddenAccessModifiers) {
            this.forbiddenAccessModifiers.add(forbiddenAccessModifier);
        }
    }

    private String getName(DetailAST classDef) {
        DetailAST token = classDef.findFirstToken(TokenTypes.IDENT);
        return token == null ? null : classDef.findFirstToken(TokenTypes.IDENT).getText();
    }

    private String getMethodAccessModifier(DetailAST methodDefAst) {
        DetailAST modifiersAst = methodDefAst.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersAst != null) {
            DetailAST modifier = modifiersAst.getFirstChild();
            while (modifier != null) {
                if (modifier.getType() == TokenTypes.LITERAL_PUBLIC) {
                    return "public";
                } else if (modifier.getType() == TokenTypes.LITERAL_PRIVATE) {
                    return "private";
                } else if (modifier.getType() == TokenTypes.LITERAL_PROTECTED) {
                    return "protected";
                }
                modifier = modifier.getNextSibling();
            }
        }
        return "default";
    }

    private static final class Error {
        private final String entityName;
        private final EntityType entityType;
        private final String accessModifier;

        public Error(String entityName, EntityType entityType, String accessModifier) {
            this.entityName = entityName;
            this.entityType = entityType;
            this.accessModifier = accessModifier;
        }

        public String getAccessModifier() {
            return accessModifier;
        }

        public String getEntityName() {
            return entityName;
        }

        public EntityType getEntityType() {
            return entityType;
        }
    }

    private enum EntityType {
        CLASS, METHOD, PROPERTY
    }
}

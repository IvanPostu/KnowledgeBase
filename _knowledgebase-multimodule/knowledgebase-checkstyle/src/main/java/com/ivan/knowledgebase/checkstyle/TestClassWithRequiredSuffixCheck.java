package com.ivan.knowledgebase.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check which verifies if the class contains at least one method with @Test
 * annotation then the class name should be `[className]Test`
 */
public class TestClassWithRequiredSuffixCheck extends AbstractCheck {
    private static final String TEST_ANNOTATION = "Test";

    private String requiredSuffix;

    @Override
    // CHECKSTYLE.SUPPRESS: AbbreviationAsWordInName
    public void beginTree(DetailAST rootAST) {
        if (requiredSuffix == null || requiredSuffix.trim().isEmpty()) {
            throw new IllegalStateException("The 'requiredSuffix' property must be provided.");
        }
        super.beginTree(rootAST);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

    @Override
    public void visitToken(DetailAST ast) {
        String className = getName(ast);

        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            if (containsAtLeastOneTestAnnotation(objBlock)) {
                if (!className.endsWith(requiredSuffix)) {
                    log(ast.getLineNo(),
                            String.format("Class name " + className + " with @Test methods should be suffixed '*%s'",
                                    requiredSuffix));
                }
            }

        }
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

    private boolean containsAtLeastOneTestAnnotation(DetailAST objBlock) {
        DetailAST methodDef = objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        while (methodDef != null) {
            DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers != null) {
                for (DetailAST modifier = modifiers.getFirstChild(); modifier
                        != null; modifier = modifier.getNextSibling()) {

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

    public void setRequiredSuffix(String requiredSuffix) {
        this.requiredSuffix = requiredSuffix;
    }

    private String getName(DetailAST classDef) {
        DetailAST token = classDef.findFirstToken(TokenTypes.IDENT);
        return token == null ? null : classDef.findFirstToken(TokenTypes.IDENT).getText();
    }

}

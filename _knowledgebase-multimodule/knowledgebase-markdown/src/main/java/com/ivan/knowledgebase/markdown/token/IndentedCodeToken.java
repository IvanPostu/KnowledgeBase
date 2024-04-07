package com.ivan.knowledgebase.markdown.token;

public final class IndentedCodeToken extends AbstractToken {
    private final String sourceCode;

    public IndentedCodeToken(String rawValue, String sourceCode) {
        super(rawValue, TokenType.INDENTED_CODE);
        this.sourceCode = sourceCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

}

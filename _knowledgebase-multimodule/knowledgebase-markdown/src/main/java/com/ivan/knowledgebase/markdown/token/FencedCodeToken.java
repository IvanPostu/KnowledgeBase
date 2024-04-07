package com.ivan.knowledgebase.markdown.token;

import java.util.Optional;

public final class FencedCodeToken extends AbstractToken {
    private final String sourceCode;
    private final Optional<String> language;

    public FencedCodeToken(String rawValue, String sourceCode) {
        super(rawValue, TokenType.FENCED_CODE);
        this.sourceCode = sourceCode;
        this.language = Optional.empty();
    }

    public FencedCodeToken(String rawValue, String sourceCode, String language) {
        super(rawValue, TokenType.FENCED_CODE);
        this.sourceCode = sourceCode;
        this.language = Optional.of(language);
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public Optional<String> getLanguage() {
        return language;
    }

}

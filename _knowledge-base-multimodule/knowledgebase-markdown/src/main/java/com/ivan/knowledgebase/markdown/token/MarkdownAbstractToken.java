package com.ivan.knowledgebase.markdown.token;

abstract class MarkdownAbstractToken implements MarkdownToken {
    private final String rawValue;
    private final MarkdownTokenType markdownTokenType;

    MarkdownAbstractToken(String rawValue, MarkdownTokenType markdownTokenType) {
        this.rawValue = rawValue;
        this.markdownTokenType = markdownTokenType;
    }

    @Override
    public MarkdownTokenType getType() {
        return markdownTokenType;
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }
}

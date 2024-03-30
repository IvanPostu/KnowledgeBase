package com.ivan.knowledgebase.markdown.token;

import com.ivan.knowledgebase.shared.ToString;

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

    @Override
    public String toString() {
        return ToString.INSTANCE.create(this);
    }
}

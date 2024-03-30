package com.ivan.knowledgebase.markdown.token;

public final class SpaceToken extends MarkdownAbstractToken {

    public SpaceToken(String rawValue) {
        super(rawValue, MarkdownTokenType.SPACE);
    }
}

package com.ivan.knowledgebase.markdown.token;

public final class SpaceToken extends AbstractToken {

    public SpaceToken(String rawValue) {
        super(rawValue, TokenType.SPACE);
    }
}

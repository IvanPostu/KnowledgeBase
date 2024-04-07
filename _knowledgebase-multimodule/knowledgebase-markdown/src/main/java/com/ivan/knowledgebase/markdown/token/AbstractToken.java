package com.ivan.knowledgebase.markdown.token;

import com.ivan.knowledgebase.shared.ToString;

abstract class AbstractToken implements Token {
    private final String raw;
    private final TokenType type;

    AbstractToken(String rawValue, TokenType type) {
        this.raw = rawValue;
        this.type = type;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String getRaw() {
        return raw;
    }

    @Override
    public String toString() {
        return ToString.INSTANCE.create(this);
    }
}

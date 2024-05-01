package com.ivan.knowledgebase.markdown.token;

import java.util.Collections;
import java.util.List;

public final class TextToken extends AbstractToken implements ParentToken {
    private final String text;
    private final List<Token> childTokens;

    public TextToken(String rawValue, String text, List<Token> childTokens) {
        super(rawValue, TokenType.TEXT);
        this.text = text;
        this.childTokens = childTokens == null
            ? Collections.emptyList()
            : Collections.unmodifiableList(childTokens);
    }

    public String getText() {
        return text;
    }

    @Override
    public List<Token> getChildTokens() {
        return childTokens;
    }
}
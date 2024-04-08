package com.ivan.knowledgebase.markdown.token;

public final class EscapeToken extends AbstractToken {
    private final String text;

    public EscapeToken(String rawValue, String text) {
        super(rawValue, TokenType.ESCAPE);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

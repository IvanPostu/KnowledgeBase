package com.ivan.knowledgebase.markdown.token;

public final class HtmlToken extends AbstractToken {
    private final boolean block;
    private final boolean pre;
    private final String text;

    public HtmlToken(String rawValue, boolean block, boolean pre, String text) {
        super(rawValue, TokenType.HTML);
        this.block = block;
        this.pre = pre;
        this.text = text;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isPre() {
        return pre;
    }

    public String getText() {
        return text;
    }
}

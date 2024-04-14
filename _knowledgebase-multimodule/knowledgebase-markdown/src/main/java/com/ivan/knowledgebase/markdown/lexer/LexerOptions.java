package com.ivan.knowledgebase.markdown.lexer;

public final class LexerOptions {
    private final boolean pedantic;

    public LexerOptions(boolean pedantic) {
        this.pedantic = pedantic;
    }

    public boolean isPedantic() {
        return pedantic;
    }

}

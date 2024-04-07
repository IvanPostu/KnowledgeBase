package com.ivan.knowledgebase.markdown.token;

public final class ParagraphToken extends AbstractToken {

    public ParagraphToken(String rawValue) {
        super(rawValue, TokenType.PARAGRAPH);
    }
}

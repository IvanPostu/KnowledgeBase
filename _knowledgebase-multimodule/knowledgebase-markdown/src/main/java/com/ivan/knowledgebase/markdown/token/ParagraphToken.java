package com.ivan.knowledgebase.markdown.token;

public final class ParagraphToken extends MarkdownAbstractToken {

    public ParagraphToken(String rawValue) {
        super(rawValue, MarkdownTokenType.PARAGRAPH);
    }
}

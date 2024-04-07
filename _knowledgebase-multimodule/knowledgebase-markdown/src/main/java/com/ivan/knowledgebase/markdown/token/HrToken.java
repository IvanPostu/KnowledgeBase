package com.ivan.knowledgebase.markdown.token;

public final class HrToken extends MarkdownAbstractToken {

    public HrToken(String rawValue) {
        super(rawValue, MarkdownTokenType.HR);
    }
}

package com.ivan.knowledgebase.markdown.token;

public interface MarkdownToken {

    MarkdownTokenType getType();

    String rawValue();

}

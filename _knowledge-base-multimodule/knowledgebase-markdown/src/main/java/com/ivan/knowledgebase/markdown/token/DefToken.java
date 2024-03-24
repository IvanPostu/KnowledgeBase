package com.ivan.knowledgebase.markdown.token;

import java.util.Optional;

public final class DefToken implements MarkdownToken {
    private final String rawValue;
    private final String id;
    private final String link;
    private final Optional<String> title;

    public DefToken(String rawValue, String id, String link, Optional<String> title) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.rawValue = rawValue;
    }

    @Override
    public MarkdownTokenType getType() {
        return MarkdownTokenType.DEF;
    }

    @Override
    public String rawValue() {
        return rawValue;
    }

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public Optional<String> getTitle() {
        return title;
    }
}

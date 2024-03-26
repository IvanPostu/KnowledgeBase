package com.ivan.knowledgebase.markdown.token;

import java.util.Optional;

public final class DefToken implements MarkdownToken {
    private final String rawValue;
    private final String reference;
    private final String link;
    private final Optional<String> title;

    public DefToken(String rawValue, String reference, String link, Optional<String> title) {
        this.reference = reference;
        this.link = link;
        this.title = title;
        this.rawValue = rawValue;
    }

    @Override
    public MarkdownTokenType getType() {
        return MarkdownTokenType.DEF;
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    public String getReference() {
        return reference;
    }

    public String getLink() {
        return link;
    }

    public Optional<String> getTitle() {
        return title;
    }
}

package com.ivan.knowledgebase.markdown.token;

import java.util.Optional;

public final class DefToken extends AbstractToken {
    private final String reference;
    private final String link;
    private final Optional<String> title;

    public DefToken(String rawValue, String reference, String link, Optional<String> title) {
        super(rawValue, TokenType.DEF);
        this.reference = reference;
        this.link = link;
        this.title = title;
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

package com.ivan.knowledgebase.markdown.token;

import java.util.Collections;
import java.util.List;

public final class UrlLinkToken extends AbstractToken implements ParentToken {
    private final String text;
    private final String href;
    private final List<Token> childTokens;

    public UrlLinkToken(String rawValue, String text, String href, List<Token> childTokens) {
        super(rawValue, TokenType.URL_LINK);
        this.text = text;
        this.href = href;
        this.childTokens = childTokens == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(childTokens);
    }

    public String getText() {
        return text;
    }

    public String getHref() {
        return href;
    }

    @Override
    public List<Token> getChildTokens() {
        return childTokens;
    }
}

package com.ivan.knowledgebase.markdown.token;

import java.util.Collections;
import java.util.List;

public final class AutolinkToken extends AbstractToken implements ParentToken {
    private final String text;
    private final String href;
    private final List<Token> childTokens;

    public AutolinkToken(String rawValue, String text, String href, List<Token> childTokens) {
        super(rawValue, TokenType.AUTOLINK);
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

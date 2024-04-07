package com.ivan.knowledgebase.markdown.token;

import java.util.Collections;
import java.util.List;

public final class LineHeadingToken extends AbstractToken implements ParentToken {
    private final String text;
    private final List<Token> childTokens;
    private final int depth;

    public LineHeadingToken(String rawValue, String text, int depth, List<Token> childTokens) {
        super(rawValue, TokenType.HEADING);
        this.text = text;
        this.depth = depth;
        this.childTokens = childTokens == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(childTokens);
    }

    public String getText() {
        return text;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public List<Token> getChildTokens() {
        return childTokens;
    }
}

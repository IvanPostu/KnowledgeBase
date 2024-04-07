package com.ivan.knowledgebase.markdown.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ParagraphToken extends AbstractToken implements ParentToken {
    private final String text;
    private final List<Token> childTokens;

    public ParagraphToken(String rawValue, String text, List<Token> childTokens) {
        super(rawValue, TokenType.PARAGRAPH);
        this.text = text;
        this.childTokens = childTokens == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(childTokens));
    }

    public String getText() {
        return text;
    }

    @Override
    public List<Token> getChildTokens() {
        return childTokens;
    }
}

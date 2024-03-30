package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;

import com.ivan.knowledgebase.markdown.token.MarkdownToken;

public interface Tokenizer<T extends MarkdownToken> {
    
    Optional<T> resolveToken(String source);
    
}

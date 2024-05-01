package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;

import com.ivan.knowledgebase.markdown.token.Token;

public interface Tokenizer<T extends Token> {

    Optional<T> resolveToken(String source);

}

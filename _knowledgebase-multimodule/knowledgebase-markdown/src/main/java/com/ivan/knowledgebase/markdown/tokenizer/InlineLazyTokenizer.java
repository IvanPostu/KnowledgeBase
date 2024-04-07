package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.List;

import com.ivan.knowledgebase.markdown.token.Token;

@FunctionalInterface
public interface InlineLazyTokenizer {

    List<Token> inline(String source, List<Token> tokens);

}

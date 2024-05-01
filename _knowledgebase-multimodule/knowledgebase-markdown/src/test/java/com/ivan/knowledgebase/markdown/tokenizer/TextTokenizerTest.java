package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.token.TokenType;

class TextTokenizerTest {

    @Test
    void testParagraphTokenizerForValidInput() {
        List<Token> childTokens = Collections.emptyList();
        InlineLazyTokenizer mockInlineLazyTokenizer = (source, tokens) -> childTokens;
        Tokenizer<TextToken> tokenizer = new TextTokenizer(mockInlineLazyTokenizer);

        assertThat(tokenizer.resolveToken("Hello World!"))
            .get().satisfies(token -> {
                assertThat(token.getChildTokens()).isEqualTo(childTokens);
                assertThat(token.getText()).isEqualTo("Hello World!");
                assertThat(token.getRaw()).isEqualTo("Hello World!");
                assertThat(token.getType()).isEqualTo(TokenType.TEXT);
            });
    }
}

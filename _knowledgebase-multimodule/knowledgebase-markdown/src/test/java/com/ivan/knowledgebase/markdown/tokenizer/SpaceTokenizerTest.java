package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.TokenType;

class SpaceTokenizerTest {
    private final SpaceTokenizer tokenizer = new SpaceTokenizer();

    @Test
    void testNewlineSpaceToken() {
        assertThat(tokenizer.resolveToken("\n\n")).get().satisfies(token -> {
            assertThat(token.getRawValue()).isEqualTo("\n\n");
            assertThat(token.getType()).isEqualTo(TokenType.SPACE);
        });
    }
}

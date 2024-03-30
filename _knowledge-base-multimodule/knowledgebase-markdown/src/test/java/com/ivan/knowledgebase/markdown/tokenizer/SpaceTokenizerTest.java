package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.MarkdownTokenType;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

class SpaceTokenizerTest {
    @Test
    void testNewlineSpaceToken() {
        SpaceTokenizer spaceTokenizer = new SpaceTokenizer();
        Optional<SpaceToken> spaceToken = spaceTokenizer.resolveToken("\n\n");

        assertThat(spaceToken).get().satisfies(token -> {
            assertThat(token.getRawValue()).isEqualTo("\n\n");
            assertThat(token.getType()).isEqualTo(MarkdownTokenType.SPACE);
        });
    }
}

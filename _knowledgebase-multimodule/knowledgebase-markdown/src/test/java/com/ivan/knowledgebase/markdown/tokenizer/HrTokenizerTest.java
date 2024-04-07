package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.HrToken;
import com.ivan.knowledgebase.markdown.token.MarkdownTokenType;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

class HrTokenizerTest {
    private final HrTokenizer tokenizer = new HrTokenizer();

    @Test
    void testHrToken() {
        Optional<HrToken> resultToken = tokenizer.resolveToken("---");

        assertThat(resultToken).get().satisfies(token -> {
            assertThat(token.getRawValue()).isEqualTo("---");
            assertThat(token.getType()).isEqualTo(MarkdownTokenType.HR);
        });
    }
}

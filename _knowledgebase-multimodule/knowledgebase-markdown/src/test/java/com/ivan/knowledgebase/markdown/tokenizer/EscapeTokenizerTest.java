package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.TokenType;

class EscapeTokenizerTest {
    @Test
    void testResolveToken() {
        EscapeTokenizer tokenizer = new EscapeTokenizer();
        assertThat(tokenizer.resolveToken("\\>").get())
            .satisfies(value -> {
                assertThat(value.getType()).isEqualTo(TokenType.ESCAPE);
                assertThat(value.getRaw()).isEqualTo("\\>");
                assertThat(value.getText()).isEqualTo("&gt;");
            });
    }
}

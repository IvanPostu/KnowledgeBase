package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.token.TokenType;

class LineHeadingTokenizerTest {

    @Test
    void testHeadingTokenizerForValidInput() {
        assertLineHeadingTokenizerResult("lheading 1\n==========\n\n", "lheading 1", 1);
        assertLineHeadingTokenizerResult("lheading 2\n----------\n", "lheading 2", 2);
    }

    private void assertLineHeadingTokenizerResult(String input, String expectedText, int expectedDepth) {
        List<Token> childTokens = Collections.emptyList();
        InlineLazyTokenizer mockInlineLazyTokenizer = (source, tokens) -> childTokens;
        LineHeadingTokenizer tokenizer = new LineHeadingTokenizer(mockInlineLazyTokenizer);

        assertThat(tokenizer.resolveToken(input))
                .get().satisfies(token -> {
                    assertThat(token.getChildTokens()).isEqualTo(childTokens);
                    assertThat(token.getType()).isEqualTo(TokenType.HEADING);
                    assertThat(token.getText()).isEqualTo(expectedText);
                    assertThat(token.getDepth()).isEqualTo(expectedDepth);
                    assertThat(token.getRaw()).isEqualTo(input);
                });
    }
}

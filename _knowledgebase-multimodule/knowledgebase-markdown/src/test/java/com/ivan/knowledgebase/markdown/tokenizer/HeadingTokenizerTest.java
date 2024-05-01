package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.token.TokenType;

class HeadingTokenizerTest {

    @Test
    void testHeadingTokenizerForValidInput() {
        assertHeadingTokenizerResult("# heading 1\n\n", "heading 1", 1);
        assertHeadingTokenizerResult("## heading 2\n\n", "heading 2", 2);
        assertHeadingTokenizerResult("### heading 3\n\n", "heading 3", 3);
        assertHeadingTokenizerResult("#### heading 4\n\n", "heading 4", 4);
        assertHeadingTokenizerResult("##### heading 5\n\n", "heading 5", 5);
        assertHeadingTokenizerResult("###### heading 6\n\n", "heading 6", 6);
    }

    private void assertHeadingTokenizerResult(String input, String expectedText, int expectedDepth) {
        List<Token> childTokens = new ArrayList<>();
        InlineLazyTokenizer mockInlineLazyTokenizer = (source, tokens) -> childTokens;
        HeadingTokenizer tokenizer = new HeadingTokenizer(mockInlineLazyTokenizer, false);

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

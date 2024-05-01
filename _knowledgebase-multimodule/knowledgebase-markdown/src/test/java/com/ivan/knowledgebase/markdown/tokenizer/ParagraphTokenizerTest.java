package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.token.TokenType;

class ParagraphTokenizerTest {

    @Test
    void testParagraphTokenizerForValidInput() {
        List<Token> childTokens = new ArrayList<>();
        InlineLazyTokenizer mockInlineLazyTokenizer = (source, tokens) -> childTokens;
        ParagraphTokenizer tokenizer = new ParagraphTokenizer(mockInlineLazyTokenizer);

        assertThat(tokenizer.resolveToken("paragraph 1\n\nparagraph 2"))
            .get().satisfies(token -> {
                assertThat(token.getChildTokens()).isEqualTo(childTokens);
                assertThat(token.getText()).isEqualTo("paragraph 1");
                assertThat(token.getRaw()).isEqualTo("paragraph 1");
                assertThat(token.getType()).isEqualTo(TokenType.PARAGRAPH);
            });
    }
}

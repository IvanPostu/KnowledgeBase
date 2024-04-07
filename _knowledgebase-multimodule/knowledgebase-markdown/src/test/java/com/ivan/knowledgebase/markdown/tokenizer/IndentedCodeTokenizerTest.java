package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.FencedCodeToken;
import com.ivan.knowledgebase.markdown.token.IndentedCodeToken;

class IndentedCodeTokenizerTest {
    private final IndentedCodeTokenizer codeTokenizer = new IndentedCodeTokenizer(false);

    @Test
    void testResolveIndentedOnelineCode() {
        Optional<IndentedCodeToken> codeToken = codeTokenizer.resolveToken("    code");

        assertCodeToken(codeToken,
                "    code",
                "code");
    }

    @Test
    void testResolveIndentedMultilineCode() {
        Optional<IndentedCodeToken> codeToken = codeTokenizer
                .resolveToken("    Hello world\n    123123\n    End Block\n");

        assertCodeToken(codeToken,
                "    Hello world\n    123123\n    End Block\n",
                "Hello world\n123123\nEnd Block");
    }

    private void assertCodeToken(Optional<IndentedCodeToken> codeToken, String expectedRawValue,
            String expectedSourceCode) {

        assertThat(codeToken).get().satisfies(token -> {
            assertThat(token.getRawValue()).isEqualTo(expectedRawValue);
            assertThat(token.getSourceCode()).isEqualTo(expectedSourceCode);
        });
    }
}

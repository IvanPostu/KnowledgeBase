package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.CodeToken;
import com.ivan.knowledgebase.markdown.token.CodeToken.CodeType;

class IndentedCodeTokenizerTest {
    private final IndentedCodeTokenizer codeTokenizer = new IndentedCodeTokenizer(false);

    @Test
    void testResolveIndentedOnelineCode() {
        IndentedCodeTokenizer codeTokenizer = new IndentedCodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken("    code");

        assertCodeToken(codeToken,
                CodeType.INDENTED,
                "    code",
                "code",
                Optional.empty());
    }

    @Test
    void testResolveIndentedMultilineCode() {
        IndentedCodeTokenizer codeTokenizer = new IndentedCodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken("    Hello world\n    123123\n    End Block\n");

        assertCodeToken(codeToken,
                CodeType.INDENTED,
                "    Hello world\n    123123\n    End Block\n",
                "Hello world\n123123\nEnd Block",
                Optional.empty());
    }

    private void assertCodeToken(Optional<CodeToken> codeToken, CodeType expectedCodeType, String expectedRawValue,
            String expectedSourceCode, Optional<String> expectedLanguage) {

        assertThat(codeToken).get().satisfies(token -> {
            assertThat(token.getCodeType()).isEqualTo(expectedCodeType);
            assertThat(token.getRawValue()).isEqualTo(expectedRawValue);
            assertThat(token.getSourceCode()).isEqualTo(expectedSourceCode);
            assertThat(token.getLanguage()).isEqualTo(expectedLanguage);
        });
    }
}

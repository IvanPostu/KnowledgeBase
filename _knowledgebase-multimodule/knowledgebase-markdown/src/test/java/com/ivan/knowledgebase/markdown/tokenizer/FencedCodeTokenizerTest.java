package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.FencedCodeToken;

class FencedCodeTokenizerTest {
    private final FencedCodeTokenizer codeTokenizer = new FencedCodeTokenizer();

    @Test
    void testResolveFencedLangCode() {
        String inputCode = "```text\ncode\n```";
        Optional<FencedCodeToken> codeToken = codeTokenizer.resolveToken(inputCode);

        assertCodeToken(codeToken,
                inputCode,
                "code",
                Optional.of("text"));
    }

    @Test
    void testResolveFencedCode() {
        Optional<FencedCodeToken> codeToken = codeTokenizer.resolveToken(
                " ```\ncode\n```");

        assertCodeToken(codeToken,
                " ```\ncode\n```",
                "code",
                Optional.empty());
    }

    @Test
    void testResolveFencedMultilineWithIndentedCodeCompensationCode() {
        Optional<FencedCodeToken> codeToken = codeTokenizer.resolveToken(
                "   ```js\n       console.log(123)\n  alert(1);\n```");

        assertCodeToken(codeToken,
                "   ```js\n       console.log(123)\n  alert(1);\n```",
                "    console.log(123)\n  alert(1);",
                Optional.of("js"));
    }

    private void assertCodeToken(Optional<FencedCodeToken> codeToken, String expectedRawValue,
            String expectedSourceCode, Optional<String> expectedLanguage) {

        assertThat(codeToken).get().satisfies(token -> {
            assertThat(token.getRawValue()).isEqualTo(expectedRawValue);
            assertThat(token.getSourceCode()).isEqualTo(expectedSourceCode);
            assertThat(token.getLanguage()).isEqualTo(expectedLanguage);
        });
    }
}

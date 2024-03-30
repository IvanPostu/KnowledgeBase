package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.CodeToken;
import com.ivan.knowledgebase.markdown.token.CodeToken.CodeType;

class CodeTokenizerTest {
    @Test
    void testResolveFencedLangCode() {
        String inputCode = "```text\ncode\n```";
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken(inputCode);

        assertCodeToken(codeToken,
                CodeType.FENCED,
                inputCode,
                "code",
                Optional.of("text"));
    }

    @Test
    void testResolveFencedCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken(
                " ```\ncode\n```");

        assertCodeToken(codeToken,
                CodeType.FENCED,
                " ```\ncode\n```",
                "code",
                Optional.empty());
    }

    @Test
    void testResolveFencedMultilineWithIndentedCodeCompensationCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken(
                "   ```js\n       console.log(123)\n  alert(1);\n```");

        assertCodeToken(codeToken,
                CodeType.FENCED,
                "   ```js\n       console.log(123)\n  alert(1);\n```",
                "    console.log(123)\n  alert(1);",
                Optional.of("js"));
    }

    @Test
    void testResolveIndentedOnelineCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken("    code");

        assertCodeToken(codeToken,
                CodeType.INDENTED,
                "    code",
                "code",
                Optional.empty());
    }

    @Test
    void testResolveIndentedMultilineCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
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

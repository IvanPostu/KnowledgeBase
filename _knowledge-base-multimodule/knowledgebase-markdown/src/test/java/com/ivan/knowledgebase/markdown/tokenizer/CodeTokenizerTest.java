package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.CodeToken;
import com.ivan.knowledgebase.markdown.token.CodeToken.CodeType;

class CodeTokenizerTest {
    @Test
    void testResolveIndentedOnelineCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken("    code");

        assertThat(codeToken).get().satisfies(token -> {
            assertThat(token.getCodeType()).isEqualTo(CodeType.INDENTED);
            assertThat(token.getRawValue()).isEqualTo("    code");
            assertThat(token.getSourceCode()).isEqualTo("code");
            assertThat(token.getLanguage()).isEmpty();
        });
    }

    @Test
    void testResolveIndentedMultilineCode() {
        CodeTokenizer codeTokenizer = new CodeTokenizer(false);
        Optional<CodeToken> codeToken = codeTokenizer.resolveToken("    Hello world\n    123123\n    End Block\n");

        assertThat(codeToken).get().satisfies(token -> {
            assertThat(token.getCodeType()).isEqualTo(CodeType.INDENTED);
            assertThat(token.getRawValue()).isEqualTo("    Hello world\n    123123\n    End Block\n");
            assertThat(token.getSourceCode()).isEqualTo("Hello world\n123123\nEnd Block");
            assertThat(token.getLanguage()).isEmpty();
        });
    }
}

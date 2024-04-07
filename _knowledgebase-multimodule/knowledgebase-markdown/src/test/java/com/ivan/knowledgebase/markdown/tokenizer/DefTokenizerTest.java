package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.DefToken;

class DefTokenizerTest {
    @Test
    void testRegularCaseWithAdditionalStringAtTheEnd() {
        String source = "[link]: https://example.com \"title\"\n\nhello\nworld\n";
        assertIsValidDefToken(source, "[link]: https://example.com \"title\"\n\n", "link", "https://example.com",
                Optional.of("\"title\""));
    }

    @Test
    void testRegularCaseWithTitle() {
        String source = "[link]: https://example.com \"title1\"";
        assertIsValidDefToken(source, "[link]: https://example.com \"title1\"", "link", "https://example.com",
                Optional.of("\"title1\""));
    }

    @Test
    void testRegularCaseWithoutTitle() {
        String source = "[link]: https://example.com ";
        assertIsValidDefToken(source, "[link]: https://example.com ", "link", "https://example.com",
                Optional.empty());
    }

    @Test(dataProvider = "invalidDefTokensParameters")
    void testInvalidDefTokens(String source) {
        assertDefTokenIsMissing(source);
    }

    @DataProvider(name = "invalidDefTokensParameters")
    Object[][] invalidDefTokensParameters() {
        return new Object[][] {
            { "[link] https://example.com " },
            { "[link]: https://example.com \"title1" },
            { "qqq https://example.com " },
            { "[link]1: qqq " },
        };
    }

    void assertDefTokenIsMissing(String source) {
        DefTokenizer defTokenizer = new DefTokenizer();
        assertThat(defTokenizer.resolveToken(source)).isEmpty();
    }

    void assertIsValidDefToken(String source, String expectedRaw, String expectedReference, String expectedLink,
            Optional<String> expectedTitle) {

        DefTokenizer defTokenizer = new DefTokenizer();
        DefToken token = defTokenizer.resolveToken(source).orElseThrow(() -> new IllegalStateException());

        assertThat(token.getRaw()).isEqualTo(expectedRaw);
        assertThat(token.getReference()).isEqualTo(expectedReference);
        assertThat(token.getLink()).isEqualTo(expectedLink);
        assertThat(token.getTitle()).isEqualTo(expectedTitle);
    }
}

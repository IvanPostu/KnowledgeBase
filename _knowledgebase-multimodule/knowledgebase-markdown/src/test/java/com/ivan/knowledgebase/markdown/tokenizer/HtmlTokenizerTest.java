package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.HtmlToken;
import com.ivan.knowledgebase.markdown.token.TokenType;

class HtmlTokenizerTest {
    private final Tokenizer<HtmlToken> tokenizer = new HtmlTokenizer();

    @Test
    void testInputTextHtmlTagWithIdentationAtTheBeginning() {
        assertHtmlToken("   <input type=\"text\" />", false);
        assertHtmlToken(" <div>html</div>", false);
    }

    @Test
    void testInputTextHtmlTagWithoutIdentation() {
        assertHtmlToken("<input type=\"text\" />", false);
        assertHtmlToken("<div>html</div>", false);
    }

    @Test
    void testPreHtml() {
        assertHtmlToken("<script src=\"script.js\"></script>", true);
        assertHtmlToken("<pre>html</pre>", true);
    }

    @Test
    void testHtmlWithComment() {
        assertHtmlToken("<div>html</div> "
            + "<!-- <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> -->", false);
    }

    private void assertHtmlToken(String htmlInput, boolean expectedIsPre) {
        assertThat(tokenizer.resolveToken(htmlInput)).get().satisfies(token -> {
            assertThat(token.getRaw()).isEqualTo(htmlInput);
            assertThat(token.getType()).isEqualTo(TokenType.HTML);
            assertThat(token.isBlock()).isTrue();
            assertThat(token.isPre()).isEqualTo(expectedIsPre);
            assertThat(token.getText()).isEqualTo(htmlInput);
        });
    }
}

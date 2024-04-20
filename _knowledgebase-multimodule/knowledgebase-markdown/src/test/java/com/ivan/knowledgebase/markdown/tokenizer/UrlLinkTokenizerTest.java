package com.ivan.knowledgebase.markdown.tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.TokenType;
import com.ivan.knowledgebase.markdown.token.UrlLinkToken;

class UrlLinkTokenizerTest {
    private final UrlLinkTokenizer tokenizer = new UrlLinkTokenizer();

    @Test(dataProvider = "urlLinksWithExpectedTextAndHref")
    void testUrlLinks(String rawValue, String expectedText, String expectedHref) {
        UrlLinkToken token = tokenizer.resolveToken(rawValue).get();
        assertUrlLinkToken(token, rawValue, expectedText, expectedHref);
    }

    @DataProvider
    private Object[][] urlLinksWithExpectedTextAndHref() {
        return new Object[][] {
            { "https://www.example.com/fhqwhgads.",
                "https://www.example.com/fhqwhgads",
                "https://www.example.com/fhqwhgads" },
            { "http://foo.com))",
                "http://foo.com",
                "http://foo.com" },
            { "http://foo.com.))",
                "http://foo.com",
                "http://foo.com" },
            { "https://www.example.com?test=quote-in-\"-url",
                "https://www.example.com?test=quote-in-&quot;-url",
                "https://www.example.com?test=quote-in-\"-url" },
            { "https://www.example.com?test=quote-in-'-url",
                "https://www.example.com?test=quote-in-&#39;-url",
                "https://www.example.com?test=quote-in-'-url" },
            { "www.commonmark.org",
                "www.commonmark.org",
                "http://www.commonmark.org" },
            { "www.commonmark.org/a.b",
                "www.commonmark.org/a.b",
                "http://www.commonmark.org/a.b" },
            { "www.google.com/search?q=Markup+(business)",
                "www.google.com/search?q=Markup+(business)",
                "http://www.google.com/search?q=Markup+(business)" },
            { "www.google.com/search?q=Markup+(business)",
                "www.google.com/search?q=Markup+(business)",
                "http://www.google.com/search?q=Markup+(business)" },
            { "www.google.com/search?q=commonmark",
                "www.google.com/search?q=commonmark",
                "http://www.google.com/search?q=commonmark" },
            { "https://encrypted.google.com/search?q=Markup+(business)",
                "https://encrypted.google.com/search?q=Markup+(business)",
                "https://encrypted.google.com/search?q=Markup+(business)" },
            { "https://example.com",
                "https://example.com",
                "https://example.com" },
            { "test@example.com",
                "test@example.com",
                "mailto:test@example.com" },
        };
    }

    private void assertUrlLinkToken(UrlLinkToken token, String rawValue, String expectedText, String expectedHref) {
        assertThat(token.getRaw()).isEqualTo(rawValue);
        assertThat(token.getText()).isEqualTo(expectedText);
        assertThat(token.getType()).isEqualTo(TokenType.URL_LINK);
        assertThat(token.getHref()).isEqualTo(expectedHref);
        assertThat(token.getChildTokens())
                .usingRecursiveComparison()
                .isEqualTo(Collections.<TextToken>singletonList(
                        new TextToken(expectedText, expectedText, Collections.emptyList())));
    }
}

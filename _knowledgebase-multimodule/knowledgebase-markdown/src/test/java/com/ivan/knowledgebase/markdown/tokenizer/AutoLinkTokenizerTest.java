package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.AutolinkToken;
import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.TokenType;

class AutoLinkTokenizerTest {
    private final AutoLinkTokenizer autolinkTokenizer = new AutoLinkTokenizer();

    @Test
    void testAutolink() {
        String rawValue = "<https://example.com>";
        AutolinkToken token = autolinkTokenizer.resolveToken(rawValue).get();
        assertAutoLinkToken(token, rawValue, "https://example.com", "https://example.com");
    }

    @Test
    void testEmailAutolink() {
        String rawValue = "<test@example.com>";
        AutolinkToken token = autolinkTokenizer.resolveToken(rawValue).get();
        assertAutoLinkToken(token, rawValue, "test@example.com", "mailto:test@example.com");
    }

    private void assertAutoLinkToken(AutolinkToken token, String rawValue, String expectedText, String expectedHref) {
        Assertions.assertThat(token.getRaw()).isEqualTo(rawValue);
        Assertions.assertThat(token.getText()).isEqualTo(expectedText);
        Assertions.assertThat(token.getType()).isEqualTo(TokenType.AUTO_LINK);
        Assertions.assertThat(token.getHref()).isEqualTo(expectedHref);

        Assertions.assertThat(token.getChildTokens())
            .usingRecursiveComparison()
            .isEqualTo(Collections.<TextToken>singletonList(new TextToken(expectedText, expectedText, null)));
    }
}

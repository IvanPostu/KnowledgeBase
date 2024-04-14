package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.token.AutolinkToken;
import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.TokenType;

class AutolinkTokenizerTest {
    private final AutolinkTokenizer autolinkTokenizer = new AutolinkTokenizer();

    @Test
    void testAutolink() {
        String rawValue = "<https://example.com>";
        AutolinkToken token = autolinkTokenizer.resolveToken(rawValue).get();
        assertAutolinkToken(token, rawValue, "https://example.com", "https://example.com");
    }

    @Test
    void testEmailAutolink() {
        String rawValue = "<test@example.com>";
        AutolinkToken token = autolinkTokenizer.resolveToken(rawValue).get();
        assertAutolinkToken(token, rawValue, "test@example.com", "mailto:test@example.com");
    }

    private void assertAutolinkToken(AutolinkToken token, String rawValue, String expectedText, String expectedHref) {
        Assertions.assertThat(token.getRaw()).isEqualTo(rawValue);
        Assertions.assertThat(token.getText()).isEqualTo(expectedText);
        Assertions.assertThat(token.getType()).isEqualTo(TokenType.AUTOLINK);
        Assertions.assertThat(token.getHref()).isEqualTo(expectedHref);

        Assertions.assertThat(token.getChildTokens())
                .usingRecursiveComparison()
                .isEqualTo(Collections.<TextToken>singletonList(new TextToken(expectedText, expectedText, null)));
    }
}

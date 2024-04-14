package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.AutolinkToken;
import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.token.UrlLinkToken;
import com.ivan.knowledgebase.markdown.tokenizer.utils.EscapeUtils;

public final class UrlLinkTokenizer implements Tokenizer<UrlLinkToken> {
    private static final String URLLINK_REGEX = RegexBuilder
            .createFromTemplate(
                    "^((?:ftp|https?):\\/\\/|www\\.)"
                            + "(?:[a-zA-Z0-9\\-]+\\.?)+[^\\s<]*|^email")
            .replacePlaceholder("email", "[A-Za-z0-9._+-]+(@)[a-zA-Z0-9-_]+(?:\\.[a-zA-Z0-9-_]*[a-zA-Z0-9])+(?![-_])")
            .buildAsString();
    private static final Pattern AUTOLINK_PATTERN = Pattern.compile(URLLINK_REGEX);
    private static final Pattern BACKPEDAL = Pattern
            .compile("(?:[^?!.,:;*_'\"~()&]+|\\([^)]*\\)|&(?![a-zA-Z0-9]+;$)|[?!.,:;*_'\"~)]+(?!$))+");

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;
    private static final int GROUP_INDEX_2 = 2;

    @Override
    public Optional<UrlLinkToken> resolveToken(String source) {
        Matcher matcher = AUTOLINK_PATTERN.matcher(source);
        if (matcher.find()) {
            String text = matcher.group(GROUP_INDEX_0);
            String rawValue = matcher.group(GROUP_INDEX_0);
            String href;

            if ("@".equals(matcher.group(GROUP_INDEX_2))) {
                text = EscapeUtils.INSTANCE.escape(text, false);
                href = "mailto:" + text;
            } else {
                String validatedText = performUrlPathValidationAndGet(text);
                text = EscapeUtils.INSTANCE.escape(validatedText, false);

                if ("www.".equals(matcher.group(GROUP_INDEX_1))) {
                    href = "http://" + validatedText;
                } else {
                    href = validatedText;
                }
            }

            List<Token> childTokens = Collections
                    .singletonList(new TextToken(text, text, Collections.emptyList()));
            return Optional.of(new UrlLinkToken(rawValue, text, href, childTokens));
        }
        return Optional.empty();
    }

    private String performUrlPathValidationAndGet(String value) {
        Objects.requireNonNull(value);
        String prevValue = null;

        do {
            prevValue = value;
            Matcher matcher = BACKPEDAL.matcher(value);
            if (matcher.find()) {
                value = matcher.group(GROUP_INDEX_0) == null
                        ? ""
                        : matcher.group(GROUP_INDEX_0);
            }
        } while (!value.equals(prevValue));

        return value;
    }
}

package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.AutolinkToken;
import com.ivan.knowledgebase.markdown.token.TextToken;
import com.ivan.knowledgebase.markdown.token.Token;

public final class AutoLinkTokenizer implements Tokenizer<AutolinkToken> {
    private static final String AUTOLINK_REGEX = RegexBuilder
            .createFromTemplate(
                    "^<(scheme:[^\\s\\x00-\\x1f<>]*|email)>")
            .replacePlaceholder("email", "[a-zA-Z0-9.!#$%&'*+/=?_`{|}~-]"
                    + "+(@)[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])"
                    + "?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+(?![-_])")
            .replacePlaceholder("scheme", "[a-zA-Z][a-zA-Z0-9+.-]{1,31}")
            .buildAsString();
    private static final Pattern AUTOLINK_PATTERN = Pattern.compile(AUTOLINK_REGEX);

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;
    private static final int GROUP_INDEX_2 = 2;

    @Override
    public Optional<AutolinkToken> resolveToken(String source) {
        Matcher matcher = AUTOLINK_PATTERN.matcher(source);
        if (matcher.find()) {
            String text = matcher.group(GROUP_INDEX_1);
            String rawValue = matcher.group(GROUP_INDEX_0);

            String href;

            if ("@".equals(matcher.group(GROUP_INDEX_2))) {
                href = "mailto:" + text;
            } else {
                href = text;
            }
            List<Token> childTokens = Collections.singletonList(new TextToken(text, text, Collections.emptyList()));
            return Optional.of(new AutolinkToken(rawValue, text, href, childTokens));
        }
        return Optional.empty();
    }
}

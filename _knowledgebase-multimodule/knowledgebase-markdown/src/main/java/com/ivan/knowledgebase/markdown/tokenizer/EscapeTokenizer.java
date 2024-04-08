package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.EscapeToken;
import com.ivan.knowledgebase.markdown.tokenizer.utils.EscapeUtils;

public final class EscapeTokenizer implements Tokenizer<EscapeToken> {
    private static final String ESCAPE_REGEX = RegexBuilder
            .createFromTemplate("^\\\\([!\"#$%&'()*+,\\-./:;<=>?@\\[\\]\\\\^_`\\{|\\}~])")
            .buildAsString();
    private static final Pattern ESCAPE_PATTERN = Pattern.compile(ESCAPE_REGEX);

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;

    @Override
    public Optional<EscapeToken> resolveToken(String source) {
        Matcher matcher = ESCAPE_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(GROUP_INDEX_0);
            String text = matcher.group(GROUP_INDEX_1);
            String escapedText = EscapeUtils.INSTANCE.escape(text, false);
            return Optional.of(new EscapeToken(rawValue, escapedText));
        }
        return Optional.empty();
    }
}

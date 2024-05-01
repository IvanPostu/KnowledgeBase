package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

public final class SpaceTokenizer implements Tokenizer<SpaceToken> {
    private static final String SPACE_REGEX = RegexBuilder
        .createFromTemplate("^(?: *(?:\\n|$))+")
        .buildAsString();
    private static final Pattern SPACE_PATTERN = Pattern.compile(SPACE_REGEX);

    @Override
    public Optional<SpaceToken> resolveToken(String source) {
        Matcher matcher = SPACE_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            return Optional.of(new SpaceToken(rawValue));
        }
        return Optional.empty();
    }
}

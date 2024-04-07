package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.DefToken;
import com.ivan.knowledgebase.markdown.token.HrToken;
import com.ivan.knowledgebase.markdown.token.MarkdownToken;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

public final class HrTokenizer implements Tokenizer<HrToken> {
    private static final String HR_REGEX = RegexBuilder
            .createFromTemplate("^ {0,3}((?:-[\\t ]*){3,}|(?:_[ \\t]*){3,}|(?:\\*[ \\t]*){3,})(?:\\n+|$)")
            .buildAsString();
    private static final Pattern HR_PATTERN = Pattern.compile(HR_REGEX);

    @Override
    public Optional<HrToken> resolveToken(String source) {
        Matcher matcher = HR_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            return Optional.of(new HrToken(rawValue));
        }
        return Optional.empty();
    }
}

package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.ParagraphToken;
import com.ivan.knowledgebase.markdown.token.TextToken;

public final class TextTokenizer implements Tokenizer<TextToken> {
    private static final String TEXT_REGEX = RegexBuilder
        .createFromTemplate("^[^\\n]+")
        .buildAsString();
    private static final Pattern TEXT_PATTERN = Pattern.compile(TEXT_REGEX);

    private static final int GROUP_INDEX_0 = 0;

    private final InlineLazyTokenizer inlineLazyTokenizer;

    public TextTokenizer(InlineLazyTokenizer inlineLazyTokenizer) {
        this.inlineLazyTokenizer = inlineLazyTokenizer;
    }

    @Override
    public Optional<TextToken> resolveToken(String source) {
        Matcher matcher = TEXT_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(GROUP_INDEX_0);

            return Optional.of(new TextToken(rawValue, rawValue,
                inlineLazyTokenizer.inline(rawValue, new LinkedList<>())));
        }
        return Optional.empty();
    }
}

package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.HeadingToken;
import com.ivan.knowledgebase.markdown.tokenizer.utils.RTrimUtils;

public final class HeadingTokenizer implements Tokenizer<HeadingToken> {
    private static final String HEADING_REGEX = RegexBuilder
        .createFromTemplate("^ {0,3}(#{1,6})(?=\\s|$)(.*)(?:\\n+|$)")
        .buildAsString();
    private static final Pattern HEADING_PATTERN = Pattern.compile(HEADING_REGEX);

    private static final Predicate<String> MATCHES_HASH_AT_END = Pattern.compile("/#$").asPredicate();
    private static final Predicate<String> MATCHES_END_OF_LINE = Pattern.compile("/$").asPredicate();

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;
    private static final int GROUP_INDEX_2 = 2;

    private final boolean pedantic;
    private final InlineLazyTokenizer inlineLazyTokenizer;

    public HeadingTokenizer(InlineLazyTokenizer inlineLazyTokenizer, boolean pedantic) {
        this.inlineLazyTokenizer = inlineLazyTokenizer;
        this.pedantic = pedantic;
    }

    @Override
    public Optional<HeadingToken> resolveToken(String source) {
        Matcher matcher = HEADING_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(GROUP_INDEX_0);
            String firstGroupValue = matcher.group(GROUP_INDEX_1);
            String text = matcher.group(GROUP_INDEX_2).trim();

            if (MATCHES_HASH_AT_END.test(text)) {
                String trimmed = RTrimUtils.INSTANCE.rtrim(text, "#");
                if (this.pedantic) {
                    text = trimmed.trim();
                } else if ("".equals(trimmed) || MATCHES_END_OF_LINE.test(trimmed)) {
                    text = trimmed.trim();
                }
            }

            return Optional.of(new HeadingToken(rawValue, text, firstGroupValue.length(),
                inlineLazyTokenizer.inline(text, new LinkedList<>())));
        }
        return Optional.empty();
    }
}

package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.HeadingToken;

public final class LineHeadingTokenizer implements Tokenizer<HeadingToken> {
    private static final String BULLET = "(?:[*+-]|\\d{1,9}[.)])";
    private static final String HEADING_REGEX = RegexBuilder
        .createFromTemplate(
            "^(?!bullet |blockCode|fences|blockquote|heading|html)"
                + "((?:.|\\n(?!\\s*?\\n|bullet |blockCode|fences|blockquote|heading|html))+?)"
                + "\\n {0,3}(=+|-+) *(?:\\n+|$)")
        .replacePlaceholder("bullet", BULLET)
        .replacePlaceholder("blockCode", " {4}")
        .replacePlaceholder("fences", " {0,3}(?:`{3,}|~{3,})")
        .replacePlaceholder("blockquote", " {0,3}>")
        .replacePlaceholder("heading", " {0,3}#{1,6}")
        .replacePlaceholder("html", " {0,3}<[^\\n>]+>\\n")
        .buildAsString();
    private static final Pattern HEADING_PATTERN = Pattern.compile(HEADING_REGEX);

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;
    private static final int GROUP_INDEX_2 = 2;

    private final InlineLazyTokenizer inlineLazyTokenizer;

    public LineHeadingTokenizer(InlineLazyTokenizer inlineLazyTokenizer) {
        this.inlineLazyTokenizer = inlineLazyTokenizer;
    }

    public static void main(String[] args) {
        System.out.println(HEADING_REGEX.equals(HEADING_REGEX));
    }

    @Override
    public Optional<HeadingToken> resolveToken(String source) {
        Matcher matcher = HEADING_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(GROUP_INDEX_0);
            String text = matcher.group(GROUP_INDEX_1);
            int depth = matcher.group(GROUP_INDEX_2).charAt(0) == '=' ? 1 : 2;

            return Optional.of(new HeadingToken(rawValue, text, depth,
                inlineLazyTokenizer.inline(text, new LinkedList<>())));
        }
        return Optional.empty();
    }
}

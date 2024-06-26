package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.ParagraphToken;

public final class ParagraphTokenizer implements Tokenizer<ParagraphToken> {
    private static final String PARAGRAPH_REGEX = RegexBuilder
        .createFromTemplate(
            "^([^\\n]+(?:\\n(?!hr|heading|lheading|blockquote|fences|list|html|table| +\\n)[^\\n]+)*)")
        .buildAsString();
    private static final Pattern PARAGRAPH_PATTERN = Pattern.compile(PARAGRAPH_REGEX);

    private static final int GROUP_INDEX_0 = 0;
    private static final int GROUP_INDEX_1 = 1;

    private final InlineLazyTokenizer inlineLazyTokenizer;

    public ParagraphTokenizer(InlineLazyTokenizer inlineLazyTokenizer) {
        this.inlineLazyTokenizer = inlineLazyTokenizer;
    }

    @Override
    public Optional<ParagraphToken> resolveToken(String source) {
        Matcher matcher = PARAGRAPH_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(GROUP_INDEX_0);
            String matchedValue = matcher.group(GROUP_INDEX_1);

            String text = matchedValue;
            if (matchedValue.charAt(matchedValue.length() - 1) == '\n') {
                text = new StringBuilder(text)
                    .replace(matchedValue.length() - 1, matchedValue.length(), "")
                    .toString();
            }

            return Optional.of(new ParagraphToken(rawValue, text,
                inlineLazyTokenizer.inline(text, new LinkedList<>())));
        }
        return Optional.empty();
    }
}

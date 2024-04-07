package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.DefToken;

public final class DefTokenizer implements Tokenizer<DefToken> {
    private static final int REFERENCE_INDEX = 1;
    private static final int LINK_INDEX = 2;
    private static final int TITLE_INDEX = 3;
    private static final String DEF_REGEX = RegexBuilder
            .createFromTemplate("^ {0,3}\\[(label)\\]: *(?:\\n *)"
                    + "?([^<\\s][^\\s]*|<.*?>)"
                    + "(?:(?: +(?:\\n *)?| *\\n *)(title))? *(?:\\n+|$)")
            .replacePlaceholder("label", "(?!\\s*\\])(?:\\\\.|[^\\[\\]\\\\])+")
            .replacePlaceholder("title", "(?:\"(?:\\\\\"?|[^\"\\\\])*\"|'[^'\\n]*"
                    + "(?:\\n[^'\\n]+)*\\n?'|\\([^()]*\\))")
            .buildAsString();
    private static final Pattern DEF_PATTERN = Pattern.compile(DEF_REGEX);

    @Override
    public Optional<DefToken> resolveToken(String source) {
        Matcher matcher = DEF_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            String reference = matcher.group(REFERENCE_INDEX);
            String link = matcher.group(LINK_INDEX);
            String title = matcher.group(TITLE_INDEX);
            return Optional.of(new DefToken(rawValue, reference, link, Optional.ofNullable(title)));
        }
        return Optional.empty();
    }
}

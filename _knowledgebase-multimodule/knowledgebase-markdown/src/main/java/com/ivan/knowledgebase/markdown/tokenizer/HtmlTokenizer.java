package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.HtmlToken;

public final class HtmlTokenizer implements Tokenizer<HtmlToken> {
    private static final String TAG = "address|article|aside|base|basefont|blockquote|body|caption|center|col"
            + "|colgroup|dd|details|dialog|dir|div|dl|dt|fieldset|figcaption|figure|footer|form|frame|frameset"
            + "|h[1-6]|head|header|hr|html|iframe|legend|li|link|main|menu|menuitem|meta|nav|noframes|ol"
            + "|optgroup|option|p|param|search|section|summary|table|tbody|td|tfoot|th|thead|title|tr|track|ul";
    private static final String COMMENT = "<!--(?:-?>|[\\\\s\\\\S]*?(?:-->|$))";
    private static final String ATTRIBUTE = " *= *\\\"[^\\\"\\\\n]*\\\"| *= *'[^'\\\\n]*'| *= *[^\\\\s\\\"'=<>`]+)?";
    private static final String HTML_REGEX = RegexBuilder
            .createFromTemplate(
                    "^ {0,3}(?:"
                            + "<(script|pre|style|textarea)[\\s>][\\s\\S]*?(?:<\\/\\1>[^\\n]*\\n+|$)"
                            + "|comment[^\\n]*(\\n+|$)"
                            + "|<\\?[\\s\\S]*?(?:\\?>\\n*|$)"
                            + "|<![A-Z][\\s\\S]*?(?:>\\n*|$)"
                            + "|<!\\[CDATA\\[[\\s\\S]*?(?:\\]\\]>\\n*|$)"
                            + "|<\\/?(tag)(?: +|\\n|\\/?>)[\\s\\S]*?(?:(?:\\n *)+\\n|$)"
                            + "|<(?!script|pre|style|textarea)([a-z][\\w-]*)"
                            + "(?: +[a-zA-Z:_][\\w.:-]*(?:attribute)*? *\\/?>(?=[ \\t]*(?:\\n|$))"
                            + "[\\s\\S]*?(?:(?:\\n *)+\\n|$)"
                            + "|<\\/(?!script|pre|style|textarea)[a-z][\\w-]*\\s*>(?=[ \\t]*(?:\\n|$))"
                            + "[\\s\\S]*?(?:(?:\\n *)+\\n|$))")
            .replacePlaceholder("tag", TAG)
            .replacePlaceholder("comment", COMMENT)
            .replacePlaceholder("attribute", ATTRIBUTE)
            .buildAsString();
    private static final Pattern HTML_PATTERN = Pattern.compile(HTML_REGEX);

    private static final int GROUP_INDEX_1 = 1;

    @Override
    public Optional<HtmlToken> resolveToken(String source) {
        Matcher matcher = HTML_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            String preText = Objects.requireNonNullElse(matcher.group(GROUP_INDEX_1), "");

            boolean isPre = preText.equals("pre") || preText.equals("script") || preText.equals("style");
            return Optional.of(new HtmlToken(rawValue, true, isPre, rawValue));
        }
        return Optional.empty();
    }
}

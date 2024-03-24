package com.ivan.knowledgebase.markdown.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static final String DEF_REGEX = "^ {0,3}\\[((?!\\s*\\])(?:\\\\.|"
            + "[^\\[\\]\\\\])+)\\]: *(?:\\n *)?([^<\\s][^\\s]*|<.*?>)"
            + "(?:(?: +(?:\\n *)?| *\\n *)((?:\"(?:\\\\\"?|[^\"\\\\])*\"|"
            + "'[^'\\n]*(?:\\n[^'\\n]+)*\\n?'|\\([^()]*\\))))? *(?:\\n+|$)";
    private static final Pattern DEF_PATTERN = Pattern.compile(DEF_REGEX);

    public static MarkdownToken resolveToken(String plainMarkdown) {
        Matcher matcher = DEF_PATTERN.matcher(plainMarkdown);

        if (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        resolveToken("[link]: https://example.com \"title\"\n\n\n");
    }
}

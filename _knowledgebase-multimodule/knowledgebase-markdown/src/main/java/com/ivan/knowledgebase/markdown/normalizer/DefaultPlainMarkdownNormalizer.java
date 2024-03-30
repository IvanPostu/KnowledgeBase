package com.ivan.knowledgebase.markdown.normalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DefaultPlainMarkdownNormalizer extends PlainMarkdownNormalizer {
    private static final Pattern NORMALIZE_PATTERN = Pattern.compile("^( *)(\t+)", Pattern.MULTILINE);

    @Override
    public String normalizeTabsAndWhitespaces(String plainMarkdown) {

        Matcher matcher = NORMALIZE_PATTERN.matcher(plainMarkdown);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String leading = matcher.group(1);
            int tabsLength = matcher.group(2).length();
            StringBuilder replacement = new StringBuilder();
            for (int i = 0; i < tabsLength; i++) {
                replacement.append("    ");
            }
            matcher.appendReplacement(result, leading + replacement.toString());
        }
        matcher.appendTail(result);

        return result.toString();
    }

}

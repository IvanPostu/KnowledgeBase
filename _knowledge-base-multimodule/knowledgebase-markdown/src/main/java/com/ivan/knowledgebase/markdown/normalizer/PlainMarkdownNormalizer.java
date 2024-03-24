package com.ivan.knowledgebase.markdown.normalizer;

public abstract class PlainMarkdownNormalizer {

    public abstract String normalizeTabsAndWhitespaces(String plainMarkdown);

    public String normalizeEndLines(String plainMarkdown) {
        return plainMarkdown.replaceAll("\r\n|\r", "\n");
    }
}

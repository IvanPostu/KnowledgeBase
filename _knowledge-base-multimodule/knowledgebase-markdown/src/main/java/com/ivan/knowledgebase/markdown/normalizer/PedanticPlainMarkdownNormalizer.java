package com.ivan.knowledgebase.markdown.normalizer;

final class PedanticPlainMarkdownNormalizer extends PlainMarkdownNormalizer {

    @Override
    public String normalizeTabsAndWhitespaces(String plainMarkdown) {
        return plainMarkdown.replaceAll("\t", "    ").replaceAll("(?m)^ +$", "");
    }

}

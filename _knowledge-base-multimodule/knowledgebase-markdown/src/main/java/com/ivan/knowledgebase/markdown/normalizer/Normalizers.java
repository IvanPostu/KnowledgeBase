package com.ivan.knowledgebase.markdown.normalizer;

public enum Normalizers {
    INSTANCE;

    public PlainMarkdownNormalizer create(boolean pedantic) {
        if (pedantic) {
            return new PedanticPlainMarkdownNormalizer();
        } else {
            return new DefaultPlainMarkdownNormalizer();
        }
    }
}

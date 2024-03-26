package com.ivan.knowledgebase.markdown.normalizer;

public enum Normalizers {
    INSTANCE;

    public PlainMarkdownNormalizer create(NormalizerType type) {
        if (type == NormalizerType.PEDANTIC) {
            return new PedanticPlainMarkdownNormalizer();
        }
        if (type == NormalizerType.DEFAULT) {
            return new DefaultPlainMarkdownNormalizer();
        }
        throw new IllegalStateException("Could not find normalizer for the type " + type);
    }

}

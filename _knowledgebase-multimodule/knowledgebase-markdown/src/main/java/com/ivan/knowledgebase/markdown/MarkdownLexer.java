package com.ivan.knowledgebase.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.normalizer.NormalizerType;
import com.ivan.knowledgebase.markdown.normalizer.Normalizers;
import com.ivan.knowledgebase.markdown.normalizer.PlainMarkdownNormalizer;
import com.ivan.knowledgebase.markdown.token.Token;

public final class MarkdownLexer {
    private final PlainMarkdownNormalizer plainMarkdownNormalizer;

    private MarkdownLexer(boolean pedantic) {
        this.plainMarkdownNormalizer = Normalizers.INSTANCE.create(NormalizerType.PEDANTIC);
    }

    public static MarkdownLexer getInstance(boolean pedantic) {
        return new MarkdownLexer(pedantic);
    }

    public List<Token> lex(String plainMarkdown) {
        plainMarkdown = plainMarkdownNormalizer.normalizeEndLines(plainMarkdown);
        plainMarkdown = plainMarkdownNormalizer.normalizeTabsAndWhitespaces(plainMarkdown);

        return new ArrayList<>();
    }

}

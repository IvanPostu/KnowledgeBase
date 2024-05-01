package com.ivan.knowledgebase.markdown.lexer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.ivan.knowledgebase.markdown.normalizer.NormalizerType;
import com.ivan.knowledgebase.markdown.normalizer.Normalizers;
import com.ivan.knowledgebase.markdown.normalizer.PlainMarkdownNormalizer;
import com.ivan.knowledgebase.markdown.token.Token;
import com.ivan.knowledgebase.markdown.tokenizer.InlineLazyTokenizer;
import com.ivan.knowledgebase.markdown.tokenizer.ParagraphTokenizer;
import com.ivan.knowledgebase.markdown.tokenizer.Tokenizer;

public enum Lexer {
    INSTANCE;

    private static final PlainMarkdownNormalizer DEFAULT_NORMALIZER = Normalizers.INSTANCE
        .create(NormalizerType.DEFAULT);
    private static final PlainMarkdownNormalizer PEDANTIC_NORMALIZER = Normalizers.INSTANCE
        .create(NormalizerType.PEDANTIC);

    public List<Token> lex(String sourceMarkdown, LexerOptions lexerOptions) {
        String changeableSource = normalize(sourceMarkdown, lexerOptions);

        List<String> inlineQueue = new LinkedList<String>();
        InlineLazyTokenizer inlineLazyTokenizer = (String source, List<Token> tokens) -> {
            inlineQueue.add(source);
            return tokens;
        };

        List<? extends Tokenizer<? extends Token>> tokenizers = initializeTokenizers(lexerOptions,
            inlineLazyTokenizer);
        List<Token> result = new LinkedList<>();

        return null;
    }

    private List<? extends Tokenizer<? extends Token>> initializeTokenizers(LexerOptions lexerOptions,
        InlineLazyTokenizer inlineLazyTokenizer) {
        List<Tokenizer<? extends Token>> tokenizers = new LinkedList<>();
        tokenizers.add(new ParagraphTokenizer(inlineLazyTokenizer));

        return Collections.unmodifiableList(tokenizers);
    }

    private String normalize(String source, LexerOptions lexerOptions) {
        PlainMarkdownNormalizer normalizer = lexerOptions.isPedantic()
            ? PEDANTIC_NORMALIZER
            : DEFAULT_NORMALIZER;
        source = normalizer.normalizeTabsAndWhitespaces(source);
        source = normalizer.normalizeEndLines(source);

        return source;
    }
}

package com.ivan.knowledgebase.markdown.token;

import java.util.Optional;

public final class CodeToken extends MarkdownAbstractToken {
    private final String sourceCode;
    private final CodeType codeType;
    private final Optional<String> language;

    public CodeToken(String rawValue, String sourceCode, CodeType codeType, String language) {
        super(rawValue, MarkdownTokenType.CODE);
        this.sourceCode = sourceCode;
        this.codeType = codeType;
        this.language = Optional.of(language);
    }

    public CodeToken(String rawValue, String sourceCode, CodeType codeType) {
        super(rawValue, MarkdownTokenType.CODE);
        this.sourceCode = sourceCode;
        this.codeType = codeType;
        this.language = Optional.empty();
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public Optional<String> getLanguage() {
        return language;
    }

    public enum CodeType {
        INDENTED, FENCED;
    }
}

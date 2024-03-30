package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.CodeToken;
import com.ivan.knowledgebase.markdown.token.CodeToken.CodeType;
import com.ivan.knowledgebase.markdown.token.DefToken;
import com.ivan.knowledgebase.markdown.token.MarkdownToken;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

public final class CodeTokenizer implements Tokenizer<CodeToken> {
    private static final String INLINE_CODE_REGEX = RegexBuilder
            .createFromTemplate("^( {4}[^\\n]+(?:\\n(?: *(?:\\n|$))*)?)+")
            .buildAsString();
    private static final Pattern INLINE_CODE_PATTERN = Pattern.compile(INLINE_CODE_REGEX);

    private final boolean pedantic;

    public CodeTokenizer(boolean pedantic) {
        this.pedantic = pedantic;
    }

    @Override
    public Optional<CodeToken> resolveToken(String source) {
        Optional<CodeToken> token = resolveInlineCodeToken(source);
        if (token.isPresent()) {
            return token;
        }

        return Optional.empty();
    }

    private Optional<CodeToken> resolveInlineCodeToken(String source) {
        Matcher matcher = INLINE_CODE_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            String sourceCode = rawValue.replaceAll("(?m)^ {1,4}", "");

            sourceCode = pedantic
                    ? sourceCode
                    : RTrimUtils.INSTANCE.rtrim(sourceCode, "\n");

            CodeToken codeToken = new CodeToken(rawValue, sourceCode, CodeType.INDENTED);
            return Optional.of(codeToken);
        }
        return Optional.empty();
    }

}

package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.IndentedCodeToken;

public final class IndentedCodeTokenizer implements Tokenizer<IndentedCodeToken> {
    private static final String INDENTED_CODE_REGEX = RegexBuilder
            .createFromTemplate("^( {4}[^\\n]+(?:\\n(?: *(?:\\n|$))*)?)+")
            .buildAsString();
    private static final Pattern INDENTED_CODE_PATTERN = Pattern.compile(INDENTED_CODE_REGEX);

    private final boolean pedantic;

    public IndentedCodeTokenizer(boolean pedantic) {
        this.pedantic = pedantic;
    }

    @Override
    public Optional<IndentedCodeToken> resolveToken(String source) {
        return resolveIndentedCodeToken(source);
    }

    private Optional<IndentedCodeToken> resolveIndentedCodeToken(String source) {
        Matcher matcher = INDENTED_CODE_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            String sourceCode = rawValue.replaceAll("(?m)^ {1,4}", "");

            sourceCode = pedantic
                    ? sourceCode
                    : RTrimUtils.INSTANCE.rtrim(sourceCode, "\n");

            IndentedCodeToken codeToken = new IndentedCodeToken(rawValue, sourceCode);
            return Optional.of(codeToken);
        }
        return Optional.empty();
    }

}

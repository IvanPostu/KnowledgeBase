package com.ivan.knowledgebase.markdown.tokenizer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ivan.knowledgebase.markdown.RegexBuilder;
import com.ivan.knowledgebase.markdown.token.CodeToken;
import com.ivan.knowledgebase.markdown.token.CodeToken.CodeType;
import com.ivan.knowledgebase.markdown.token.DefToken;
import com.ivan.knowledgebase.markdown.token.MarkdownToken;
import com.ivan.knowledgebase.markdown.token.SpaceToken;

public final class CodeTokenizer implements Tokenizer<CodeToken> {
    private static final String INDENTED_CODE_REGEX = RegexBuilder
            .createFromTemplate("^( {4}[^\\n]+(?:\\n(?: *(?:\\n|$))*)?)+")
            .buildAsString();
    private static final Pattern INDENTED_CODE_PATTERN = Pattern.compile(INDENTED_CODE_REGEX);
    private static final String FENCED_CODE_REGEX = RegexBuilder
            .createFromTemplate("^ {0,3}(`{3,}(?=[^`\\n]*(?:\\n|$))|~{3,})"
                    + "([^\\n]*)(?:\\n|$)(?:|([\\s\\S]*?)(?:\\n|$))(?: {0,3}\\1[~`]* *(?=\\n|$)|$)")
            .buildAsString();
    private static final Pattern FENCED_CODE_PATTERN = Pattern.compile(FENCED_CODE_REGEX);
    private static final Pattern INDENT_TO_CODE = Pattern.compile("^(\\s+)(?:```)");
    private static final Pattern SPACES_AT_THE_BEGINING_OF_LINE = Pattern.compile("^\\s+");
    private static final String ESCAPED_PUNCTUATION_AND_SYMBOLS = "\\\\([!\"#$%&'()*+,./:;<=>?@\\[\\]\\^_`{|}~-])";

    private static final int GROUP_INDEX_1 = 1;
    private static final int GROUP_INDEX_2 = 2;
    private static final int GROUP_INDEX_3 = 3;

    private final boolean pedantic;

    public CodeTokenizer(boolean pedantic) {
        this.pedantic = pedantic;
    }

    @Override
    public Optional<CodeToken> resolveToken(String source) {
        Optional<CodeToken> token;
        token = resolveIndentedCodeToken(source);
        if (token.isPresent()) {
            return token;
        }
        token = resolveFencedCodeToken(source);
        if (token.isPresent()) {
            return token;
        }

        return Optional.empty();
    }

    private Optional<CodeToken> resolveFencedCodeToken(String source) {
        Matcher matcher = FENCED_CODE_PATTERN.matcher(source);
        if (matcher.find()) {
            String rawValue = matcher.group(0);
            String code = indentCodeCompensation(rawValue, matcher.group(GROUP_INDEX_3));
            Optional<String> language = tryToGetLanguage(matcher.group(GROUP_INDEX_2))
                    .map(this::replaceAnyPunctuationIfNeeded);

            if (language.isPresent()) {
                return Optional.of(new CodeToken(rawValue, code, CodeType.FENCED, language.get()));
            } else {
                return Optional.of(new CodeToken(rawValue, code, CodeType.FENCED));
            }

        }
        return Optional.empty();
    }

    private Optional<CodeToken> resolveIndentedCodeToken(String source) {
        Matcher matcher = INDENTED_CODE_PATTERN.matcher(source);
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

    private static String indentCodeCompensation(String raw, String code) {
        if (code == null) {
            code = "";
        }

        Matcher indentToCodeMatcher = INDENT_TO_CODE.matcher(raw);
        if (indentToCodeMatcher.find()) {
            String indentToCode = indentToCodeMatcher.group(GROUP_INDEX_1);
            return Stream.of(code.split("\n"))
                    .map(node -> {
                        Matcher spacesMatcher = SPACES_AT_THE_BEGINING_OF_LINE.matcher(node);
                        if (spacesMatcher.find()) {
                            String indentInNode = spacesMatcher.group(0);
                            if (indentInNode.length() >= indentToCode.length()) {
                                return node.substring(indentToCode.length());
                            }
                        }
                        return node;
                    })
                    .collect(Collectors.joining("\n"));
        }

        return code;
    }

    private String replaceAnyPunctuationIfNeeded(String value) {
        if (value == null || value.equals("")) {
            return value;
        }

        return value.trim().replaceAll(ESCAPED_PUNCTUATION_AND_SYMBOLS, "$1");
    }

    private Optional<String> tryToGetLanguage(String value) {
        if (value == null || value.equals("")) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
}

package com.ivan.knowledgebase.markdown.tokenizer.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EscapeUtils {
    INSTANCE;

    private static final Pattern ESCAPE = Pattern.compile("[&<>\"']");
    private static final Pattern ESCAPE_NO_ENCODE = Pattern
            .compile("[<>\"']|&(?!(#\\d{1,7}|#[Xx][a-fA-F0-9]{1,6}|\\w+);)");
    private static final Map<Character, String> ESCAPE_REPLACEMENT = initializeEscapeReplacements();

    public String escape(String text, boolean encode) {
        if (encode) {
            Matcher matcher = ESCAPE.matcher(text);
            StringBuffer stringBuilder = new StringBuffer();
            while (matcher.find()) {
                String replacement = ESCAPE_REPLACEMENT.get(matcher.group(0).charAt(0));
                matcher.appendReplacement(stringBuilder, replacement);
            }
            matcher.appendTail(stringBuilder);

            return stringBuilder.toString();
        } else {
            Matcher matcher = ESCAPE_NO_ENCODE.matcher(text);
            StringBuffer stringBuilder = new StringBuffer();
            while (matcher.find()) {
                String replacement = ESCAPE_REPLACEMENT.get(matcher.group(0).charAt(0));
                matcher.appendReplacement(stringBuilder, replacement);
            }
            matcher.appendTail(stringBuilder);

            return stringBuilder.toString();
        }
    }

    private static Map<Character, String> initializeEscapeReplacements() {
        Map<Character, String> replacements = new HashMap<>();
        replacements.put(Character.valueOf('&'), "&amp;");
        replacements.put(Character.valueOf('<'), "&lt;");
        replacements.put(Character.valueOf('>'), "&gt;");
        replacements.put(Character.valueOf('\"'), "&quot;");
        replacements.put(Character.valueOf('\''), "&#39;");
        return Collections.unmodifiableMap(replacements);
    }
}

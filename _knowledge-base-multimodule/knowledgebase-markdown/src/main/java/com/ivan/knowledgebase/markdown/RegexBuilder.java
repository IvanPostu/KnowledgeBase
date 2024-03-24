package com.ivan.knowledgebase.markdown;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexBuilder {
    private static final String CARRET = "(^|[^\\[])\\^";
    private String currentPattern;

    private RegexBuilder(String template) {
        this.currentPattern = template;
    }

    public static RegexBuilder createFromTemplate(String template) {
        return new RegexBuilder(template);
    }

    public RegexBuilder replacePlaceholder(String name, String value) {
        value = value.replaceAll(CARRET, "$1");
        currentPattern = currentPattern.replace(name, value);
        return this;
    }

    public Pattern build() {
        return Pattern.compile(currentPattern);
    }

    public String buildAsString() {
        return currentPattern;
    }
}

package com.ivan.knowledgebase.markdown.tokenizer.utils;

// CHECKSTYLE.SUPPRESS: AbbreviationAsWordInName
public enum RTrimUtils {
    INSTANCE;

    public String rtrim(String str, String c) {
        return rtrim(str, c, false);
    }

    public String rtrim(String str, String c, boolean invert) {
        int l = str.length();
        if (l == 0) {
            return "";
        }

        int suffLen = 0;

        while (suffLen < l) {
            char currChar = str.charAt(l - suffLen - 1);
            if ((currChar == c.charAt(0) && !invert) || (currChar != c.charAt(0) && invert)) {
                suffLen++;
            } else {
                break;
            }
        }

        return str.substring(0, l - suffLen);
    }
}

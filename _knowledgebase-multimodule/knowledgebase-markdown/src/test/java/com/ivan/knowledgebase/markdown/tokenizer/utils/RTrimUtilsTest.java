package com.ivan.knowledgebase.markdown.tokenizer.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.ivan.knowledgebase.markdown.tokenizer.utils.RTrimUtils;

//CHECKSTYLE.SUPPRESS: AbbreviationAsWordInName
class RTrimUtilsTest {
    @Test
    void testTrim() {
        String result1 = RTrimUtils.INSTANCE.rtrim("hellooo", "o");
        assertThat(result1).isEqualTo("hell");

        String result2 = RTrimUtils.INSTANCE.rtrim("hellooo", "o", false);
        assertThat(result2).isEqualTo("hell");

        String result3 = RTrimUtils.INSTANCE.rtrim("hellooo", "h", true);
        assertThat(result3).isEqualTo("h");
    }
}

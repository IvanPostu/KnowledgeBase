package com.ivan.knowledgebase.markdown.tokenizer.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

class EscapeUtilsTest {

    @Test
    void testEscapeWithEncode() {
        assertThat(EscapeUtils.INSTANCE.escape("\\>", false))
                .isEqualTo("\\&gt;");
        assertThat(EscapeUtils.INSTANCE.escape("Hello <World>", false))
                .isEqualTo("Hello &lt;World&gt;");
        assertThat(EscapeUtils.INSTANCE.escape("This & that", false))
                .isEqualTo("This &amp; that");
        assertThat(EscapeUtils.INSTANCE.escape("She said, \"Hi!\"", false))
                .isEqualTo("She said, &quot;Hi!&quot;");
    }

    @Test
    void testEscapeWithoutEncode() {
        assertThat(EscapeUtils.INSTANCE.escape(">>>", false))
                .isEqualTo("&gt;&gt;&gt;");
    }
}

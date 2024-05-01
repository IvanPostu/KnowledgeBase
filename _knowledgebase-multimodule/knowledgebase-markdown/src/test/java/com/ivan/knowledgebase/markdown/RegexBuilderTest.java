package com.ivan.knowledgebase.markdown;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class RegexBuilderTest {

    @Test
    void testRegexBuilder() {
        RegexBuilder regexBuilder = RegexBuilder.createFromTemplate("^ {0,3}\\[(label)\\]: *(?:\\n *)?([^<\\s]"
            + "[^\\s]*|<.*?>)(?:(?: +(?:\\n *)?| *\\n *)"
            + "(title))? *(?:\\n+|$)");
        regexBuilder.replacePlaceholder("label", "(?!\\s*\\])(?:\\\\.|[^\\[\\]\\\\])+");
        regexBuilder.replacePlaceholder("title",
            "(?:\"(?:\\\\\"?|[^\"\\\\])*\"|'[^'\\n]" + "*(?:\\n[^'\\n]+)*\\n?'|\\([^()]*\\))");

        Assertions.assertThat(regexBuilder.buildAsString())
            .isEqualTo("^ {0,3}\\[((?!\\s*\\])(?:\\\\.|[^\\[\\]\\\\])+)\\]: *(?:\\n *)?([^<\\s][^\\s]*|<.*?>)"
                + "(?:(?: +(?:\\n *)?| *\\n *)((?:\"(?:\\\\\"?|[^\"\\\\])*\"|'[^'\\n]"
                + "*(?:\\n[^'\\n]+)*\\n?'|\\([^()]*\\))))? *(?:\\n+|$)");
    }

    @Test
    void testRegexBuilderHandlesCarretProperly() {
        RegexBuilder regexBuilder = RegexBuilder.createFromTemplate("^([^\\n]+(?:\\n(?!hr| +\\n)[^\\n]+)*)");
        regexBuilder.replacePlaceholder("hr",
            "^ {0,3}((?:-[\\t ]*){3,}|(?:_[ \\t]*){3,}" + "|(?:\\*[ \\t]*){3,})(?:\\n+|$)");

        Assertions.assertThat(regexBuilder.buildAsString()).isEqualTo("^([^\\n]+(?:\\n(?! {0,3}((?:-[\\t ]*){3,}"
            + "|(?:_[ \\t]*){3,}|(?:\\*[ \\t]*){3,})"
            + "(?:\\n+|$)| +\\n)[^\\n]+)*)");
    }
}

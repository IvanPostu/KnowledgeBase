package com.ivan.knowledgebase.markdown.normalizer;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

class NormalizersTest {
    private static final PlainMarkdownNormalizer DEFAULT_NORMALIZER = Normalizers.INSTANCE
        .create(NormalizerType.DEFAULT);
    private static final PlainMarkdownNormalizer PEDANTIC_NORMALIZER = Normalizers.INSTANCE
        .create(NormalizerType.PEDANTIC);

    @Test
    void testNormalizeSpacesAndTabsWithPedanticTrue() {
        String input = "This is a\n    "
            + "test\n    "
            + "string";
        String expectedOutput = "This is a\n    test\n    string";

        assertThat(normalize(PEDANTIC_NORMALIZER, input)).isEqualTo(expectedOutput);
    }

    @Test
    void testNormalizeSpacesAndTabsWithPedanticFalse() {
        String input = "This is a\n\ttest\n\tstring";
        String expectedOutput = "This is a\n    test\n    string";

        assertThat(normalize(DEFAULT_NORMALIZER, input)).isEqualTo(expectedOutput);
    }

    @Test
    void testRemoveExtraSpacesAndTabsWithPedanticTrue() {
        String input = "This is a\n\ttest\n\tstring";
        String expectedOutput = "This is a\n    test\n    string";

        assertThat(normalize(PEDANTIC_NORMALIZER, input)).isEqualTo(expectedOutput);
    }

    @Test
    void testConvertTabsIntoSpacesWithPedanticFalse() {
        String input = "This is a\n\ttest\n\tstring";
        String expectedOutput = "This is a\n    test\n    string";

        assertThat(normalize(DEFAULT_NORMALIZER, input)).isEqualTo(expectedOutput);
    }

    private String normalize(PlainMarkdownNormalizer normalizer, String value) {
        return normalizer.normalizeEndLines(normalizer.normalizeTabsAndWhitespaces(value));
    }
}

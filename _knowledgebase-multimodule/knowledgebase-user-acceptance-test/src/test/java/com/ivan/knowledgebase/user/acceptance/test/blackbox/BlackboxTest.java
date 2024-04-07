package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class BlackboxTest {

    @Test
    void shouldAnswerWithTrue() {
        Assertions.assertThat(true).isTrue();
    }

    @Test
    void shouldAnswerWithTrue1() {
        Assertions.assertThat(1 == 2).isTrue();
    }

}

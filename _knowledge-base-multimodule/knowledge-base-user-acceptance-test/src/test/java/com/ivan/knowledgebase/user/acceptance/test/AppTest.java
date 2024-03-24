package com.ivan.knowledgebase.user.acceptance.test;

import org.assertj.core.api.Assertions;
import org.testng.TestNG;

public class AppTest {

    @org.testng.annotations.Test
    public void shouldAnswerWithTrue() throws Exception {
        Thread.sleep(10000);
        Assertions.assertThat(true).isEqualTo(false);
    }

}

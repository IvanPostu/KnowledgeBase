package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import org.testng.Assert;
import org.testng.annotations.Test;

class BlackboxTest {

    @Test
    void shouldAnswerWithTrue() {
        Assert.assertTrue(true);
    }

    @Test
    void shouldAnswerWithTrue1() {
        Assert.assertTrue(1 == 2);
    }

}

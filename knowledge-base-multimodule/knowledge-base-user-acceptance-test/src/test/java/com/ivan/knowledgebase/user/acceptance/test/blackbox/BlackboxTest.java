package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BlackboxTest {
    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void shouldAnswerWithTrue1() {
        assertTrue(1 == 2);
    }

    @Test
    void shouldAnswerWithTrue2() {
        assertTrue(1 == 3);
    }
}

package com.kayulu.junitdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DemoUtilsTest {

    private DemoUtils demoUtils;

    @BeforeAll
    static void setupBeforeAll() {
        System.out.println("@BeforeAll runs once before all test-methods");
    }

    @AfterAll
    static void setupAfterAll() {
        System.out.println("@AfterAll runs once after all test-methods");
    }

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach runs before each test-methode");
    }

    @AfterEach
    void setupAfterEach() {
        System.out.println("@AfterEach runs after each test-method\n");
    }

    @Test
    void testEqualsAndNotEquals() {
        System.out.println("Running test: testEqualsAndNotEquals");

        assertEquals(6, demoUtils.add(2,4), "2 + 4 must be 6");
        assertNotEquals(19, demoUtils.add(1,9), "1 + 9 must not be 19");
    }

    @Test
    void testNullAndNotNull() {
        System.out.println("Running test: testNullAndNotNull");

        assertNull(demoUtils.checkNull(null), "Object should be null");
        assertNotNull(demoUtils.checkNull("Hello"), "Object should not be null");
    }
}

package com.kayulu.junitdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
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
    @DisplayName("Equals and Not Equals")
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2,4), "2 + 4 must be 6");
        assertNotEquals(19, demoUtils.add(1,9), "1 + 9 must not be 19");
    }

    @Test
    @DisplayName("Null and Not Null")
    void testNullAndNotNull() {
        assertNull(demoUtils.checkNull(null), "Object should be null");
        assertNotNull(demoUtils.checkNull("Hello"), "Object should not be null");
    }

    @Test
    @DisplayName("Same and Not Same")
    void testSameObject() {
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Should reference same object");
        assertNotSame(demoUtils.getAcademy(), "Other", "Should not reference same object");
    }

    @Test
    @DisplayName("True and False")
    void testIsTrue() {
        assertTrue(demoUtils.isGreater(20, 10), "Should return true");
        assertFalse(demoUtils.isGreater(10, 20), "Should return false");
    }
}

package com.kayulu.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

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

    @Test
    @DisplayName("Arrays Equals")
    void testArraysEquals() {
        String[] arr = {"A", "B", "C"};
        assertArrayEquals(arr, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be equal");
    }

    @Test
    @DisplayName("Iterables equals")
    void testIterablesEquals() {
        List<String> list = List.of("luv", "2", "code");

        assertIterableEquals(demoUtils.getAcademyInList(), list, "Iterables should be equal");
    }

    @Test
    @DisplayName("Lines match")
    void testLinesMatch() {
        List<String> list = List.of("luv", "2", "code");

        assertLinesMatch(demoUtils.getAcademyInList(), list, "Lines should match");
    }

    @Test
    @DisplayName("Throws and Does Not Throw exception")
    void testThrowsException() {
        assertThrows(Exception.class, () -> demoUtils.throwException(-1), "Should throw exception");
        assertDoesNotThrow(() -> demoUtils.throwException(1), "Should not throw exception");
    }

    @Test
    @DisplayName("Timeout")
    void testTimeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> demoUtils.checkTimeout(), "Method should execute in 3 seconds");
    }
}

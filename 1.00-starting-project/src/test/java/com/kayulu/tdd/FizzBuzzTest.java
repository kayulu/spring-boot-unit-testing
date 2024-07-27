package com.kayulu.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {
    // if number is divisible by 3, write Fizz
    // if number is divisible by 5, write Buzz
    // if number is divisible by 3 and 5, write FizzBuzz
    // else write number

    @Test
    @DisplayName("Divisible by Three")
    @Order(1)
    void testForDivisibleByThree() {
        assertEquals("Fizz", FizzBuzz.compute(3L), "Should return \"Fizz\"");
    }

    @Test
    @DisplayName("Divisible by Five")
    @Order(2)
    void testForDivisibleByFive() {
        assertEquals("Buzz", FizzBuzz.compute(5L), "Should return \"Buzz\"");
    }

    @Test
    @DisplayName("Divisible by Three and Five")
    @Order(3)
    void testForDivisibleByThreeAndFive() {
        assertEquals("FizzBuzz", FizzBuzz.compute(15L), "Should return \"FizzBuzz\"");
    }

    @Test
    @DisplayName("NOT Divisible by Three and Five")
    @Order(4)
    void testForNotDivisibleByThreeAndFive() {
        assertEquals("1", FizzBuzz.compute(1L), "Should return 1");
    }

//    @ParameterizedTest
    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Divisible by Three and Five; CSV Parameterized")
    @CsvSource({
            "1,1",
            "2,2",
            "3,Fizz",
            "4,4",
            "5,Buzz",
            "6,Fizz",
            "15,FizzBuzz"
    })
    @Order(5)
    void testCsvData(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute((long) value), "Result should be " + expected);
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Divisible by Three and Five; CSV File Parameterized")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(6)
    void testSmallCsvFileData(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute((long) value), "Result should be " + expected);
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Divisible by Three and Five; CSV File Parameterized")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(7)
    void testMediumCsvFileData(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute((long) value), "Result should be " + expected);
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @DisplayName("Divisible by Three and Five; CSV File Parameterized")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(8)
    void testLargeCsvFileData(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute((long) value), "Result should be " + expected);
    }
}

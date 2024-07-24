package com.kayulu.tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}

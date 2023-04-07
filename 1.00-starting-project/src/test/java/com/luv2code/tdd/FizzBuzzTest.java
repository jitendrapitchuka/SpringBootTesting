package com.luv2code.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    @DisplayName("DIvisble by three")
    @Test
    @Order(1)
    void testForDivisibleBYThree(){
        String expected="fizz";
        assertEquals(expected,FizzBuzz.compute(3),"should retur fizz");
    }

    @DisplayName("DIvisble by five")
    @Test
    @Order(2)
    void testForDivisibleBYFive(){
        String expected="buzz";
        assertEquals(expected,FizzBuzz.compute(5),"should retur buzz");
    }

    @DisplayName("DIvisble by five and three")
    @Test
    @Order(3)
    void testForDivisibleBYFiveAndTHree(){
        String expected="fizzbuzz";
        assertEquals(expected,FizzBuzz.compute(15),"should retur fizz buzz");
    }

    @DisplayName("not DIvisble by five or three")
    @Test
    @Order(4)
    void testForNotDivisibleBYFiveOrTHree(){
        String expected="7";
        assertEquals(expected,FizzBuzz.compute(7),"should return 7");
    }

    @DisplayName("testing with medium csv data ")
    @ParameterizedTest(name = "value={0},expected={1}")
    @CsvFileSource(resources ="/medium-test-data.csv" )
    @Order(5)
    void testForParametrTest(int value,String expected){

        assertEquals(expected,FizzBuzz.compute(value));
    }

    @DisplayName("testing with large csv data ")
    @ParameterizedTest(name = "value={0},expected={1}")
    @CsvFileSource(resources ="/small-test-data.csv" )
    @Order(5)
    void testForParametrTestSmallData
            (int value,String expected){

        assertEquals(expected,FizzBuzz.compute(value));
    }
}

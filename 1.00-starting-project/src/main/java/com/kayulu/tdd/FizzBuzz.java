package com.kayulu.tdd;

public class FizzBuzz {
    // if number is divisible by 3, write Fizz
    // if number is divisible by 5, write Buzz
    // if number is divisible by 3 and 5, write FizzBuzz
    // else write number

    public static String compute(Long number) {

        if(number % 3 == 0) {
            if(number % 5 == 0)
                return "FizzBuzz";
            return "Fizz";
        }
        else if(number % 5 == 0)
            return "Buzz";

        return number.toString();
    }
}

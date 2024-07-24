package com.kayulu.tdd;

public class FizzBuzz {
    // if number is divisible by 3, write Fizz
    // if number is divisible by 5, write Buzz
    // if number is divisible by 3 and 5, write FizzBuzz
    // else write number

    public static String compute(Long number) {
        StringBuilder sb = new StringBuilder();

        if(number % 3 == 0)
            sb.append("Fizz");
        if(number % 5 == 0)
            sb.append("Buzz");
        if(sb.isEmpty())
            sb.append(number);

        return sb.toString();
    }
}

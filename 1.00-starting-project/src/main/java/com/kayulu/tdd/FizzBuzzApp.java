package com.kayulu.tdd;

public class FizzBuzzApp {
    public static void main(String[] args) {
        for(int i = 1; i < 100; i++)
            System.out.println(FizzBuzz.compute((long) i));
    }
}

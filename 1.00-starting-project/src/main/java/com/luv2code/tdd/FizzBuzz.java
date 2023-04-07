package com.luv2code.tdd;

public class FizzBuzz {

    public static String compute(int i) {

        StringBuilder result=new StringBuilder();
        if(i%3==0)
            result.append("fizz");
        if(i%5==0)
            result.append("buzz");
        if(result.isEmpty())
            result.append(i);

        return result.toString();

    }
//    public static String compute(int i) {
//        if(i%3==0 && i%5==0)
//            return "fizzbuzz";
//
//        if(i%3==0)
//            return "fizz";
//        else if(i%5==0)
//            return "buzz";
//        else
//            return Integer.toString(i);
//
//    }
}

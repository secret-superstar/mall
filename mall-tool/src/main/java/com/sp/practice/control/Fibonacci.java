package com.sp.practice.control;// control/Fibonacci.java
// TIJ4 Chapter Control, Exercise 9, page 153
/* A Fibonacci sequence is the sequence of numbers 1, 1, 2, 3, 5, 8, 13, 21, 34,
* and so on, where each number (from the third on) is the sum of the previous
* two. Create a method that takes an integer as an argument and displays that
* many Fibonacci numbers starting from the beginning, e.g.,if you run java
* Fibonacci 5 (where Fibonacci is the name of the class) the output will be
* 1,1,2,3,5.
*/

public class Fibonacci {
    int fib(int n) {
        if (n < 2) return 1;
        return (fib(n - 2) + fib(n - 1));
    }

    /**
     * Fib的数列
     *
     * @param n
     * @return
     */
    int fib2(int n) {
        if (n < 2) return 1;

        return (fib2(n - 2) + fib(n));
    }

    public static void main(String[] args) {
        Fibonacci f = new Fibonacci();
        int k = 30; //Integer.parseInt(args[0]);
        System.out.println("First " + k + " Fibonacci number(s): ");
        for (int i = 0; i < k; i++)
            System.out.println(f.fib(i));

        System.out.println("Prime print .....");
        prime();
    }

    public static void prime() {
        for (int i = 1; i < 1000; i++) {
            int factors = 0;
            for (int j = 1; j < (i + 2) / 2; j++) {
                if ((i % j) == 0) factors++;
            }
            if (factors < 2) System.out.println(i + " is prime");
        }
    }
}
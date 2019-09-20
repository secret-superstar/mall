package com.sp.practice.strings;// strings/Ex5.java
// TIJ4 Chapter Strings, Exercise 5, page 521
/* For each of the basic conversion types in the above table, write the 
* most complex formatting expression possible. That is, use all the possible
* format specifiers available for that conversion type.
*/

import java.math.*;
import java.util.*;

public class Ex5 {

    String s;

    public static void main(String[] args) {
        Formatter f = new Formatter(System.out);

        char u = 'a';
        System.out.println("char u = \'a\'");
        f.format("%-2s%-2S%-2c%-2C%-5b%-5B%-3h%-3H%%\n", u, u, u, u, u, u, u, u);

        int v = 121;
        System.out.println("int v = 121");
        f.format("%-4s%-4S%-4d%-4c%-4C%-5b%-5B%-4x%-4X%-4h%-4H%%\n", v, v, v, v, v, v, v, v, v, v, v);

        BigInteger w = new BigInteger("50000000000000");
        System.out.println("BigInteger w = 50000000000000");
        f.format("%-15s%-15S%-5b%-5B%-15x%-15X%-5h%-5H%%\n", w, w, w, w, w, w, w, w);

        double x = 179.543;
        System.out.println("double x = 179.543");
        f.format("%-8s%-8S%-5b%-5B%-15f%-15e%-15E%-12h%-12H%%\n", x, x, x, x, x, x, x, x, x);

        boolean z = false;
        System.out.println("boolean z = false");
        f.format("%-7s%-7S%-7b%-7B%-7h%-7H%%\n", z, z, z, z, z, z);

        replaceString();

        Ex5 s20 = new Ex5();
        System.out.println(s20.scanner2("17 56789 2.7 3.61412 hello"));

        //匹配字符串的处理操作
        matches();

    }

    /**
     * 对字符串进行替换操作，并且涉及了里面的字符都进行替换其操作
     */
    public static void replaceString() {
        String knights =
                "Then, when you have found the shrubbery, you must " +
                        "cut down the mightiest tree in the forest... " +
                        "with... a herring!";
        System.out.println(knights.replaceAll("[aeiouAEIOU]", "_"));
    }

    public String scanner2(String s) {
        Scanner sc = new Scanner(s);
        int i = sc.nextInt();
        long L = sc.nextLong();
        float f = sc.nextFloat();
        double d = sc.nextDouble();
        this.s = sc.next();
        return i + " " + L + " " + f + " " + d + " " + s;
    }

    public static void matches() {
        String sen = "^[A-Z].*[\\.]$";
        String s1 = "Once upon a time.";
        String s2 = "abcd.";
        String s3 = "Abcd?";
        String s4 = "An easy way out.";
        String s5 = "Zorro.";
        String s6 = "X.";
        println(s1.matches(sen));
        println(s2.matches(sen));
        println(s3.matches(sen));
        println(s4.matches(sen));
        println(s5.matches(sen));
        println(s6.matches(sen));
        splitDemo();
    }


    public static void splitDemo() {
        String input = "This!!unusual use!!of exclamation!!points";
        println(Arrays.toString(input.split("!!")));
        // Only do the first three:
        println(Arrays.toString(input.split("!!", 3)));
    }

    public static void println(Object s) {
        System.out.println(s.toString());
    }

}

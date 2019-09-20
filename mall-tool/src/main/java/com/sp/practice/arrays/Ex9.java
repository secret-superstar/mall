package com.sp.practice.arrays;// arrays//Ex9.java
// TIJ4 Chapter Arrays, Exercise 9, page 762
/* Create the classes necessary for the Peel<Banana> example and
* show that the compiler doesn't accept it. Fix the problem
* using an ArrayList.
*/

import java.util.*;

class Banana {
    private static long counter;
    private final long id = counter++;

    public String toString() {
        return "Banana " + id;
    }
}

class Peel<T> {
    private T t;
    private static long counter;
    private final long id = counter++;

    public Peel(T t) {
        this.t = t;
    }

    public String toString() {
        return "Peel " + id + " " + t.toString();
    }
}

class Apple<T> {
    private T t;
    private static long counter;
    private final long id = counter++;

    public Apple(T t) {
        this.t = t;
    }

    public String toString() {
        return "Apple " + id + t.toString();
    }
}

/**
 * 这是对数组模板的创建，该数组的格式是
 * {
 * {
 * <p>
 * },
 * }
 *
 * @param <T>
 */
public class Ex9<T> {
    public static void main(String[] args) {

        List<Apple> apples = new ArrayList<Apple>();
        for (int i = 0; i < 10; i++) {
            Apple<String> apple = new Apple("aaa" + i + ",");
            apples.add(apple);
        }

        List<Peel<Banana>> peels = new ArrayList<Peel<Banana>>();
        for (int i = 0; i < 10; i++)
            peels.add(new Peel<Banana>(new Banana()));
        System.out.println(peels);
        System.out.println("peels.get(0).getClass(): " + peels.get(0).getClass());
        System.out.println(apples);

        equalsArray();
        asList();

    }

    public static void equalsArray() {

        int[][][] a1 = new int[4][3][2];
        int[][][] a2 = new int[4][3][2];
        print(Arrays.deepToString(a1));
        print(Arrays.deepEquals(a1, a2));
        A[][][] aa1 = new A[4][3][2];
        A[][][] aa2 = new A[4][3][2];
        print(Arrays.deepToString(aa1));
        print(Arrays.deepEquals(aa1, aa2));
        print(Arrays.deepEquals(a1, aa1));
    }

    public static void asList() {
        List<Integer> aList =
                new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        print(aList);
        print(aList.get(4));
    }

    public static void print(Object t) {
        System.out.println(t.toString());
    }
}

class A {
}
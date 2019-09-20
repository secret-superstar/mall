package com.sp.practice.arrays;

import java.util.Arrays;

/**
 * 扩展的处理操作
 */
public class Ex4 {

    static double[][][] create3Array(int size1, int size2, int size3,
                                     double start, double end) {
        if (!(start < end)) {
            System.out.println("Start must be less than end");
            return null;
        }
        if ((size1 < 1) || (size2 < 1) || (size3 < 1)) {
            System.out.println("Size must be > 0");
            return null;
        }

        //构建三维数组的处理操作
        double[][][] result = new double[size1][size2][size3];
        for (int i = 0; i < size1; i++)
            result[i] = Ex3.createArray(size2, size3, start, end);
        return result;
    }

    static void print3Array(double[][][] da) {
        System.out.println(Arrays.deepToString(da));
    }

    static void print3Array(int[][] da) {
        System.out.println(Arrays.deepToString(da));
    }


    public static void main(String[] args) {
        print3Array(create3Array(0, 2, 3, 4, 5));
        print3Array(create3Array(2, 3, 3, 5, 4));
        print3Array(create3Array(1, 2, 2, 5, 10));
        print3Array(create3Array(2, 2, 3, 5, 10));
        print3Array(create3Array(3, 3, 3, 5, 10));
        print3Array(create3Array(3, 5, 5, 10, 20));
        print3Array(create3Array(5, 5, 5, 10, 100));
        print3Array(createIntArray(5, 8, 5, 100));
    }

    public static int[][] createIntArray(int size1, int size2, int start, int end) {

        if (start > end) {
            System.out.println("End should great than start");
            return null;
        }

        int[][] arr = new int[size1][size2];

        for (int i = 0; i < size1; i++) {
            //arr[i] = new int[size2];
            for (int j = 0; j < size2; j++) {
                arr[i][j] = start +  (j / (size2 - 1)) * ((j + i) / (size2 - 1 + i)) * (end - start);
            }
        }

        return arr;
    }
}

package com.sp.tencent;

/**
 * Apple 来作为Java类的处理方式
 */
class Apple {

    private static Apple apple = null;

    private Apple() {

    }

    public static synchronized Apple getInstance() {
        if (apple == null) {
            apple = new Apple();
        }
        return apple;
    }

    public void print() {
        System.out.println("Hello world");
    }

    public static void main(String[] args) {

        Apple a = Apple.getInstance();
        a.print();
    }

}
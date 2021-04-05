package com.gexx.spring;

public class JVM {

    public static void main(String[] args) {
        try {
            System.out.println(666);
            Thread.sleep(1000000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

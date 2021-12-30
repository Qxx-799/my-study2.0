package com.qxx.common.service;

public class StringTest {

    public static void main(String[] args) {
        String str1 = new  String("计算机");
        String str2 = str1.intern();

        System.out.println(str1 == str2);
    }
}

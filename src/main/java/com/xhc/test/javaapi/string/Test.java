package com.xhc.test.javaapi.string;

public class Test {

    public static void main(String[] args) {
        String s = "12||3|fvererw|   4434|fw   | sdfa||";
        String[] ss = s.split("\\|");
        System.out.println(ss.length);
        for(String temp : ss){
            System.out.println(temp.trim());
        }
    }
}

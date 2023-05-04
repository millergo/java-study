package com.github.millergo.javase.jdk8;

import java.util.Arrays;

/**
 * 方法引用
 */
public class MethodReference {
    public static void main(String[] args) {
        String[] array = new String[]{"Apple", "Orange", "Banana", "Lemon"};
        // 所谓方法引用，是指如果某个方法签名和接口恰好一致，就可以直接传入方法引用。int compare(String, String)
        // 方法签名只看参数类型和返回类型，不看方法名称，也不看类的继承关系。
        Arrays.sort(array, MethodReference::cmp);

        // 因为实例方法有一个隐含的this参数，String类的compareTo()方法在实际调用的时候，第一个隐含参数总是传入this，相当于静态方法
        // public static int compareTo(this, String o);
        Arrays.sort(array, String::compareTo);
        System.out.println(String.join(", ", array));

    }

    static int cmp(String s1, String s2) {
        return s1.compareTo(s2);
    }
}

package com.github.millergo.javase.basic;

import org.junit.jupiter.api.Test;

/**
 * 字符串比较。
 * String 的 compareTo() 方法按照字符串的字典顺序进行逐个字符比较。
 * 如果str1小于(字母顺序在前面)str2则返回一个负数，
 * 如果str1大于(字母顺序在后面)str2则返回一个整数，
 * 相等则返回0
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/7 16:57:03
 */
public class StringCompareToTests {
    @Test
    void testLessThan0() {
        String str1 = "abc";
        String str2 = "bcd";
        System.out.println(str1.compareTo(str2));
    }
    @Test
    void testGreaterThan0() {
        String str1 = "bcd";
        String str2 = "acd";
        System.out.println(str1.compareTo(str2));
    }
    @Test
    void testEqual0() {
        String str1 = "abc";
        String str2 = "abc";
        System.out.println(str1.compareTo(str2));
    }
}

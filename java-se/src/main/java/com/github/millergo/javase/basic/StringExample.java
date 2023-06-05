package com.github.millergo.javase.basic;


/**
 * 概要描述
 *
 * <p>
 * 详细描述
 * </p>
 *
 * @author Miller Shan
 * @since 2023-05-06 14:11:17
 */
public class StringExample {
    // 字符型常量
    public static final char LETTER_A = 'A';

    // 字符串常量
    public static final String GREETING_MESSAGE = "Hello, world!";

    public static void main(String[] args) {
        System.out.println("字符型常量占用的字节数为：" + Character.BYTES);
        System.out.println("字符串常量占用的字节数为：" + GREETING_MESSAGE.getBytes().length);
        var i = 10;

    }

}

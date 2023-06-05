package com.github.millergo.javase.algorithm;

/**
 * 回文字符串
 *
 * <p>
 * 详细描述
 * </p>
 *
 * @author Miller Shan
 * @since 2023-05-25 18:12:37
 */
public class Plalindrome {
    public static void main(String[] args) {
        System.out.println(isPlalindrome_1("12321"));
        System.out.println(isPlalindrome_1("1231"));
        System.out.println(isPlalindrome_1("123321"));
    }

    /**
     * 借助内置函数反转实现回文字符串判断
     *
     * @param str 待判断字符串
     * @return
     */
    private static boolean isPlalindrome_1(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString().equals(str);
    }
}
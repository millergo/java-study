package com.github.millergo.javase.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Java 中的操作符
 * <p>
 * 移位运算符
 * <p>
 * << :左移运算符，向左移若干位，高位丢弃，低位补零。x << 1,相当于 x 乘以 2(不溢出的情况下)。
 * >> :带符号右移，向右移若干位，高位补符号位，低位丢弃。正数高位补 0,负数高位补 1。x >> 1,相当于 x 除以 2。
 * >>> :无符号右移，忽略符号位，空位都以 0 补齐。
 * </p>
 *
 * @author Miller Shan
 * @since 2023-05-05 17:12:59
 */

@DisplayName("Java 操作符")
public class Operator {
    @DisplayName("位移")
    @Test
    void testMove() {
        int i = -1;
        System.out.println("初始数据：" + i);
        System.out.println("初始数据对应的二进制字符串：" + Integer.toBinaryString(i));
        i <<= 10;
        System.out.println("左移 10 位后的数据 " + i);
        System.out.println("左移 10 位后的数据对应的二进制字符 " + Integer.toBinaryString(i));
    }

    /**
     * 为什么会出现这个问题呢？
     * <p>
     * 这个和计算机保存浮点数的机制有很大关系。我们知道计算机是二进制的，而且计算机在表示一个数字时，宽
     * 度是有限的，无限循环的小数存储在计算机时，只能被截断，所以就会导致小数精度发生损失的情况。
     * 这也就是解释了为什么浮点数没有办法用二进制精确表示。
     */
    @Test
    void testFloat() {
        // 浮点数运算精度丢失代码演示：
        float i = 2.0f - 1.9f;
        float j = 1.8f - 1.7f;
        System.out.println(i);// 0.100000024
        System.out.println(j);// 0.099999905
        System.out.println(i == j);// false
        // 解决方法：
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("0.9");
        BigDecimal c = new BigDecimal("0.8");

        BigDecimal x = a.subtract(b);
        BigDecimal y = b.subtract(c);

        System.out.println(x); /* 0.1 */
        System.out.println(y); /* 0.1 */
        System.out.println(Objects.equals(x, y)); /* true */
    }
}

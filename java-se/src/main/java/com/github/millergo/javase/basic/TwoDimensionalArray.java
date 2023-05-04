package com.github.millergo.javase.basic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 二维数组
 *
 * @author Miller Shan
 * @since 2023-05-04 18:04:49
 */
public class TwoDimensionalArray {
    @Test
    public void test() {
        String[][] arrays = new String[4][4];
        arrays[0] = new String[]{"a1", "a2", "a3"};
        arrays[1] = new String[]{"b1", "b2", "b3"};
        arrays[2] = new String[]{"c1", "c2", "c3"};
        arrays[3] = new String[]{"d1", "d2", "d3"};

        for (int i = 0; i < arrays.length; i++) {
            String[] a = arrays[i];
            System.out.println("每一行的数据是：" + Arrays.asList(a));
            for (int j = 0; j < a.length; j++) {
                System.out.println("每一列的数据是:" + a[j]);
            }
        }
        System.out.println(arrays[1][2]);
    }
}

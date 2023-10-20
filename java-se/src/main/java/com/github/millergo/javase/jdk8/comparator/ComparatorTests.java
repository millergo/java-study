package com.github.millergo.javase.jdk8.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * JDK 8 排序
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/19 15:53:11
 */
@DisplayName("Comparator TestSuite")
public class ComparatorTests {
    private List<String> myFamily = Arrays.asList("Miller", "Vicky", "Mila", "Millie");

    @Test
    void testSortByCompare() {
        // 正序: Mila, Vicky, Miller, Millie
        Collections.sort(myFamily, (item1, item2) -> item1.length() - item2.length());
        System.out.println(myFamily);
        // 倒序:  Miller, Millie, Vicky, Mila
        Collections.sort(myFamily, (item1, item2) -> item2.length() - item1.length());
        System.out.println(myFamily);
    }

    @Test
    void testSortByComparator() {
        // 正序
        Collections.sort(myFamily, Comparator.comparingInt(item -> item.length()));
        System.out.println(myFamily);
        // 倒序. 注意: 这里由于lambda 表达式里面的item类型编译器无法推断出来，所以需要显示声明
        Collections.sort(myFamily, Comparator.comparingInt((String item) -> item.length()).reversed());
        System.out.println(myFamily);
        /*
        使用方法引用.
        注意:这一步的结果与上一步相同。原因是因为在上一步代码已经对结果进行倒序排序了，
        使用comparingInt进行比较时，当发现两个元素比较==0，那么就不会进行排序，
        所以虽然调用了 reversed() 进行反转，但是元素内里的内容已经是排好序了，所以不会重新倒序。
         */
        Collections.sort(myFamily, Comparator.comparingInt(String::length).reversed());
        System.out.println(myFamily);
    }

    @Test
    void testComparatorChain() {
        myFamily.sort(
                // 先根据长度排序。Mila, Vicky, Miller, Millie
                Comparator.comparingInt(String::length).
                        // 然后在按照自然顺序进行倒序排序。Miller, Millie, Vicky, Mila
                                thenComparing(String::compareToIgnoreCase).reversed());
        System.out.println(myFamily);
    }
}

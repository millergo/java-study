package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/13 14:38:05
 */
public class StreamTest05 {
    @Test
    void practiceStreamByFlatMap() {
        List<String> list1 = Arrays.asList("Hello", "Hi");
        List<String> list2 = Arrays.asList("Miller", "Vicky", "Mila");
        // 实现 list1 和 list2 交叉. eg:Hello Miller, Hello Vicky...
        List<String> stringList = list1.stream().flatMap(item1 -> list2.stream().map(item2 -> item1 + " " + item2)).toList();
        stringList.forEach(System.out::println);
    }

}

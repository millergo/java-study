package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流转换为数组
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/9 19:49:53
 */
public class StreamTest02 {

    @DisplayName("Stream to array ")
    @Test
    void testStreamToArray() {
        Stream<String> strings = Stream.of("Miller", "Mila", "Vicky");
          String[] toArray = strings.toArray((length -> new String[length]));
        // String[] toArray = strings.toArray(String[]::new);

        Arrays.asList(toArray).forEach(System.out::println);
    }

    @DisplayName("Stream to array by Collect")
    @Test
    void testStreamToArray2() {
        // 方式一：使用 Collectors 封装好的方法
        Stream<String> strings1 = Stream.of("Miller", "Mila", "Vicky");
        List<String> stringList1 = strings1.collect(Collectors.toList());
        stringList1.forEach(System.out::println);

        // 方式二: 使用 lambda 表达式，这种方式能够更灵活，也便于理解方式一的底层实现
        Stream<String> strings2 = Stream.of("Miller", "Mila", "Vicky");
        List<String> stringList2 = strings2.collect(
                // 参数一: Supplier， 创建一个接受最终结果的容器(resultList)。
                () -> new ArrayList<>(),
                // 参数二: 将每一个元素(item)，添加到最终结果的容器(resultList)中.
                (resultList, item) -> resultList.add(item),
                // 参数三:将 参数二 的结果添加到最终结果的容器(resultList)中。
                (resultList, secondArgResult) -> resultList.addAll(secondArgResult));
        stringList2.forEach(System.out::println);

        // 方式三: 是对 方式二 的进一步优化，通过使用方法引用来完成同样的操作
        Stream<String> strings3 = Stream.of("Miller", "Mila", "Vicky");
        List<String> stringList3 = strings3.collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        stringList3.forEach(System.out::println);

        // 方式四：是对 方式一的一种灵活处理，方式一默认是转换为ArrayList。
        Stream<String> strings4 = Stream.of("Miller", "Mila", "Vicky");
        LinkedList<String> stringList4 = strings4.collect(Collectors.toCollection(LinkedList::new));
        stringList4.forEach(System.out::println);
    }

    @DisplayName("字符串处理")
    @Test
    void testStreamJoin(){
        Stream<String> stringStream = Stream.of("Miller", "Mila", "Vicky");
        String collect = stringStream.map(String::toUpperCase)
                .collect(Collectors.joining(", ", "-", "-"));
        System.out.println(collect);
    }
}

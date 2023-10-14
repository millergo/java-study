package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/10 10:51:11
 */
public class StreamTest03 {

    @DisplayName("flat map 用于将流中的元素全部平铺开来")
    @Test
    void testFlatMap() {
        // 定义一个流，流中的所有元素是一个 list
        Stream<List<Integer>> numbers = Stream.of(Arrays.asList(4, 2), Arrays.asList(3, 1, 5), Arrays.asList(6, 7));
        // 将流中的List取出来然后计算平方
        numbers.flatMap(
                        // 将流中的每一个元素都转换为流
                        (item) -> item.stream())
                // 将流中每一个元素映射处理
                .map(item -> item * item)
                // 将每一个流收集到集合中
                .collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);
    }

    @DisplayName("iterate 无限串行流")
    @Test
    void testIterate() {
        /*
        参数一: seed，种子数。
        参数二: UnaryOperator 是一个 Function 的函数式接口，表示对seed重复应用此Function。
         */
        Stream<Integer> limit = Stream.iterate(1, item -> item + 2).limit(5);
        limit.forEach(System.out::println);
    }

    @Test
    void testStream() {
        Stream<Integer> numbers = Stream.of(1, 3, 5, 7, 9, 11);
        int sum = numbers.filter(item -> item > 2).mapToInt(item -> item * 2)
                // 跳过前2个元素
                .skip(2)
                // 取前2个元素
                .limit(2)
                // 求总和
                .sum();
        System.out.println(sum);

        Stream<Integer> numbers2 = Stream.of(1, 3, 5, 7, 9, 11);
        Optional<Integer> reduce = numbers2.filter(item -> item > 2).map(item -> item * 2)
                // 跳过前2个元素
                .skip(2)
                // 取前2个元素
                .limit(2)
                // 求总和
                .reduce((item1, item2) -> item1 + item2);

        reduce.ifPresent(System.out::println);
    }
}

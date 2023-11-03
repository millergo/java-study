package com.github.millergo.javase.jdk8.stream;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stream 源码分析
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 10:19:39
 */
public class StreamTest10 {
    public static void main(String[] args) {
        List<String> myFamily = List.of("Miller", "Mila", "Vicky", "Millie");

        Stream<String> sourceStream = myFamily.stream();
        System.out.println("--------- source stream -----------");

        Stream<String> intermediateStream = sourceStream.map(item -> "Hello " + item);
        System.out.println("--------- intermediate stream ---------");

        intermediateStream.forEach(System.out::println);
        System.out.println("--------- terminal stream -------------");
    }
}

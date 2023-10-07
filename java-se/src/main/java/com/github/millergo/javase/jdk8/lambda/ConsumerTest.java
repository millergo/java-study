package com.github.millergo.javase.jdk8.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * Consumer 函数接口的含义为接受一个参数，不返回结果。
 */
public class ConsumerTest {
    @Test
    void testAndThen() {
        Consumer<String> consumer1 = (param) -> {
            String toUpperCase = param.toUpperCase();
            System.out.println(toUpperCase);
        };
        Consumer<String> consumer2 = (param) -> {
            CharSequence charSequence = param.subSequence(0, param.length() - 1);
            System.out.println(charSequence);
        };
        // Consumer 接口中的 andThen 方法接受一个 Consumer 参数。含义是先执行自身然后在执行参数中的 Consumer 函数接口的实现。
        consumer1.andThen(consumer2).accept("Miller");
    }
}

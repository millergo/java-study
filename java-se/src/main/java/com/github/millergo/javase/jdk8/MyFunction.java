package com.github.millergo.javase.jdk8;

/**
 * 函数式编程。要求接口中只能有一个抽象方法
 *
 * @author Miller Shan
 * @since 2023-05-04 18:04:49
 */
@FunctionalInterface
public interface MyFunction {
    public Integer getValue(Integer num);
}

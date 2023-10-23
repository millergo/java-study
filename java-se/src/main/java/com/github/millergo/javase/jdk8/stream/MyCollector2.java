package com.github.millergo.javase.jdk8.stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义收集器。实现返回Map类型。
 * 比如：输入元素为：hello, world, welcome; 输出结果为: hello: hello, world:world, welcome: welcome;
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/22 22:04:21
 */
public class MyCollector2<T> implements Collector<T, Set<T>, Map<T, T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println(Thread.currentThread().getName() + " supplier invoked!");
        return () -> {
            HashSet<T> hashSet = new HashSet<>();
            System.out.println("supplier function invoked Memory hashSet ID is: " + System.identityHashCode(hashSet));
            return hashSet;
        };
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println(Thread.currentThread().getName() + " accumulator invoked!");
        return (set, inputElement) -> {
            // 通过打印 set 的内存地址可以发现，这里的set对象就是 supplier() 方法生成的对象
            System.out.println("accumulator function invoked Memory set ID is: " + System.identityHashCode(set));
            set.add(inputElement);
        };
    }

    /**
     * 执行中间结果合并
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println(Thread.currentThread().getName() + " combiner invoked!");
        return (set1, set2) -> {
            // 当使用并行流 parallelStream() 并设置 Characteristics.UNORDERED 时此函数体会执行, 此时会生成多个容器对象
            System.out.println("combiner function invoked Memory set1 ID is: " + System.identityHashCode(set1));
            System.out.println("combiner function invoked Memory set2 ID is: " + System.identityHashCode(set2));
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println(Thread.currentThread().getName() + " finisher invoked!");
        return set -> {
            Map<T, T> map = new HashMap<>();
            // 使用流的方式在 CONCURRENT 模式下，使用parallelStream() 会引发 ConcurrentModificationException
            set.stream().forEach(item -> {
                System.out.println("finisher function invoked Memory item ID is: " + System.identityHashCode(item));
                map.put(item, item);
            });
            // for (T t : set) {
            //     map.put(t, t);
            // }
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println(Thread.currentThread().getName() + " characteristics invoked!");

         return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));

        // Error: ClassCastException。添加 IDENTITY_FINISH 之后会将中间结果容器 Set<T> 强制转换为 Map<T, T> 类型。
        // return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH));

        // Error: 设置为 CONCURRENT 之后如果使用 parallelStream() 并行流大概率会引发 Exception
        // return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT));
    }

    public static void main(String[] args) {
        for (var i = 0; i < 1; ++i) {
            Set<String> set = new HashSet<>();
            set.add("Java");
            set.add("Python");
            set.add("JavaScript");
//            Map<String, String> collect = set.stream().collect(new MyCollector2<>());
            Map<String, String> collect = set.parallelStream().collect(new MyCollector2<>());
            System.out.println(collect);
        }
    }
}

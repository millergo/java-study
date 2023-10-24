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
            System.out.println("accumulator:" + set + ", " + Thread.currentThread().getName());
            set.add(inputElement);
        };
    }

    /**
     * 执行中间结果合并。
     * combiner()方法在串行的时候是不会被调用的。只有在并行 parallelStream() 并且不包含 Characteristics.CONCURRENT 的时候才会被调用。
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println(Thread.currentThread().getName() + " combiner invoked!");
        return (set1, set2) -> {
            // 当使用并行流 parallelStream() 并不设置 Characteristics.CONCURRENT 时此函数体会执行, 此时会生成多个容器对象
            System.out.println("combiner function invoked Memory set1 ID is: " + System.identityHashCode(set1));
            System.out.println("combiner function invoked Memory set2 ID is: " + System.identityHashCode(set2));
            set1.addAll(set2);
            return set1;
        };
    }

    /**
     * 当设置 Characteristics.IDENTITY_FINISH 时会将中间结果值强制类型转换为最终值(R) container，不会调用 finisher() 方法。
     */
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

        // 数据源为无序时设置为 UNORDERED，否则则不需要设置。
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));

        /*
        Error: ClassCastException
        使用并行流 parallelStream 并设置为 Characteristics.IDENTITY_FINISH 时会将中间结果值强制类型转换为最终值(R) container，
        不会调用 finisher() 方法。比如本示例添加 IDENTITY_FINISH 之后会将中间结果容器 Set<T> 强制转换为 Map<T, T> 类型。
         */
        // return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

        /*
        Error: ConcurrentModificationException
        使用并行流 parallelStream 并设置为 Characteristics.CONCURRENT 表明中间结果容器只会有一个，多个线程同时对中间结果容器进行操作。
        大概率会引发 ConcurrentModificationException
         */
        // return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT));
    }

    public static void main(String[] args) {
        for (var i = 0; i < 1; ++i) {
            Set<String> set = new HashSet<>();
            set.add("Java");
            set.add("Python");
            set.add("JavaScript");
            set.add("C++");
            set.add("Rest");
            set.add("Go");
//            Map<String, String> collect = set.stream().collect(new MyCollector2<>());
            Map<String, String> collect = set.parallelStream().collect(new MyCollector2<>());
            System.out.println(collect);
        }
    }
}

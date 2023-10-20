package com.github.millergo.javase.jdk8.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义收集器
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/20 10:04:21
 */
public class MyCollector<T> implements Collector<T, Set<T>, Set<T>> {
    /**
     * 可变容器的结果
     */
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println(this.getClass().getName() + " supplier invoked!");
        return HashSet::new;
    }

    /**
     * 将结算结果累积到可变容器中
     */
    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println(this.getClass().getName() + " accumulator invoked!");
        return Set::add;
        // return HashSet::add;
    }

    /**
     * 将两个部分结果，合并到一个结果容器中
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println(this.getClass().getName() + " combiner invoked!");
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Set<T>> finisher() {
        System.out.println(this.getClass().getName() + " finisher invoked!");
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println(this.getClass().getName() + " characteristics invoked!");

        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED));
        // 不包含 Characteristics.IDENTITY_FINISH 时会调用 finisher() 函数
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    /**
     * 可以跟进去collect()方法，查看底层具体实现调用的方法。<br />
     * 1: collect()方法的定义：<R, A> R collect(Collector<? super T, A, R> collector);<br />
     * 2: collect()方法的具体实现：{@link ReferencePipeline#collect(Collector) }<br />
     * <pre>
     * public final <R, A> R collect(Collector<? super P_OUT, A, R> collector) {
     *      // 并发
     *      if (isParallel()
     *          ....
     *       }
     *      else {
     *      // 串行.可以查看 makeRef()方法实现了对四个函数的真实调用。
     *          container = evaluate(ReduceOps.makeRef(collector));
     *      }
     *      // 这里会调用一次 characteristics() 方法
     *      return collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? (R) container : collector.finisher().apply(container);
     * }
     * </pre>
     * 3: makeRef()方法调用Collector接口中的方法：
     * <pre>
     *      public static <T, I> TerminalOp<T, I> makeRef(Collector<? super T, I, ?> collector) {
     *          // 调用 supplier()
     *         Supplier<I> supplier = Objects.requireNonNull(collector).supplier();
     *         // accumulator()
     *         BiConsumer<I, ? super T> accumulator = collector.accumulator();
     *         // combiner()
     *         BinaryOperator<I> combiner = collector.combiner();
     * </pre>
     */
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Java");
        set.add("Python");
        set.add("JavaScript");
        set.stream().collect(new MyCollector<>()).forEach(System.out::println);
    }
}

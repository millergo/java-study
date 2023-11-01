package com.github.millergo.javase.jdk8.stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 * Stream 源码分析
 * </p>
 *
 * <p>1. stream() 方法定义在Collection类中，所以所有与集合相关的操作都拥有stream()方法，比如：List、Set等。
 * <p>2. stream() 底层具体实现是由 new ReferencePipeline 类来完成具体的操作。底层设计是通过"管道"的概念进行数据的处理，
 * 包含三个部分：源、中间操作、终止操作。
 * <p>3. ReferencePipeline 中有一个内部静态类 ReferencePipeline.Head 用于处理"引用管道的源"阶段，
 * 这是在 ReferencePipeline 中进行优化的处理。从管道设计而言，源与中间操作 大部分都是相同的，只是对于源会有一些
 * 特殊的标识。比如{@link java.util.stream.AbstractPipeline#previousStage} 、nextStage 等。
 *
 * @author Miller Shan
 * @version 1.0
 * @see java.util.stream.AbstractPipeline
 * @see java.util.stream.ReferencePipeline
 * @see java.util.stream.ReferencePipeline.Head
 * @since 2023/10/30 10:20:06
 */
public class StreamTest09 {
    private static List<String> list = Arrays.asList("Miller", "Vicky", "Mila", "Millie");

    public static void main(String[] args) {
        theStreamContainSourceAndTerminal();
        theStreamContainSourceAndIntermediateAndTerminal();
    }

    /**
     * <p>1. 源->终止操作。
     *
     * <p>2. stream() 方法被定义在{@link java.util.Collection}接口中，所以所有的Collection子类都可以调用此方法获取到Stream对象。
     * stream() 方法的实现体<code>return StreamSupport.stream(spliterator(), false);</code>会调用spliterator()方法。
     * spliterator() 方法的实现体<code>return Spliterators.spliterator(this, 0);</code>会调用Spliterators的spliterator()
     * 方法获得对象<code>return new IteratorSpliterator<>(Objects.requireNonNull(c), characteristics);</code>。而
     * 最终<code>IteratorSpliterator<T> implements Spliterator<T> </code>所以会实现里面的迭代分割器对元素进行分割处理，
     * 从这个类的内部可以发现底层还是通过一个Collection进行元素的存储{@link Spliterators.IteratorSpliterator#collection}。
     *
     * <p>3. {@link Collection#spliterator()}方法实现类对元素的分割处理，但是这个方法被众多子类的方法所重写，所以当我们调用stream()
     * 方法获取元素时，实际上是需要查看具体子类override的实现的。比如这里的"list"是 {@link Arrays.ArrayList} 中对 spliterator()
     * 方法进行重写，其具体实现体<code>return
     * .spliterator(a, Spliterator.ORDERED);</code>调用的是Spliterators
     * 的实现，而其实现体为<code>return new ArraySpliterator<>(Objects.requireNonNull(array), additionalCharacteristics);</code>
     * 其底层实现是通过{@link java.util.Spliterators.ArraySpliterator#forEachRemaining(Consumer)}  }完成真正的元素遍历，可以
     * 通过打个断点查看执行，最终遍历执行元素的代码为<code> do { action.accept((T)a[i]); } while (++i < hi); </code>
     *
     * <p>4. forEach() 方法在{@link java.util.stream.ReferencePipeline}中有两个具体的实现，可以通过在
     * {@link java.util.stream.ReferencePipeline.Head} 的 forEach() 方法打一个断点发现实际执行时调用的方法，
     * 如果从源直接调用终止操作，那么会被 {@link java.util.stream.ReferencePipeline.Head } 优化处理，提高效率.
     *
     * <p>5. 在{@link  java.util.Spliterators.IteratorSpliterator#forEachRemaining(Consumer)} 中也
     * 有一个 forEachRemaining()forEach() 方法对元素进行遍历，其底层实现是通过{@link Iterator#forEachRemaining(Consumer)}方法
     * 完成对迭代器中的元素进行遍历，从这个角度来看，最终肯定还是直接调用JDK提供的Iterator进行遍历。
     */
    private static void theStreamContainSourceAndTerminal() {
        list.stream().forEach(System.out::println);
        // System.out.println(list.getClass());
    }

    /**
     * 1. 源->中间操作->终止操作。
     *
     * <p>2. stream().map() 只有一个具体的map()实现体，在{@link java.util.stream.ReferencePipeline#map(Function)}中。
     * 这里用到类一个{@link java.util.stream.Sink} 类，它是Consumer的扩展，用于在流管道的各个阶段传递值，
     * 并带有管理大小信息、控制流等附加的方法。它规定了在调用 accept() 方法之前必须调用 begin() 方法，在 accept() 方法
     * 之后必须调用 end() 方法。
     *
     * <p>3. 从map() 方法的实现体<code> downstream.accept(mapper.apply(u)); </code> 可以看到，对于元素的 apply() 和 accept()
     * 是同时处理的，这里也<b>验证了流的操作是对每一个元素执行所有操作之后在处理下一个元素的, 而不是常规的面向对象的方式按照顺序依次调用方法。</b>
     * 同理 filter() 也是相同的逻辑。
     *
     * <p>4. 当流中包含中间操作时调用的是{@link java.util.stream.ReferencePipeline#forEach(Consumer)} 方法。
     */
    private static void theStreamContainSourceAndIntermediateAndTerminal() {
        list.stream().map(String::length).forEach(System.out::println); // 6 5 4 6

        System.out.println("--------------");
        // 结果只打印了 6 5
        list.stream().map(String::length).filter((length) -> {
            if (length >= 5) {
                return true;
            } else {
                /*
                强制终止运行 Java 虚拟机。从这里可以验证流的执行顺序是对每一元素都执行所有操作之后在对下一元素进行所有操作,
                所以即使后续有元素满足要求也没有被打印出来，
                 */
                System.exit(-1);    // 注视掉则打印 6 5 6
                return false;
            }
        }).forEach(System.out::println);
    }

}

package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collector 是 JDK Stream 中的一个非常重要的接口。Collector是一个接口，它是一个可变的汇聚操作，将输入元素累积到一个可变的结果容器中。
 * 它会在所有元素都处理完毕之后，将累积的结果转换为一个最终的表示(这个是一个可选的操作)。它支持串行和并行两种方法执行。
 *
 *
 * <p>
 * 1. collect(): 收集器。
 * 2. Collector: 作为 collect() 方法的参数。
 * 3. Collectors: 提供了关于 Collector 的常见汇聚实现，Collectors 本身实际上是一个工厂。
 * 4. Collector 由四个函数组合在一起进行工作，然后将计算过程累积到一个可变的容器中，最终会将结果进行强制类型转换为制定类型。这个四个函数是：
 *        a: supplier(): 创建一个新的结果容器。存储流的每一个元素；
 *        b: accumulator(): 将数据累积到结果容器中。对于每一个输入的元素都会调用 accumulator 函数一次；
 *        c: combiner(): 将两个结算结果的容器合并到一个结果中；
 *        d: finisher(): 对容器执行一个最终的转换操作，可选；
 * 5. 为了确保串行与并行操作结果的等价性，Collector函数需要满足两个条件：identity(同一性)与associativity(结合性)。
 * 6. 对于任意一系列累加器和组合器调用必须满足恒等约束，a == combiner.apply(a, supplier.get())。
 * 7. 函数式编程最大的特性：表示做什么，而不是如何做。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/17 10:46:17
 */
public class CollectorTests {
    private final User user1 = new User("Miller", 30, 90.0);
    private final User user2 = new User("Miller", 50, 80.0);
    private final User user3 = new User("Vicky", 50, 80.0);
    private final User user4 = new User("Mila", 80, 80.0);
    private final List<User> userList = Arrays.asList(user1, user2, user3, user4);

    @Test
    void testCollector() {
        userList.stream().collect(Collectors.minBy(Comparator.comparingInt(User::getMath))).ifPresent(System.out::println);
        // userList.stream().min(Comparator.comparingInt(User::getMath)).ifPresent(System.out::println);

    }
}

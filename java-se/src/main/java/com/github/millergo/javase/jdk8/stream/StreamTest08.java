package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * Spliterator 源码中 default boolean tryAdvance() 方法测试。
 * 在 Spliterator 源码中使用了一种强制类型转换，将 Consumer 强制转换为 IntConsumer，但是实际上这两者之间并不存在继承关系，
 * 却能转换成功，其实这里是因为传递的是lambada表达式的行为，而lambada表达式行为会根据上下文关系进行推断，这里由于是Integer与int类型的
 * 转换，会发生自动装箱的操作，所以能转换成功。
 * <pre>
 * default boolean tryAdvance(Consumer<? super Integer> action) {
 *             if (action instanceof IntConsumer) {
 *                 return tryAdvance((IntConsumer) action);
 *             }
 *             else {
 *                 if (Tripwire.ENABLED)
 *                     Tripwire.trip(getClass(),
 *                                   "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
 *                 return tryAdvance((IntConsumer) action::accept);
 *             }
 *         }
 * </pre>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/28 13:48:28
 */
public class StreamTest08 {

    @Test
    void testLambda() {
        Consumer<Integer> consumer = (x) -> System.out.println(x);
        IntConsumer intConsumer = (x) -> System.out.println(x);

        // 面向对象方式：传递的是引用
        testClassCast(2, consumer);

        // Error: ClassCastException
        // testClassCast(3, (Consumer<Integer>) intConsumer);

        // 函数式方式：传递的是函数的行为
        testClassCast(2, consumer::accept);
        /**
         * 使用函数式方式传递行为则不会报错，因为传递的是lambda 表达式的行为 (x) -> System.out.println(x);
         * 如果 Consumer<Integer> 类型为 String 则无法自动转换。
         * 可以通过将鼠标移动到 accept 上发现，跳转的是IntConsumer， 而光标放在 :: 上跳转到的是 Consumer
         */
        testClassCast(3, intConsumer::accept);
    }


    private void testClassCast(Integer i, Consumer<Integer> consumer) {
        consumer.accept(i * i);
    }

}

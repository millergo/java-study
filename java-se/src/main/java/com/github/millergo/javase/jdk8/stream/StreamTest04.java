package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 关于流的执行：当我们使用流的多个中间操作时，实际上JDK底层并不是逐个按照顺序执行的，底层是将流的多个"操作"聚合到流框架中，然后一次性完成所有操作。
 * 比如：streams().filter(item -> item > 2).mapToInt(item -> item * 2).filter(item -> item.toUpperCase())....实际上底层并不是
 * 多个for循环操作，而是一个for循环操作。关于流的操作可以理解为，底层有个流框架模版，我们编写的这些函数会填充到底层流框架，然后流框架会与函数代码
 * 和我们自己编写的业务逻辑代码合并成一个代码块然后执行，所以相对于传统（对集合的遍历操作）的方式效率会更高。<br/>
 * <p>
 * 流的中间操作与终止操作的区别：
 * 中间操作：总是会返回一个Stream对象；
 * 终止操作：不会返回一个Stream对象，可能不返回值，也可能返回其他类型的一个值。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/11 08:24:04
 */
public class StreamTest04 {
    @DisplayName("流的执行顺序")
    @Test
    void testStreamExecute() {
        List<String> myFamily = Arrays.asList("Miller", "Vicky", "Mila");
        // 找出集合中第一个长度为6的单词
        // myFamily.stream().mapToInt(item -> item.length()).filter(item -> item == 6).findFirst().ifPresent(System.out::println);
        /*
        从执行结果可以得出程序仅执行了一次 System.out.println(item); 而不是先执行map()的遍历，然后在执行filter()过滤，
        然后执行取出第一个元素，最后执行打印输出。因为流底层其实只执行了一次循环，流框架将lambda表达式与我们自己编写的代码整合
        在一起之后一并执行。这种描述性编程风格与SQL语句及其相似，我们仅需要通过描述性编程的方式告知编译器我们需要做的"行为"，而
        具体如何实施由编译器自动解析并执行。
         */
        myFamily.stream().mapToInt(item -> {
            // 将单词转换为长度
            int length = item.length();

            // 这里应该打印出什么？？？
            System.out.println(item);
            return length;
        }).filter(item -> item == 6).findFirst().ifPresent(System.out::println);
    }

    @DisplayName("流的使用顺序注意事项")
    @Test
    void testDistinct() {
        // 下面这行代码会创建一个无限流，因为distinct操作会去重，而前面的操作获得的数据之后会一直执行distinct，造成无限循环
        // IntStream.iterate(0, (i) -> (i + 1) % 2).distinct().limit(4).forEach(System.out::println);
        // 改成先调用limit取完数之后在去重
        IntStream.iterate(0, (i) -> (i + 1) % 2).limit(4).distinct().forEach(System.out::println);
    }
}

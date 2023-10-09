package com.github.millergo.javase.jdk8.stream;

import com.github.millergo.javase.jdk8.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Steam(流)，是对数据的操作，共分为3部分：
 * 1. 数据源；
 * 2. 0个或多个中间操作；
 * 3. 终止操作(惰性求值)。
 *
 * @author Miller Shan
 */
public class StreamTest {

    @DisplayName("流的声明")
    @Test
    void defineStream() {
        // Create Steam by of()
        Stream stream = Stream.of(3, 5, 7, 1, 9);

        List<String> myFamily = Arrays.asList("Miller", "Mila", "Vicky");
        // Create Stream by stream()
        myFamily.stream();

    }

    @DisplayName("流的惰性求值")
    @Test
    void testStream() {
        // 数据源
        List<Integer> users = Arrays.asList(22, 33, 11, 66, 44);

        // 第一步：创建流
        Stream<Integer> stream = users.stream();

        // 第二步：中间操作
        Stream<Integer> userStream = stream.filter((predicate) -> {
            System.out.println("Steam API 正在操作流！");
            return predicate > 33;
        });

        // 第三步：终止流。不调用终止操作时中间操作不会生效，这叫做“惰性求值”。下面这行代码的执行才会导致上面步骤二真正被执行。
        userStream.forEach(System.out::println);
    }

    @DisplayName("使用流和函数式编程完成两个数相加")
    @Test
    void testStream2() {
        List<Integer> numbers = Arrays.asList(1, 3, 6);
        Optional<Integer> reduce = numbers
                // 获取流
                .stream()
                // map， 映射。将一个值映射为另一个值，其接受 Function 接口作为参数。
                .map((function) -> function * 10)
                // reduce 是一个终止操作，表示 归纳 结果。
                //.reduce((item1, item2) -> item1 + item2);
                .reduce(Integer::sum);
        System.out.println(reduce.get());
    }


}

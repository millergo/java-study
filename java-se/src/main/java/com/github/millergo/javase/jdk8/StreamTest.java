package com.github.millergo.javase.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 流，是对数据的操作。不会改变数据源的数据。
 *
 * @author Miller Shan
 * @since 2023-05-04 18:04:49
 */
public class StreamTest {

    private List<User> users = Arrays.asList(
            new User("AAA", 11, 111.111),
            new User("BBB", 22, 222.111),
            new User("CCC", 33, 333.111),
            new User("DDD", 44, 444.111),
            new User("EEE", 55, 555.111)
    );

    @Test
    public void testStream() {
        // 第一步：创建流
        Stream<User> stream = users.stream();
        // 第二步：中间操作
        Stream<User> employeeStream = stream.filter((x) -> {
            System.out.println("Steam API 正在操作流！");
            boolean b = x.getAge() > 22;
            return b;
        });
        Stream<User> limit = employeeStream.limit(1);

        // 第三步：终止流。不调用终止操作时中间操作不会生效，这叫做“惰性求值”。
        // 下面这行代码的执行才会导致上面步骤二真正被执行。这里我们用limit进行对流截取了，所以执行匹配之后就中断流的操作了
        limit.forEach(System.out::println);
    }

}

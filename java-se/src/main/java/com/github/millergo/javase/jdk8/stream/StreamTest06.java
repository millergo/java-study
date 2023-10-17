package com.github.millergo.javase.jdk8.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stream 的 分组(group by)、与分区(partition by)
 *
 * <p>
 * 流的分组与 SQL 语句中的 group by 概念是相同的，Java 中的流操作很多都是借鉴 SQL 语句中的概念。
 * 流的分区是分组的一种特化，将数据分为两组，true和false。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/14 16:06:43
 */
public class StreamTest06 {
    private User user1 = new User("Miller", 30, 90.0);
    private User user2 = new User("Miller", 50, 80.0);
    private User user3 = new User("Vicky", 50, 80.0);
    private User user4 = new User("Mila", 80, 80.0);
    private List<User> userList = Arrays.asList(user1, user2, user3, user4);

    @DisplayName("流的分组")
    @Test
    void testGroupBy() {
        // group by name.返回参数Map的key根据 groupingBy 参数的类型返回类型决定key的类型。比如getName则返回String类型。
        System.out.println("-------- group by name ---------------");
        Map<String, List<User>> groupByName = userList.stream()
                .collect(Collectors.groupingBy(User::getName));
        groupByName.forEach((item1, item2) -> System.out.println(item1 + ":" + item2));
        System.out.println("-------- group by history ---------------");

        // group by history。getHistory 是Double类型所以返回的map的可以是Double类型
        Map<Double, List<User>> groupByHistory = userList.stream()
                .collect(Collectors.groupingBy(User::getHistory));
        groupByHistory.forEach((item1, item2) -> System.out.println(item1 + ":" + item2));

        System.out.println("-------- group by and count ---------------");
        // 分组并计算数量，类似于SQL语句中的 select name, count(*) from user....
        Map<String, Long> groupByAndCount = userList.stream()
                .collect(Collectors.groupingBy(User::getName, Collectors.counting()));
        groupByAndCount.forEach((item1, item2) -> System.out.println(item1 + ":" + item2));
    }

    @DisplayName("流的分区")
    @Test
    void testPartitionBy() {
        Map<Boolean, List<User>> partitionByMath = userList.stream()
                .collect(Collectors.partitioningBy(item -> item.getMath() >= 50));
        partitionByMath.forEach((item1, item2) -> System.out.println(item1 + ":" + item2));
        partitionByMath.get(true).forEach(System.out::println);
    }

}

@AllArgsConstructor
@Data
class User {
    private String name;
    private Integer math;
    private Double history;
}

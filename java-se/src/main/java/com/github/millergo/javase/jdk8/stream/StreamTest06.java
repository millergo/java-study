package com.github.millergo.javase.jdk8.stream;

import lombok.AllArgsConstructor;

/**
 * Stream 的 分组(group by)、与分区(partition by)
 *
 * <p>
 * 流的分区与 SQL 语句中的 group by 概念是相同的，Java 中的流操作很多都是借鉴 SQL 语句中的概念。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/14 16:06:43
 */
public class StreamTest06 {

}

@AllArgsConstructor
class User {
    private String name;
    private Integer age;
    private Double salary;
}
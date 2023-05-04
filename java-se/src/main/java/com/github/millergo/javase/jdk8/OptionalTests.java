package com.github.millergo.javase.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

public class OptionalTests {
    /**
     * ofNullable测试:返回一个Optional描述的指定值，如果为空，则返回Optional。
     */
    @Test
    public void optionalTest02() {
        User u1 = new User();
        User u2 = new User();
        u2.setName("张三");
        User u3 = null;
        Optional<User> u11 = Optional.ofNullable(u1);
        Optional<User> u12 = Optional.ofNullable(u2);
        Optional<User> u13 = Optional.ofNullable(u3);
        Random random = new Random();
        // 打印空user对象
        System.out.println("u11:" + u11.get());
        // 打印name = 张三的user对象
        System.out.println("u12:" + u12.get().getName());
        // 打印empty
        System.out.println("u13:" + u13.isPresent());
        // 可以抛出自定义异常
//        Optional.ofNullable(u3).orElseThrow(() -> new NullPointerException());
    }
}

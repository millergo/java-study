package com.github.millergo.javase.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Optional 是一个容器对象，里面可以存储任意值，其设计主要是用于避免出现NPE，空指针异常。
 * Optional 提供了几个静态方法用于构造对象：
 * of()：被构造的对象不能为空，否则回抛异常。
 * empty(): 构造一个null对象。
 * ofNullable(): 构造的对象可以为null也可以不为null
 */
public class OptionalTests {


    @DisplayName("Optional of 方法测试")
    @Test
    void testOptionalOfMethod() {
        String str = "Miller";
        Optional<String> optional = Optional.of(str);
        // isPresent() 判断当前值是否为null，不为null则返回true。推荐使用 ifPresent() 替换。
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }
        // ifPresent() 如果 optional 里面值存在则执行具体的 action，否则什么都不执行。
        optional.ifPresent(value -> {
            System.out.println(optional.get());
        });
    }

    @Test
    void testOptionalElseMethod() {
        String str = null;
        Optional<String> optional = Optional.ofNullable(str);
        optional.ifPresent(value -> {
            System.out.println(optional.get());
        });

        System.out.println(optional.orElse("当前值为null，执行orElse()"));

        optional.orElseGet(() -> {
            System.out.println("--------");
            return "";
        });
    }


    /**
     * ofNullable 容器内的对象可以为null也可以不为null内
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
        // 打印空user对象
        System.out.println("u11:" + u11.get());
        // 打印name = 张三的user对象
        System.out.println("u12:" + u12.get().getName());
        // 判断当前对象是否有值
        System.out.println("u12:" + u12.isPresent());
        System.out.println("u13:" + u13.isPresent());
        // 可以抛出自定义异常
//        Optional.ofNullable(u3).orElseThrow(() -> new NullPointerException());

        // 使用Optional 类包装对象，如果对象为空侧返回默认值
        System.out.println(Optional.ofNullable(u1).map(user -> user.getName()).orElse("默认值"));
        System.out.println(Optional.ofNullable(u2).map(user -> user.getName()).orElse("默认值"));
    }
}
@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    private String name;
    private Integer age;
    private Double salary;
}

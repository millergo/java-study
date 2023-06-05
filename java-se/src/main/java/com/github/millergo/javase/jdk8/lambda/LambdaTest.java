package com.github.millergo.javase.jdk8.lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * lambda 表达式（函数式编程）在Java中实际是一个对象，只是这个对象的创建方式可以是通过lambda表达式、方法引用、构造器方式。
 * lambda 语法：通过箭头区分，箭头左边代表方法的参数， 右边代表具体实现。<br>
 * 1: (Type1 param1, Type2 param2,...) -> { statement....} <br>
 * 2: param1, param2 -> {statement...} <br>
 * 3: param1 -> {statement...} <br>
 * 4: () -> {statement...} <br>
 *
 * @see FunctionalInterface
 */
public class LambdaTest {

    @Test
    @DisplayName("lambda")
    void lambdaShouldBeJavaObject() {
        List<String> myFamily = Arrays.asList("Miller", "Mila", "Vicky");
        myFamily.forEach(value -> System.out.println(value));
        // 通过一个引用类型指向一个对象
        Consumer consumer = param -> {
        };
        System.out.println(consumer.getClass());
        System.out.println(consumer.getClass().getSimpleName());
        System.out.println(consumer.getClass().getSuperclass());
        System.out.println(consumer.getClass().getInterfaces()[0]);
    }

    @Test
    void myFunction() {
        MyFunctionInterface myFunctionInterface = (paramOne) -> {
        };
        System.out.println(myFunctionInterface.getClass().getTypeName());
        // 如果lambda代码块只有一行可以省略（）、{}。 默认可以省略类型声明，编译器会自动推断类型。
        MyFunctionInterface<String> myFunctionInterface2 = (String paramOne) -> {
            // 传递行为：将参数转换为大写
            String result = paramOne.toUpperCase();
            System.out.println(result);
        };
        myFunctionInterface2.method("Miller");
    }
}

/**
 * 自定义函数式接口。
 * 函数式编程与面向对象最大的不同在于，函数式编程传递的是行为（具体实现由客户端决定），而面向对象编程传递的是数据（具体实现已预先定义）。
 */
@FunctionalInterface
interface MyFunctionInterface<P> {
    /**
     * 接受一个参数不返回结果
     */
    void method(P p);
}

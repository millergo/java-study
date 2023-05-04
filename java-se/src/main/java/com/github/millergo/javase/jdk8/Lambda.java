package com.github.millergo.javase.jdk8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Lambda 是基于函数式接口进行实现的（类似于内部类）。Lambda是一个匿名函数，可以把Lambda表达式理解为一段可以传递的代码。
 * Lambda 表达式分为左右两边：-> 左边代表方法的参数， ->右边写方法的实现代码。 如果方法只有一条语句那么return和大括号都可以省略
 *
 * @author Miller Shan
 * @since 2023-05-04 18:04:49
 */
public class Lambda {

    @Test
    @DisplayName("使用Lambda表达式完成数据运算")
    public void testLambda() {
        // (x) 表示方法的参数，类型编译器会自动推断.参数列表只有一个参数可以省略参数列表的小括号()
        Integer result = operation(100, (x) -> {
            System.out.println("Hello Lambda");
            return x + 2;
        });
        System.out.println(result);
        // 方法只有一条语句那么return和大括号都可以省略
        Integer result2 = operation(100, (x) -> x - 2);
        System.out.println(result2);

        // 直接使用lambda表达式,实现方法的逻辑
        MyFunction myFunction = (y) -> y * y;
        System.out.println(myFunction.getValue(100));
    }

    // 提供一个方法，但是不提供方法的具体实现
    public Integer operation(Integer num, MyFunction myFunction) {
        return myFunction.getValue(num);
    }
}

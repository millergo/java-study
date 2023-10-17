package com.github.millergo.javase.jdk8.lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * 函数式接口.Function 接口接受一个参数，返回一个值
 *
 * @see java.util.function.Function
 */
public class FunctionInterfaceTest {
    @Test
    @DisplayName("测试Function接口")
    void testFunctionInterface() {
        var functionInterfaceTest = new FunctionInterfaceTest();

        String result = functionInterfaceTest.functionMethod(33, value -> value + " Miller");
        System.out.println(result);
    }

    @Test
    @DisplayName("组合函数")
    void testComposeFunction() {
        var functionInterfaceTest = new FunctionInterfaceTest();

        // 36
        System.out.println(functionInterfaceTest.compose(2, value -> value * value, value2 -> value2 * 3));
        // 12
        System.out.println(functionInterfaceTest.andThen(2, value -> value * value, value2 -> value2 * 3));
    }

    /**
     * @param i        传值参数
     * @param function 行为参数，传递的是行为
     * @return 字符串
     */
    String functionMethod(Integer i, Function<Integer, String> function) {
        return function.apply(i);
    }

    /**
     * 组合函数。Function的compose方法可以将另一个函数作为参数，执行时先执行function2函数，
     * 然后将function2的返回结果作为参数应用到当前函数function1中。
     *
     * @param a         参数
     * @param function1 行为1
     * @param function2 行为2
     * @return 整型
     */
    Integer compose(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function1.compose(function2).apply(a);
    }

    /**
     * 组合函数。andThen为先执行自身，然后在执行function2。与compose相反。
     *
     * @param a
     * @param function1
     * @param function2
     * @return
     */
    Integer andThen(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function1.andThen(function2).apply(a);
    }

}

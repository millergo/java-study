package com.github.millergo.javase.thread;

import java.util.concurrent.Callable;

/**
 * JDK 1.5 新增Callable接口，用于实现带返回值的多线程。 Thread 类或 Runnable 接口，两种方式创建的线程是属于”三无产品“：
 * 没有参数、没有返回值、没办法抛出异常。
 * 参考：https://www.cnblogs.com/FraserYu/p/13277747.html
 **/
public class CallableTest {

    public static void main(String[] args) {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            System.out.println("Hello callable");
            return "Hello";
        };
        System.out.println(callable);
    }
}

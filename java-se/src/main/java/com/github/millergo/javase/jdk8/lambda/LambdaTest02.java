package com.github.millergo.javase.jdk8.lambda;

/**
 * Lambada 与 匿名内部类的区别
 * <p>
 * Lambada 表达式与匿名内部类在语法、使用上有很多类似的地方，lambda表达式能完成很多内部类的功能，
 * 但是它们之间有本质上的区别，Lambda表达式是一项新的技术，而不是基于内部类的一个实现，或语法糖。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 10:27:07
 */
public class LambdaTest02 {
    // LambdaTest02@38a17ecf，打印结果lambda表达式并不是匿名内部类，lambda的作用域范围是这个类本身
    Runnable r1 = () -> System.out.println(this);
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            // LambdaTest02$1@30b11b29， Java匿名内部类格式classname $X, 而可以在硬盘上看到确实生成了一个LambdaTest02$1.class文件
            System.out.println(this);
        }
    };

    public static void main(String[] args) {
        LambdaTest02 lambdaTest02 = new LambdaTest02();

        Thread t1 = new Thread(lambdaTest02.r1);
        t1.start();
        System.out.println("-------------");

        Thread t2 = new Thread(lambdaTest02.r2);
        t2.start();

        // System.out.println(lambdaTest02);
    }
}

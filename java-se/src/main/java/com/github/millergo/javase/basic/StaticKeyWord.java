package com.github.millergo.javase.basic;

/**
 * static 关键字。
 *
 * @author Miller Shan
 * @since 2023-05-04 18:04:49
 */
public class StaticKeyWord {
    /*
    静态代码块会在类加载之后自动执行一次，而且也只会执行一次。
    静态代码块里面只能用于成员变量的初始化，而不能定义方法
     */
    static {
        System.out.println("hello");
//        public void test(){}
    }
}

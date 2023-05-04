package com.github.millergo.javase.jdk8;

public class LambdaTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Multi Thread...");
            }
        };
        runnable.run();
        //使用Lambda。可以发现Lambda就像一段可以传递的代码，有些像是方法的参数传递
        Runnable runnable1 = () -> System.out.println("Hello Lambda...");
        runnable1.run();
    }
}

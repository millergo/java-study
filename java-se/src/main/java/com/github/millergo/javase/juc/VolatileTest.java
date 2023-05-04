package com.github.millergo.javase.juc;


import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

/**
 * Volatile 关键字作用：被volatile修饰的成员变量不管哪个线程来访问都是在内存中是直接访问，而不会去L1缓存中访问。
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 相较于 synchronized 是一种较为轻量级的同步策略。
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性“。
 **/
public class VolatileTest {

    @Test
    public void testVolatile() {
        ThreadDemo threadDemo = new ThreadDemo();
        Thread thread = new Thread(threadDemo);
        thread.start();

        // while循环是系统非常底层的执行语句，执行效率会非常高，默认直接从L1缓存中取数据
        while (true) {
            if (threadDemo.isFlag()) {
                System.out.println("------------------");
                break;
            }
        }
    }

    @Getter
    @Setter
    class ThreadDemo implements Runnable {
        // 当多个线程同时访问共享数据时可能会出现数据不同步的问题。因为默认每个线程都会有一份缓存数据，会优先从缓存数据取数据，而不是从物理内存中读取数据。
        private volatile boolean flag = false;

        @Override
        public void run() {

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            flag = true;
            System.out.println("flag=" + isFlag());
        }
    }
}

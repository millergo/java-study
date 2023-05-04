package com.github.millergo.javase.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一、用于解决多线程安全问题的方式：
 *
 * @author: Miller
 * <p>
 * synchronized:隐式锁
 * 1. 同步代码块
 * <p>
 * 2. 同步方法
 * <p>
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 **/
public class LockTest {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();

        System.out.println("线程执行完毕");
    }

    static class Ticket implements Runnable {

        private int tick = 100;

        private Lock lock = new ReentrantLock();

        @Override
        public void run() {
            while (true) {
                // 如果没有获取到锁就抛出异常，最终释放锁肯定是有问题的，因为还未曾拥有锁谈何释放锁呢.
                // 上锁(建议写在try外面)
                lock.lock();
                try {
                    if (tick > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                        }
                        System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                    }
                } finally {
                    // 释放锁(建议写在finally里面保证能被执行)
                    lock.unlock();
                    if (tick == 0) {
                        break;
                    }
                }
            }
        }

    }
}

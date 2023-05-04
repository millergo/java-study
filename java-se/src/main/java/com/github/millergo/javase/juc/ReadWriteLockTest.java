package com.github.millergo.javase.juc;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁（也叫乐观锁）
 * 写写/读写  需要互斥
 * 读读 不需要互斥
 *
 * @author Miller
 **/
public class ReadWriteLockTest {


    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        new Thread(() -> readWriteLockDemo.setNumber(new Random().nextInt(100) + 1), "Write").start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> readWriteLockDemo.getNumber()).start();
        }
    }

    static class ReadWriteLockDemo {
        private int number = 0;
        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


        public void getNumber() {
            // 如果没有获取到锁就抛出异常，最终释放锁肯定是有问题的，因为还未曾拥有锁谈何释放锁呢.
            // 上锁(建议写在try外面)
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " : " + number);
            } finally {
                // 释放锁(建议写在finally里面保证能被执行)
                readWriteLock.readLock().unlock();
            }
        }

        public void setNumber(Integer number) {
            try {
                readWriteLock.writeLock().lock();
                System.out.println(Thread.currentThread().getName());
                this.number = number;
            } finally {
                readWriteLock.writeLock().unlock();
            }

        }

    }
}

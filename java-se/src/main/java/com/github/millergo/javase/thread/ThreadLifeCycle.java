package com.github.millergo.javase.thread;

import org.junit.jupiter.api.Test;

/**
 * https://dayarch.top/p/java-thread-life-cycle.html </br>
 * Java语言中将通用线程状态的可运行状态和运行状态合并为 Runnable，
 * 将休眠状态细分为三种 (BLOCKED/WAITING/TIMED_WAITING); 反过来理解这句话，就是这三种状态在操作系统的眼中都是休眠状态，同样不会获得CPU使用权
 **/
public class ThreadLifeCycle extends Thread {

    /**
     * 获取线程状态.NEW->Runnable
     */
    @Test
    public void testThreadStatus() {
        Thread thread = new Thread(() -> {
        });
        System.out.println("start之前线程状态:" + thread.getState());
        thread.start();
        System.out.println("start之后线程状态:" + thread.getState());
    }

    /**
     * RUNNABLE 与 BLOCKED状态转换
     * 当且仅有（just only）一种情况会从 RUNNABLE 状态进入到 BLOCKED 状态，就是线程在等待 synchronized 内置隐式锁；
     * 如果等待的线程获取到了 synchronized 内置隐式锁，也就会从 BLOCKED 状态变为 RUNNABLE 状态了
     */
    @Test
    public void testThreadStatusFormRunnableToBlocked() throws InterruptedException {
        Thread t1 = new Thread(new DemoThreadB());
        Thread t2 = new Thread(new DemoThreadB());

        t1.start();
        t2.start();

        Thread.sleep(1000);
        // 线程1启动之后会进入死循环，并且持有对象的锁，使代码块一直处于同步状态。此时线程2的状态就会一直是Blocked状态
        System.out.println((t2.getState()));
        System.exit(0);
    }

    /**
     * RUNNABLE 与 WAITING 状态转换
     * 调用不带时间参数的等待API，就会从RUNNABLE状态进入到WAITING状态；当被唤醒就会从WAITING进入RUNNABLE状态
     */
    @Test
    public void testThreadStatusFromRunnableToWaiting() throws InterruptedException {
        Thread main = Thread.currentThread();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            System.out.println(main.getState());
        });
        thread2.start();
        System.out.println("Thread2:" + thread2.getState());
        thread2.join();
    }

    /**
     * RUNNABLE与 TIMED-WAITING 状态转换
     * 调用带时间参数的等待API，自然就从 RUNNABLE 状态进入 TIMED-WAITING 状态；当被唤醒或超时时间到就会从TIMED_WAITING进入RUNNABLE状态
     */
    @Test
    public void testThreadStatusFromRunnableToTimeWaiting() throws InterruptedException {
        Thread thread3 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // 为什么要调用interrupt方法？
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        thread3.start();

        Thread.sleep(1000);
        System.out.println(thread3.getState());
    }

    /**
     * 线程执行完自然就到了 TERMINATED 状态了
     */
    @Test
    public void testThreadStatusFromRunnableToTerminated() throws InterruptedException {
        Thread thread = new Thread(() -> {
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println(thread.getState());
    }
}

class DemoThreadB implements Runnable {

    @Override
    public void run() {
        commonResource();
    }

    // 必须是静态方法，这样所有类的实例就都会共享此方法，线程1执行次方法是会持有此方法的锁，线程2执行的时候会等待此方法的锁释放才能执行。
    public static synchronized void commonResource() {
        while (true) {

        }
    }
}

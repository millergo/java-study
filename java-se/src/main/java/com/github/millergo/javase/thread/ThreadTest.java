package com.github.millergo.javase.thread;

/**
 * Thread 实现资源共享
 * 1.start（）方法来启动线程，真正实现了多线程运行。这时无需等待run方法体代码执行完毕，可以直接继续执行下面的代码；
 * 通过调用Thread类的start()方法来启动一个线程， 这时此线程是处于就绪状态，并没有运行。 然后通过此Thread类调用方法run()来完成其运行操作的，
 * 这里方法run()称为线程体，它包含了要执行的这个线程的内容，Run方法运行结束， 此线程终止。然后CPU再调度其它线程.
 * 2. runnable其实相对于一个task，并不具有线程的概念，如果你直接去调用runnable的run，其实就是相当于直接在主线程中执行了一个函数而已，并未开启线程去执行，
 **/
public class ThreadTest {

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        // 因为这里只只实例化了一个t1对象，然后是通过启动3个线程去操作同一个对象，所以其实操作的是同一个对象，一个对象数据当然只有一份。
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
        new Thread(t1, "线程3").start();
    }
}

class MyThread extends Thread {
    private int total = 10;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // 使用代码块来进行上锁，以保障代码块的执行是同步
            synchronized (this) {
                if (total > 0) {
                    try {
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.total--));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
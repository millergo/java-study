package com.github.millergo.javase.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 多线程执行多任务例子。https://www.cnblogs.com/FraserYu/p/13277747.html </br>
 * 泡茶的列子：
 * 洗水壶 1 分钟
 * 烧开水 15 分钟
 * 洗茶壶 1 分钟
 * 洗茶杯 1 分钟
 * 拿茶叶 2 分钟
 * 最终泡茶：如果串行总共需要 20 分钟，但很显然在烧开水期间，我们可以洗茶壶/洗茶杯/拿茶叶
 * 使用多线完成：T1完成洗水壶、烧开水；T2完成洗茶壶/洗茶杯/拿茶叶；当T1完成烧开水任务后获取T2的执行状态，如果T2完成了则立马执行泡茶任务。
 * 这样总共需要 16 分钟，节约了 4分钟时间，
 **/
@Slf4j
public class MakeTeaExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 创建线程2的FutureTask
        FutureTask<String> ft2 = new FutureTask<String>(new T2Task());
        // 创建线程1的FutureTask
        FutureTask<String> ft1 = new FutureTask<String>(new T1Task(ft2));

        executorService.submit(ft1);
        executorService.submit(ft2);

        executorService.shutdown();
    }

    @Slf4j
    static class T1Task implements Callable<String> {

        private FutureTask<String> ft2;

        public T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            log.info("T1:洗水壶...");
            TimeUnit.SECONDS.sleep(1);

            log.info("T1:烧开水...");
            TimeUnit.SECONDS.sleep(15);
            // 当拿到T2的线程执行结果之后即可立马执行线程1，这样两个线程协同完成一件事件，提高了效率
            String t2Result = ft2.get();
            log.info("T1 拿到T2的 {}， 开始泡茶", t2Result);
            return "T1: 上茶！！！";
        }
    }

    @Slf4j
    static class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            log.info("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            log.info("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "福鼎白茶";
        }
    }
}

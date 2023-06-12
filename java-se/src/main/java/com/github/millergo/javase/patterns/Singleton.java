package com.github.millergo.javase.patterns;

import java.util.Objects;

/**
 * Java 实现单例模式
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023-06-12 10:50:23
 */
public class Singleton {
    /**
     * 使用 {@code volatile} 关键字保证多线程环境下数据的可见性，每次使用它都到主存中进行读取，而不从缓存读取数据。
     */
    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getSingleton() {

        if (Objects.isNull(singleton)) {
            // 对类对象进行加锁
            synchronized (Singleton.class) {
                if (Objects.isNull(singleton)) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }


}

package com.github.millergo.javase.thread;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
https://dayarch.top/p/java8-completablefuture-tutorial.html
 **/
public class CompletableFutureTest {

    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = new CompletableFuture();

        Object o = completableFuture.get();
        completableFuture.complete("Future's Result Here Manually");
        System.out.println(o);
    }
}

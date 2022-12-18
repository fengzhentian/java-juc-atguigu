package com.atguigu.tl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyData {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocal.set(threadLocal.get() + 1);
    }
}

/**
 * ThreadLocal案例
 *
 * @author jingjq
 * @version 1.0
 * @date 2022-12-18 11:47
 */
public class ThreadLocalDemo2 {
    public static void main(String[] args) {
        MyData myData = new MyData();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    try {
                        Integer beforeInteger = myData.threadLocal.get();
                        myData.add();
                        Integer afterInteger = myData.threadLocal.get();
                        System.out.println(Thread.currentThread().getName() + ", beforeInteger = " + beforeInteger + ", afterInteger = " + afterInteger);
                    } finally {
                        myData.threadLocal.remove();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}

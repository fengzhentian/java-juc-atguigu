package com.atguigu;

public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            // 打印子线程名称、是否守护线程
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        // 设置守护线程
        aa.setDaemon(true);
        aa.start();

        // 打印主线程名称
        System.out.println(Thread.currentThread().getName() + " over");

    }
}

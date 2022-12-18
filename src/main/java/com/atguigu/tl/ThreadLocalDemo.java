package com.atguigu.tl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 资源类
 */
class House {

    int saleCount = 0;

    public synchronized void saleHouse() {
        saleCount++;
    }

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void saleVolumeByThreadLocal() {
        saleVolume.set(saleVolume.get() + 1);
    }

}

/**
 * ThreadLocal案例
 *
 * @author jingjq
 * @version 1.0
 * @date 2022-12-17 22:23
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        House house = new House();
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    int size = new Random().nextInt(5) + 1;
                    for (int j = 0; j < size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖出了" + house.saleVolume.get() + "套房子");
                } finally {
                    countDownLatch.countDown();
                    house.saleVolume.remove();
                }
            }, "Thread-" + i).start();
        }

        countDownLatch.await();

        System.out.println("最终卖出了" + house.saleCount + "套房子");

    }
}

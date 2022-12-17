package com.atguigu.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 资源类
 */
class BankAccount {

    String bankName = "中国银行";

    /**
     * 余额
     */
    public volatile int money = 0;

    public synchronized void add() {
        money++;
    }

    static final AtomicIntegerFieldUpdater<BankAccount> updater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public void transMoney(BankAccount bankAccount) {
        updater.getAndIncrement(bankAccount);
    }

}


/**
 * 对象的属性修改原子类
 *
 * @author jingjq
 * @version 1.0
 * @date 2022-12-17 22:07
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
//                        bankAccount.add();
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();

        System.out.println(bankAccount.money);
    }
}

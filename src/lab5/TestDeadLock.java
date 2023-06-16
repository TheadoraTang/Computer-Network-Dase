package lab5;

public class TestDeadLock {
    public static void main(String[] args) throws InterruptedException {
        PlusMinus plusMinus1 = new PlusMinus();
        plusMinus1.num = 1000;
        PlusMinus plusMinus2 = new PlusMinus();
        plusMinus2.num = 1000;
        MyThread thread1 = new MyThread(plusMinus1, plusMinus2, 1);
        MyThread thread2 = new MyThread(plusMinus1, plusMinus2, 2);
        MyThread thread3 = new MyThread(plusMinus2, plusMinus1, 3); // 新增第三个线程
        Thread t1 = new Thread(thread1);
        Thread t2 = new Thread(thread2);
        Thread t3 = new Thread(thread3); // 新增第三个线程的线程对象
        t1.start();
        t2.start();
        t3.start(); // 启动第三个线程
        t1.join();
        t2.join();
        t3.join(); // 等待三个线程结束
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        if (tid == 1) {
            synchronized (pm1) {
                System.out.println("thread" + tid + "正在占用 plusMinus1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread" + tid + "试图继续占用 plusMinus2");
                System.out.println("thread" + tid + "等待中...");
                synchronized (pm2) {
                    System.out.println("thread" + tid + "成功占用了 plusMinus2");
                }
            }
        } else if (tid == 2) {
            synchronized (pm2) {
                System.out.println("thread" + tid + "正在占用 plusMinus2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread" + tid + "试图继续占用 plusMinus1");
                System.out.println("thread" + tid + "等待中...");
                synchronized (pm1) {
                    System.out.println("thread" + tid + "成功占用了 plusMinus1");
                }
            }
        } else if (tid == 3) { // 新增第三个线程的互斥代码
            synchronized (pm1) {
                System.out.println("thread" + tid + "正在占用 plusMinus1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread" + tid + "试图继续占用 plusMinus2");
                System.out.println("thread" + tid + "等待中...");
                synchronized (pm2) {
                    System.out.println("thread" + tid + "成功占用了 plusMinus2");
                }
            }
        }
    }

    MyThread(PlusMinus _pm1, PlusMinus _pm2, int _tid) {
        this.pm1 = _pm1;
        this.pm2 = _pm2;
        this.tid = _tid;
    }

    PlusMinus pm1;
    PlusMinus pm2;
    int tid;
}

class PlusMinus {
    int num;

    synchronized void increment() {
        num++;
    }

    synchronized void decrement() {
        num--;
    }
}

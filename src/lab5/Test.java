package lab5;

import java.util.LinkedList;
import java.util.Queue;
public class Test {
    public static void main(String[] args) {
        ProductFactory pf = new ProductFactory();
        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        String s = pf.getProduct();
                        System.out.println("t1 get product: " + s);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        String s = "product";
                        pf.addProduct(s);
                        System.out.println("t2 add product: " + s);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t3 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        String s = pf.getProduct();
                        System.out.println("t3 get product: " + s);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t4 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        String s = pf.getProduct();
                        System.out.println("t4 get product: " + s);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
class ProductFactory {
    Queue<String> productQueue = new LinkedList<>();
    public synchronized void addProduct(String s) {
        productQueue.add(s);
        this.notifyAll(); // 唤醒所有在this锁等待的线程
    }
    public synchronized String getProduct() throws InterruptedException {
//        while (productQueue.isEmpty()) {
// 释放this锁
            this.wait();
// 重新获取this锁
        //}
        return productQueue.remove();
    }
}

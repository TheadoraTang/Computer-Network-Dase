package lab5;
//
//class PlusMinusOne {
//    volatile int num;
//    public void plusOne() {
//        synchronized (this) {
//            this.num = this.num + 1;
//            printNum();
//        }
//    }
//    public void minusOne() {
//        synchronized (this) {
//            this.num = this.num - 1;
//            printNum();
//        }
//    }
//    public void printNum() {
//        System.out.println("num = " + this.num);
//    }
//}
//public class TestInteract {
//    public static void main(String[] args) {
//        PlusMinusOne pmo = new PlusMinusOne();
//        pmo.num = 50;
//        Thread t1 = new Thread() {
//            public void run() {
//                while (true) {
//                    while (pmo.num == 1) {
//                        continue;
//                    }
//                    pmo.minusOne();
//                    pmo.minusOne();
//                    pmo.minusOne();
//                    pmo.minusOne();
//                    pmo.minusOne();
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        t1.start();
//        Thread t2 = new Thread() {
//            public void run() {
//                while (true) {
//                    pmo.plusOne();
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        t2.start();
//    }
//}

class PlusMinusOne {
    volatile int num;
    public synchronized void plusOne() throws InterruptedException {
        if (num == 1) {
            wait();
        }
        this.num = this.num + 1;
        printNum();
        notifyAll();
    }

    public synchronized void minusOne() throws InterruptedException {
        if (num == 0) {
            wait();
        }
        this.num = this.num - 1;
        printNum();
        notifyAll();
    }

    public void printNum() {
        System.out.println("num = " + this.num);
    }
}
public class TestInteract {
    public static void main(String[] args) {
        PlusMinusOne pmo = new PlusMinusOne();
        pmo.num = 50;

        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        pmo.minusOne();
                        pmo.minusOne();
                        pmo.minusOne();
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        pmo.plusOne();
                        pmo.minusOne();
                        pmo.minusOne();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();
    }
}

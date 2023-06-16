//package lab5;
//import java.util.Random;
//import java.util.ArrayList;
//public class TestMax {
//    public static void main(String[] args) throws InterruptedException {
//        Res res = new Res();
//        int threadNum = 3;
//        Thread[] threads = new Thread[threadNum];
//        for (int i = 0; i < threadNum; i++) {
//            threads[i] = new Thread(new MyThread(i, res));
//        }
//        for (int i = 0; i < threadNum; i++) {
//            threads[i].start();
//        }
//        for (int i = 0; i < threadNum; i++) {
//            threads[i].join();
//        }
//        System.out.println(res.max_idx);
//    }
//}
//
//class MyThread implements Runnable {
//    static int[] seeds = {1234567, 2345671, 3456712};
//
//    MyThread(int id, Res _res) {
//        Random r = new Random(seeds[id]);
//        list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add(r.nextInt(10000));
//        }
//        res = _res;
//    }
//
//    @Override
//    public void run() {
//        synchronized (res) {
//            int max = Integer.MIN_VALUE;
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i) > max) {
//                    max = list.get(i);
//                    res.max_idx = i;
//                }
//            }
//        }
//    }
//
//    ArrayList<Integer> list;
//    Res res;
//}
//
//class Res {
//    int max_idx;
//}
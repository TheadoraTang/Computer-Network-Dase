package lab4;

class ThreadTest extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId());
        }
    }
    public static void main(String[] args){
        ThreadTest mthread1 = new ThreadTest();
        ThreadTest mthread2 = new ThreadTest();
        mthread1.start();
        mthread2.start();
    }
}
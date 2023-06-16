package lab4;

public class ThreadTest04 extends Thread{
    @Override
    public void run(){
        int worktime = 0;
        while(true){
            System.out.println("助教在教室的第"+ worktime +"秒");
            try{
                Thread.currentThread().sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            worktime ++;
        }
    }
    public static void main(String[] args) throws InterruptedException{
        ThreadTest04 thread = new ThreadTest04();
        thread.setDaemon(true);
        thread.start();
        for(int i = 0; i < 10; i++){
            thread.sleep(1000);
            System.out.println("同学们正在上课");
            if(i == 9){
                System.out.println("同学们下课了");
            }
        }
    }
}


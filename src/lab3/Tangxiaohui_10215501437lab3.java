package lab3;

import java.util.Date;

class Watch {
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void start() {
        this.startTime = new Date();
    }

    public void stop() {
        this.endTime = new Date();
    }
}

class StopWatch extends Watch {
    public long getElapsedTime() {
        return this.getEndTime().getTime() - this.getStartTime().getTime();
    }
}

public class Tangxiaohui_10215501437lab3 {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        long elapsedTime = stopWatch.getElapsedTime();
        System.out.println(elapsedTime);
    }
}

package appguru.timer;

public class ScheduleQuit implements appguru.timer.TimeListener {
    public ScheduleQuit() {
        super();
    }
    
    public void invoke(long j) {
        System.gc();
    }
}

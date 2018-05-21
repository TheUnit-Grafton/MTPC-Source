package appguru.timer;

public class Event {
    public long lastCall;
    public long intervall;
    public appguru.timer.TimeListener invoke;
    
    public Event(long j, long j0, appguru.timer.TimeListener a) {
        super();
        this.lastCall = j;
        this.intervall = j0;
        this.invoke = a;
    }
    
    public void addIntervall() {
        this.lastCall = this.lastCall + this.intervall;
    }
    
    public void setLastCall(long j) {
        this.lastCall = j;
    }
    
    public void call(long j) {
        this.invoke.invoke(j);
    }
}

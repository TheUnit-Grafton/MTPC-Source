package appguru.timer;

import appguru.info.Console;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer extends Thread {
    public long currentTime;
    public long applicationTime;
    public long startTime;
    public java.util.ArrayList events;
    public long[] measurements;
    public boolean accurate;
    
    public Timer(boolean b) {
        super();
        this.setName("AGE Scheduler");
        this.accurate = b;
        this.applicationTime = 0L;
        this.startTime = System.currentTimeMillis();
        this.currentTime = this.startTime;
        this.events = new java.util.ArrayList();
        this.measurements = new long[127];
        int i = 0;
        while(i < 127) {
            this.measurements[i] = -1L;
            i = (byte)(i + 1);
        }
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "INITIALISATION");
    }
    
    public void addEvent(long j, appguru.timer.TimeListener a) {
        this.events.add((Object)new appguru.timer.Event(this.applicationTime, j, a));
    }
    
    public void terminate() {
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "SHUTDOWN");
        this.stop();
    }
    
    public byte startTimeMeasure() {
        int i = 0;
        while(i < 127) {
            if (this.measurements[i] == -1L) {
                this.measurements[i] = this.applicationTime;
                return (byte)i;
            }
            i = (byte)(i + 1);
        }
        appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "NO MEASUREMENT SLOTS AVAILABLE");
        return (byte)(-1);
    }
    
    public long endTimeMeasure(byte a) {
        int i = a;
        if (a <= 126 && i >= 0) {
            long j = this.applicationTime - this.measurements[i];
            this.measurements[i] = -1L;
            if (j == -1L) {
                return -1L;
            }
            return j;
        }
        appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "INVALID INDEX : NO SUCH MEASUREMENT");
        return -1L;
    }
    
    public void printTimeMeasure(byte a) {
        long j = this.endTimeMeasure(a);
        int i = (j < -1L) ? -1 : (j == -1L) ? 0 : 1;
        int i0 = a;
        if (i == 0) {
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "INVALID INDEX : NO SUCH MEASUREMENT");
        }
        String s = ((Object)this).getClass().getName();
        StringBuilder a0 = new StringBuilder().append("MEASUREMENT ");
        int i1 = (byte)(i0 + 1);
        appguru.info.Console.infoMessage(s, a0.append(Byte.toString((byte)i1)).append(" : ").append(Long.toString(j)).append(" MS").toString());
    }
    
    @Override
    public synchronized void run() {
        while(true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Console.errorMessage(Timer.class.getName(), "INTERRUPTED");
            }
            this.currentTime = System.currentTimeMillis();
            this.applicationTime = this.currentTime - this.startTime;
            int i = 0;
            while(i < this.events.size()) {
                appguru.timer.Event a = (appguru.timer.Event)this.events.get(i);
                if (this.applicationTime - a.lastCall > a.intervall) {
                    if (this.accurate) {
                        a.addIntervall();
                    } else
                    {
                        a.setLastCall(this.applicationTime);
                    }
                    a.call(this.currentTime);
                }
                i = i + 1;
            }
        }
    }
}

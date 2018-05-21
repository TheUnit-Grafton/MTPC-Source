package appguru.timer;

public class SchedulerTest implements appguru.timer.TimeListener {
    public SchedulerTest() {
        super();
    }
    
    public void invoke(long jja) {
        Runtime runtime=Runtime.getRuntime();
        long j = runtime.freeMemory();
        long j0 = runtime.totalMemory();
        long j1 = (j0 - j) / 1048576L;
        long j2 = (j0 - j) % 1048576L / 1024L;
        long j3 = (j0 - j) % 1024L;
        String s = appguru.Main.class.getName();
        Object[] a0 = new Object[3];
        a0[0] = j3;
        a0[1] = j2;
        a0[2] = j1;
        appguru.info.Console.infoMessage(s, String.format("RAM used : Byte : %d, Kilobyte : %d, Megabyte : %d", a0));
        System.gc();
    }
}

package appguru.info;

public class Console {
    final public static java.io.PrintStream INFO;
    final public static java.io.PrintStream ERROR;
    public static java.io.PrintStream ps;
    final public static String esc;
    final public static int c = 0;
    final public static int BLACK = 0;
    final public static int RED = 1;
    final public static int GREEN = 2;
    final public static int YELLOW = 3;
    final public static int BLUE = 4;
    final public static int MAGENTA = 5;
    final public static int CYAN = 6;
    final public static int WHITE = 7;
    public static String ending;
    public static int fg;
    public static int bg;
    public static boolean LOG;
    public static String LOGFILE;
    public static String LOGVAL;
    
    public Console() {
        super();
    }
    
    public static void setEnding(String s) {
        ending = s;
    }
    
    public static void reset() {
        ps.print(new StringBuilder().append(esc).append("[0m").toString());
    }
    
    public static void setColor(int i) {
        fg = i + 30;
        ps.print(new StringBuilder().append("\u001b[").append(Integer.toString(i + 30)).append("m").toString());
    }
    
    public static void setBackgroundColor(int i) {
        bg = i + 40;
        ps.print(new StringBuilder().append("\u001b[").append(Integer.toString(i + 40)).append("m").toString());
    }
    
    public static void setOutput(java.io.PrintStream a) {
        ps = a;
    }
    
    public static void print(Object a) {
        String s = a.toString();
        LOGVAL = new StringBuilder().append(LOGVAL).append(s).toString();
        ps.print(s);
    }
    
    public static void printLn(Object a) {
        String s = new StringBuilder().append(a.toString()).append(ending).toString();
        LOGVAL = new StringBuilder().append(LOGVAL).append(s).toString();
        ps.print(s);
    }
    
    public static void printLn() {
        LOGVAL = new StringBuilder().append(LOGVAL).append("\n").toString();
        ps.print("\n");
    }
    
    public static String formatClassName(String s) {
        return s.replace((CharSequence)(Object)".", (CharSequence)(Object)" : ").toUpperCase();
    }
    
    public static void message(int i, String s, String s0, String s1) {
        appguru.info.Console.setColor(i);
        appguru.info.Console.printLn((Object)new StringBuilder().append(s).append(" : ").append(appguru.info.Console.formatClassName(s0)).append(" : ").append(s1).toString());
    }
    
    public static void errorMessage(String s, String s0) {
        appguru.info.Console.message(1, "ERROR", s, s0);
    }
    
    public static void warningMessage(String s, String s0) {
        appguru.info.Console.message(1, "WARNING", s, s0);
    }
    
    public static void infoMessage(String s, String s0) {
        appguru.info.Console.message(4, "INFO", s, s0);
    }
    
    public static void successMessage(String s, String s0) {
        appguru.info.Console.message(2, "SUCCESS", s, s0);
    }
    
    public static void saveLog() {
        java.io.File a = new java.io.File(LOGFILE);
        boolean b = a.exists();
        label1: {
            if (b) {
                break label1;
            }
            label0: {
                try {
                    a.createNewFile();
                    appguru.info.Console.successMessage(appguru.info.Console.class.getName(), "CREATED LOG FILE");
                } catch(java.io.IOException ignoredException) {
                    break label0;
                }
                break label1;
            }
            appguru.info.Console.errorMessage(appguru.info.Console.class.getName(), "COULD NOT CREATE FILE - ABORTING LOG SAVING");
            return;
        }
        try {
            java.io.BufferedWriter a0 = new java.io.BufferedWriter((java.io.Writer)new java.io.FileWriter(a));
            a0.write(LOGVAL);
            a0.close();
            appguru.info.Console.successMessage(appguru.info.Console.class.getName(), "SAVED LOG FILE");
        } catch(java.io.IOException ignoredException0) {
            appguru.info.Console.errorMessage(appguru.info.Console.class.getName(), "COULD NOT WRITE TO FILE - ABORTING LOG SAVING");
        }
    }
    
    static {
        INFO = System.out;
        ERROR = System.err;
        ps = INFO;
        char[] a = new char[1];
        a[0] = (char)27;
        esc = new String(a);
        ending = "\n";
        fg = 30;
        bg = 40;
        LOG = true;
        LOGFILE = "res/logfile.txt";
        LOGVAL = "";
    }
}

package appguru.db;

public class Clause {

    public boolean first;
    public boolean string;
    public boolean like;
    public boolean not;
    public boolean or;
    public String category;
    public String op;
    public String compareTo;
    final public static String[] NUMERICAL_OPS = new String[]{"<", ">", "<=", "=", ">="};
    final public static String[] STRING_OPS = new String[]{"LIKE", "GLOB", "="};
    final public static String[] STRING_DSP = new String[]{"matches (case-insensitive)", "matches (case-sensitive)", "="};
    final public static char ESCAPE = '§';
    final public static char[] GLOB_WC = {'?', '*'};
    final public static char[] LIKE_WC = {'_', '%'};
    final public static char[] WC = new char[]{'_', '*'};

    public Clause(String s, String s0, String s1) {
        super();
        this.string = false;
        this.not = false;
        this.or = false;
        this.category = s;
        this.op = s0;
        this.compareTo = s1;
    }

    public Clause(boolean first, boolean string, boolean not, boolean or, String s, String s0, String s1) {
        super();
        this.string = false;
        this.first = first;
        this.string = string;
        if (string) {
            int i = 0;
            while (i < STRING_DSP.length) {
                {
                    if (!s0.equals((Object) STRING_DSP[i])) {
                        i = i + 1;
                        continue;
                    }
                    break;
                }
            }
            this.op = STRING_OPS[i];
        } else {
            this.op = s0;
        }
        this.not = not;
        this.or = or;
        this.category = s;
        this.compareTo = s1.replace("'","''");
        this.like = this.op.equals((Object) "LIKE");
    }

    public String toNiceString() {
        String s = (this.first) ? "" : (this.or) ? "or " : "and ";
        if (this.not) {
            s = s + "not ";
        }
        if (this.op.equals((Object) "=")) {
            return s + this.category + " = " + this.compareTo;
        }
        if (!this.string) {
            return s + this.category + " " + this.op + " " + this.compareTo;
        }
        int i = 0;
        while (i < STRING_OPS.length) {
            if (STRING_OPS[i].equals((Object) this.op)) {
                return s + this.category + " " + STRING_DSP[i] + " " + this.compareTo;
            }
            i = i + 1;
        }
        return "Sorry, an impossible error must have occured. Please contact the developer.";
    }

    public String toString() {
        String s = null;
        if (this.string) {
            if (this.op.equals((Object) "=")) {
                s = "";
            } else {
                if (this.like) {
                    this.compareTo = this.compareTo.replace("%", "§%");
                } else {
                    this.compareTo = this.compareTo.replace("?", "§?");
                }
                StringBuilder a = new StringBuilder(this.compareTo);
                s = "";
                int i = 0;
                int i0 = -1;
                label4:
                while (true) {
                    i0 = i0 + 1;
                    if (i0 >= this.compareTo.length()) {
                        break;
                    }
                    int i1 = (i0 == 0) ? 48 : this.compareTo.charAt(i0 - 1);
                    int i2 = this.compareTo.charAt(i0);
                    {
                        label0:
                        if (i1 == 167) {
                            label2:
                            if (this.like) {
                                label3:
                                {
                                    if (i2 == 95) {
                                        break label3;
                                    }
                                    if (i2 != 37) {
                                        break label2;
                                    }
                                }
                                s = " ESCAPE '§'";
                                continue;
                            }
                            label1:
                            {
                                if (i2 == 42) {
                                    break label1;
                                }
                                if (i2 != 63) {
                                    break label0;
                                }
                            }
                            a.setCharAt(i0 - 1 + i, (char) 91);
                            a.insert(i0 + 1 + i, "]");
                            i++;
                            continue;
                        }
                        int i3 = 0;
                        while (true) {
                            int i4 = WC[i3];
                            if (i4 != i2) {
                                i3 = i3 + 1;
                                if (i3 < WC.length) {
                                    continue;
                                }
                            }
                            if (i3 != WC.length) {
                                if (this.like) {
                                    a.setCharAt(i0 + i, LIKE_WC[i3]);
                                } else {
                                    a.setCharAt(i0 + i, GLOB_WC[i3]);
                                }
                            }
                            break;
                        }
                    }
                }
                this.compareTo = a.toString();
            }
        } else {
            s = "";
        }
        String r = (this.first) ? "" : (this.or) ? "OR " : "AND ";
        if (this.not) {
            r += " NOT";
        }
        if (this.op.equals("=")) {
            if (!this.string) {
                return r + this.category + " = " + this.compareTo;
            }
                return r + this.category + " = '" + this.compareTo + "'";
        } else {
            if (!this.string) {
                return r + this.category + " " + this.op + " " + this.compareTo;
            }
            return r + this.category + " " + this.op + " '" + this.compareTo + "'" + s;
        }
    }
}

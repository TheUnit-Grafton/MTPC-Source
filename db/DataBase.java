package appguru.db;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    public java.sql.Connection conn;
    public java.util.ArrayList<Table> tables;
    final public static byte REAL = (byte) 0;
    final public static byte INTEGER = (byte) 1;
    final public static byte TEXT = (byte) 2;
    final public static byte LINK = (byte) 4;
    final public static byte BLOB = (byte) 3;
    public static Object[] NULLS;

    static {
        try {
            NULLS = new Object[] {111111.11111F,11111111,"No Value/Unknown","https://www.google.de","https://www.example.com"
            };
        } catch (Exception e) {

        }
    }

    public static Object convert(String s, byte type) {
        try {
            switch (type) {
                case TEXT:
                    return s;
                case REAL:
                    return new Float(s);
                case INTEGER:
                    return new Integer(s);
                case LINK: {
                    URL u = new URL(s);
                }
            }
        } catch (Exception ex) {
            return NULLS[type];
        }
        return null;
    }

    public DataBase(String s) {
        super();
        label0:
        {
            java.sql.SQLException a = null;
            label1:
            try {
                try {
                    Class.forName("org.sqlite.JDBC");
                    //new java.io.File(s).delete();
                    this.conn = java.sql.DriverManager.getConnection("jdbc:sqlite:" + s);
                    if (this.conn != null) {
                        java.sql.DatabaseMetaData a0 = this.conn.getMetaData();
                        appguru.info.Console.successMessage(appguru.db.DataBase.class.getName(), new StringBuilder().append("CREATED DATABASE - DRIVER NAME : ").append(a0.getDriverName()).toString());
                    }
                    String[] tp_columns = new String[]{"id", "author", "name", "title", "description", "rating", "ratings", "type", "version", "screenshot", "homepage", "download", "req_mt"};
                    byte[] tp_types = new byte[]{INTEGER, TEXT, TEXT, TEXT, TEXT, REAL, INTEGER, TEXT, REAL, LINK, LINK, LINK, REAL};
                    appguru.db.Table tp = new appguru.db.Table(this, "tps", tp_columns, tp_types);
                    String[] mods_columns = new String[]{"id", "author", "name", "title", "description", "rating", "ratings", "type", "depends", "subgame", "mods", "tps", "version", "screenshot", "homepage", "download", "req_mt"};
                    byte[] mods_types = new byte[]{INTEGER, TEXT, TEXT, TEXT, TEXT, REAL, INTEGER, TEXT, TEXT, TEXT, TEXT, TEXT, REAL, LINK, LINK, LINK, REAL};
                    appguru.db.Table mods = new appguru.db.Table(this, "mods", mods_columns, mods_types);
                    String[] authors = new String[]{"id", "author", "firstname", "lastname", "info", "rating", "ratings", "github", "mt_forums", "email", "profile_picture", "homepage", "birthday"};
                    byte[] author_types = new byte[]{INTEGER, TEXT, TEXT,        TEXT,        TEXT,    REAL,    INTEGER,   TEXT,     TEXT,        TEXT,    LINK,              LINK,       INTEGER};
                    appguru.db.Table a3 = new appguru.db.Table(this, "authors", authors, author_types);
                    String[] subgame_columns = new String[]{"id", "author", "name", "title", "description", "rating", "ratings", "type", "mods", "tps", "version", "screenshot", "homepage", "download", "req_mt"};
                    byte[] subgame_types = new byte[]{INTEGER, TEXT, TEXT, TEXT, TEXT, REAL, INTEGER, TEXT, TEXT, TEXT, REAL, LINK, LINK, LINK, REAL};
                    appguru.db.Table subgames = new appguru.db.Table(this, "subgames", subgame_columns, subgame_types);
                    String[] fork_columns = new String[]{"id", "author", "name", "title", "description", "rating", "ratings", "type", "version", "screenshot", "homepage", "download", "req_mt"};
                    byte[] fork_types = new byte[]{INTEGER, TEXT, TEXT, TEXT, TEXT, REAL, INTEGER, TEXT, REAL, LINK, LINK, LINK, REAL};
                    appguru.db.Table forks = new appguru.db.Table(this, "forks", fork_columns, fork_types);

                    /*Object[] kgm = new Object[]{"KGM", "Kai Gerd", "Müller", "hobby lua dev", "1.1", "1", "KaiGerdMueller", "KGM", "kai_gerd_mueller@gmx.de",
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", 20020410, 10};
                    //a3.defaultInsert(this, kgm);
                    Object[] jellyfish = new Object[]{"KGM", "mana", "Jellyfish", "ADDS JELLYFISHES. OUCH !", 1f, 1, "MOBS", "none", "none", "none", "none", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://forum.minetest.net/download/file.php?id=8108", 4.13};
                    //mods.defaultInsert(this, jellyfish);
                    Object[] baubles = new Object[]{"LMD", "baubles", "Baubles", "ADDS DECO BAUBLES. YAY !", 5f, 1, "DECO", "mana", "none", "none", "none", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://github.com/appgurueu/baubles/archive/master.zip", 4.13};
                    //mods.defaultInsert(this, baubles);
                    Object[] jellyfish_game = new Object[]{"KGM", "jellyfish_game", "Jellyfish GAME", "FIGHT GAME", 1f, 1, "MOBS", "none", "none", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://forum.minetest.net/downloada/file.php?id=8108a", 4.13};
                    //subgames.defaultInsert(this, jellyfish_game);
                    Object[] baubles_game = new Object[]{"LMD", "baubles_game", "Baubles GAME", "XMAS GAME", "none", "none", 5f, 1, "DECO", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://github.com/appgurueu/baubles/archive/master.zip", 4.13};
                    //subgames.defaultInsert(this, baubles_game);
                    Object[] jellyfish_tp = new Object[]{"KGM", "jellyfish_tp", "Jellyfish TEXPACK", "FIGHT ADVENTURE TEXES", 1f, 1, "MOBS", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://forum.minetest.net/download/file.php?id=8108", 4.13};
                    tp.defaultInsert(this, jellyfish_tp);
                    Object[] baubles_tp = new Object[]{"LMD", "baubles_tp", "Baubles Texture PACK", "XMAS TEXES", 5f, 1, "DECO", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://github.com/appgurueu/baubles", 4.13};
                    tp.defaultInsert(this, baubles_tp);
                    Object[] jellyfish_fork = new Object[]{"KGM", "jellyfish_tp", "Jellyfish MT FORK", "FIGHT ADVENTURE BASED C++", 1f, 1, "MOBS", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://forum.minetest.net/download/file.php?id=8108", 4.13};
                    forks.defaultInsert(this, jellyfish_fork);
                    Object[] baubles_fork = new Object[]{"LMD", "baubles_tp", "Baubles MT FORK", "DAMN", 5f, 1, "DECO", 3.2f,
                        "https://img.zeit.de/wissen/gesundheit/2018-03/drogenpolitik-cannabis-legalisierung-sucht-staat-steuereinnahmen-hanf/wide__820x461__desktop",
                        "https://appgurueu.github.io", "https://github.com/appgurueu/baubles", 4.13};
                    forks.defaultInsert(this, baubles_fork);*/
                    this.tables = new java.util.ArrayList();
                    this.tables.add(mods);
                    this.tables.add(subgames);
                    this.tables.add(tp);
                    this.tables.add(forks);
                    this.tables.add(a3);
                    this.tables.add(new appguru.db.Table(this, "installed_mods", mods_columns, mods_types));
                    this.tables.add(new appguru.db.Table(this, "installed_subgames", subgame_columns, subgame_types));
                } catch (java.sql.SQLException a10) {
                    a = a10;
                    break label1;
                }
                break label0;
            } catch (ClassNotFoundException ignoredException) {
                appguru.info.Console.errorMessage(appguru.db.DataBase.class.getName(), "NO SQLITE");
                break label0;
            }
            appguru.info.Console.errorMessage(appguru.db.DataBase.class.getName(), new StringBuilder().append("CORRUPTED DATABASE - FURTHER LOG : ").append(a.getMessage()).toString());
        }
    }

    public static String buildCreationString(String s, String[] a) {
        String s0 = new StringBuilder().append("CREATE TABLE IF NOT EXISTS ").append(s).append(" (\n").toString();
        int i = a.length;
        int i0 = 0;
        while (i0 < i) {
            String s1 = a[i0];
            String s2 = new StringBuilder().append(s0).append("\t").toString();
            String s3 = new StringBuilder().append(s2).append(s1).toString();
            String s4 = new StringBuilder().append(s3).append(",").toString();
            s0 = new StringBuilder().append(s4).append("\n").toString();
            i0 = i0 + 1;
        }
        String s5 = s0.substring(0, s0.length() - 2);
        return new StringBuilder().append(s5).append(");").toString();
    }

    public static String buildOrderBy(String[] a, boolean[] a0) {
        String s = "ORDER BY\n";
        int i = 0;
        while (i < a.length) {
            s = new StringBuilder().append(s).append("\t").append(a[i]).toString();
            if (!a0[i]) {
                s = new StringBuilder().append(s).append(" DESC").toString();
            }
            if (i != a.length - 1) {
                s = new StringBuilder().append(s).append(",\n").toString();
            }
            i = i + 1;
        }
        return new StringBuilder().append(s).append(";").toString();
    }

    public static String buildInsertQuery(String table, String[] cols, Object[] insert) {
        String s0 = new StringBuilder().append("INSERT INTO ").append(table).append(" (").toString();
        int i = 0;
        while (i < cols.length) {
            s0 = new StringBuilder().append(s0).append("\t").append(cols[i]).toString();
            if (i != cols.length - 1) {
                s0 = new StringBuilder().append(s0).append(",\n").toString();
            }
            i = i + 1;
        }
        String s1 = new StringBuilder().append(s0).append(" )\nVALUES\n (").toString();
        int j = 0;
        while (j < cols.length) {
            String s2 = new StringBuilder().append(s1).append("\t").toString();
            if (insert[j].getClass() == String.class || insert[j].getClass() == URL.class) {
                s2 = new StringBuilder().append(s2).append("'").toString();
            }
            s1 = new StringBuilder().append(s2).append(insert[j].toString()).toString();
            if (insert[j].getClass() == String.class || insert[j].getClass() == URL.class) {
                s1 = new StringBuilder().append(s1).append("'").toString();
            }
            if (j != cols.length - 1) {
                s1 = new StringBuilder().append(s1).append(",\n").toString();
            }
            j = j + 1;
        }
        return new StringBuilder().append(s1).append(");").toString();
    }

    public static String buildSearchQuery(String s, String[] a, appguru.db.Clause[] a0) {
        int i = a.length;
        String s0 = "SELECT\n";
        int i0 = 0;
        int i1 = 0;
        while (i1 < i) {
            String s1 = a[i1];
            String s2 = new StringBuilder().append(s0).append("\t").append(s1).toString();
            i0 = i0 + 1;
            if (i0 != a.length) {
                s2 = new StringBuilder().append(s2).append(",").toString();
            }
            s0 = new StringBuilder().append(s2).append("\n").toString();
            i1 = i1 + 1;
        }
        String s3 = new StringBuilder().append(s0).append("FROM\n\t").append(s).append("\n").toString();
        String s4 = new StringBuilder().append(s3).append("WHERE\n").toString();
        int i2 = a0.length;
        int i3 = 0;
        int i4 = 0;
        while (i4 < i2) {
            appguru.db.Clause a1 = a0[i4];
            String s5 = new StringBuilder().append(s4).append("\t").toString();
            if (i3 != 0) {
                s5 = new StringBuilder().append(s5).append("AND ").toString();
            }
            s4 = new StringBuilder().append(s5).append(a1.category).append(a1.op).append(a1.compareTo).toString();
            if (i3 != 0) {
                s4 = new StringBuilder().append(s4).append(";").toString();
            }
            i3 = i3 + 1;
            if (i3 != a0.length) {
                s4 = new StringBuilder().append(s4).append("\n").toString();
            }
            i4 = i4 + 1;
        }
        return s4;
    }

    public void execute(String s) {
        try {
            this.conn.createStatement().execute(s);
        } catch (java.sql.SQLException a) {
            appguru.info.Console.errorMessage(appguru.db.DataBase.class.getName(), new StringBuilder().append("CORRUPTED DATABASE/STATEMENT - FURTHER LOG : ").append(a.getMessage()).toString() + " - HERES THE CODE : " + s);
        }
    }

    public java.sql.ResultSet executeQuery(String s) {
        try {
            return this.conn.createStatement().executeQuery(s);
        } catch (java.sql.SQLException a) {
            appguru.info.Console.errorMessage(appguru.db.DataBase.class.getName(), new StringBuilder().append("CORRUPTED DATABASE/STATEMENT - FURTHER LOG : ").append(a.getMessage()).toString() + " - HERES THE CODE : " + s);
            Object a0 = null;
            return (java.sql.ResultSet) a0;
        }
    }
}

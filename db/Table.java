package appguru.db;

import appguru.info.Console;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Table {

    public String name;
    public String[] c2;
    public String[] columns;
    public byte[] types;
    public static String[] assignments = new String[]{"REAL", "INTEGER", "TEXT", "BLOB", "LINK"};
    public HashMap<String, Integer> indexes;

    public static final byte MODE_ADD_NEW = 0;
    public static final byte MODE_UPDATE = 1;
    public static final byte MODE_OVERWRITE = 2;

    public Table(appguru.db.DataBase a, String s, String[] a0, byte[] a1) {
        super();
        this.name = s;
        this.columns = a0;
        this.types = a1;
        indexes = new HashMap();
        for (int i = 1; i < columns.length; i++) {
            indexes.put(columns[i], i - 1);
        }
        this.create(a);
    }

    public void create(appguru.db.DataBase a) {
        String[] a0 = new String[this.columns.length];
        int i = 0;
        while (i < a0.length) {
            StringBuilder a1 = new StringBuilder().append(this.columns[i]).append(" ");
            String[] a2 = this.assignments;
            int i0 = this.types[i];
            a0[i] = a1.append(a2[i0]).toString();
            if (i == 0) {
                int i1 = this.types[i];
                if (i1 == 1 && this.columns[i].equals((Object) "id")) {
                    this.c2 = new String[this.columns.length - 1];
                    a0[i] = new StringBuilder().append(a0[i]).append(" PRIMARY KEY AUTOINCREMENT").toString();
                    System.arraycopy((Object) this.columns, 1, (Object) this.c2, 0, this.columns.length - 1);
                }
            }
            i = i + 1;
        }
        //System.out.println(appguru.db.DataBase.buildCreationString(this.name, a0));
        a.execute(appguru.db.DataBase.buildCreationString(this.name, a0));
    }

    public void delete(appguru.db.DataBase a) {
        a.execute(new StringBuilder().append("DROP TABLE IF EXISTS ").append(this.name).toString());
    }

    public boolean defaultInsert(appguru.db.DataBase a, Object[] a0) {
        if (this.exists(a, a0)) {
            return false;
        }
        this.insertAllSorted(a, a0);
        return true;
    }

    public void insertAllSorted(appguru.db.DataBase a, Object[] a0) {
        //System.out.println(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
        a.execute(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
    }

    public void insertAllSorted(appguru.db.DataBase a, Object[][] a1) {
        try {
            //System.out.println(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
            a.conn.setAutoCommit(false);
            for (Object[] a0 : a1) {
                if (a0 != null && a0[0] != null) {
                    PreparedStatement prepared = a.conn.prepareStatement(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
                    prepared.executeUpdate();
                }
            }
            a.conn.commit();
        } catch (SQLException ex) {
            Console.errorMessage(this.getClass().getName(), "SQL INSERT");
        }
    }

    public void insertAll(appguru.db.DataBase a, Object[][] a1) {
        try {
            //System.out.println(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
            a.conn.setAutoCommit(false);
            for (Object[] a0 : a1) {
                if (a0 == null) {
                    continue;
                }
                for (int i = 0; i < a0.length; i++) {
                    if (a0[i] == null) {
                        //System.out.println(c2[i]+" - "+assignments[types[i+1]]);
                        a0[i] = DataBase.NULLS[types[i + 1]];
                        //System.out.println(c2[i]+" - "+DataBase.NULLS[types[i+1]]);
                    } else if (types[i + 1] == DataBase.TEXT || types[i + 1] == DataBase.LINK) {
                        a0[i] = a0[i].toString().replace("'", "''");
                    }
                }
                if (a0[0] != null) {
                    PreparedStatement prepared = a.conn.prepareStatement(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
                    prepared.executeUpdate();
                }
            }
            a.conn.commit();
        } catch (SQLException ex) {
            Console.errorMessage(this.getClass().getName(), "SQL INSERT");
            System.out.println(ex);
        }
    }

    public void updateAllSorted(appguru.db.DataBase a, int i, Object[] a0) {
        String s = new StringBuilder().append("UPDATE ").append(this.name).append("\nSET ").toString();
        int i0 = 0;
        while (i0 < a0.length) {
            if (a0[i0] != null) {
                String s0 = new StringBuilder().append(s).append(this.c2[i0]).append(" = ").toString();
                if (a0[i0].getClass() == String.class) {
                    s0 = new StringBuilder().append(s0).append("'").toString();
                }
                String s1 = new StringBuilder().append(s0).append(a0[i0].toString()).toString();
                if (a0[i0].getClass() == String.class) {
                    s1 = new StringBuilder().append(s1).append("'").toString();
                }
                s = new StringBuilder().append(s1).append(",").toString();
            }
            i0 = i0 + 1;
        }
        String s2 = s.substring(0, s.length() - 1);
        a.execute(new StringBuilder().append(s2).append("\nWHERE id = ").append(Integer.toString(i)).append(";").toString());
    }

    public void updateColumn(appguru.db.DataBase a, int i, int i0, Object a0) {
        a.execute(buildUpdateQuery(i, i0, a0));
    }

    public String buildUpdateQuery(int i, int i0, Object a0) {
        String s = new StringBuilder().append("UPDATE ").append(this.name).append("\nSET ").append(this.columns[i0]).append(" = ").toString();
        if (a0.getClass() == String.class || a0.getClass() == URL.class) {
            s = new StringBuilder().append(s).append("'").toString();
        }
        String s0 = new StringBuilder().append(s).append(a0.toString()).toString();
        if (a0.getClass() == String.class || a0.getClass() == URL.class) {
            s0 = new StringBuilder().append(s0).append("'").toString();
        }
        return new StringBuilder().append(s0).append("\nWHERE id = ").append(Integer.toString(i)).append(";").toString();
    }

    public String buildUpdateQuery(Clause c, int i0, Object a0) {
        String s = new StringBuilder().append("UPDATE ").append(this.name).append("\nSET ").append(this.columns[i0]).append(" = ").toString();
        if (a0.getClass() == String.class || a0.getClass() == URL.class) {
            s = new StringBuilder().append(s).append("'").toString();
        }
        String s0 = new StringBuilder().append(s).append(a0.toString()).toString();
        if (a0.getClass() == String.class || a0.getClass() == URL.class) {
            s0 = new StringBuilder().append(s0).append("'").toString();
        }
        return new StringBuilder().append(s0).append("\nWHERE ").append(c.toString()).append(";").toString();
    }

    public java.sql.ResultSet searchUnsortedEquals(appguru.db.DataBase a, Object[] a0) {
        appguru.db.Clause[] a1 = new appguru.db.Clause[this.c2.length];
        int i = 0;
        while (i < a1.length) {
            String s = a0[i].toString();
            if (a0[i].getClass() == String.class) {
                s = new StringBuilder().append("'").append(s).append("'").toString();
            }
            a1[i] = new appguru.db.Clause(this.c2[i], " = ", s);
            i = i + 1;
        }
        return a.executeQuery(appguru.db.DataBase.buildSearchQuery(this.name, this.c2, a1));
    }

    public String grabAll() {
        String[] a = this.columns;
        int i = a.length;
        String s = "SELECT ";
        int i0 = 0;
        while (i0 < i) {
            String s0 = a[i0];
            s = new StringBuilder().append(s).append(s0).append(", ").toString();
            i0 = i0 + 1;
        }
        String s1 = s.substring(0, s.length() - 2);
        String s2 = new StringBuilder().append(s1).append(" ").toString();
        return new StringBuilder().append(s2).append("FROM ").append(this.name).toString();
    }

    public String grabAllClauses(Clause... a) {
        String[] a0 = this.columns;
        int i = a0.length;
        String s = "SELECT ";
        int i0 = 0;
        while (i0 < i) {
            String s0 = a0[i0];
            s = new StringBuilder().append(s).append(s0).append(", ").toString();
            i0 = i0 + 1;
        }
        String s1 = s.substring(0, s.length() - 2);
        String s2 = new StringBuilder().append(s1).append(" ").toString();
        String s3 = new StringBuilder().append(s2).append("FROM ").append(this.name).append("\n WHERE\n").toString();
        for (Clause c : a) {
            s3 = new StringBuilder().append(s3).append("\t").append(c.toString()).append("\n").toString();
        }
        String s4 = s3.substring(0, s3.length() - 1);
        return new StringBuilder().append(s4).append(";").toString();
    }

    public boolean existsClauses(DataBase db, Clause... a) {
        String[] a0 = this.columns;
        int i = a0.length;
        String s1 = "SELECT 1";
        String s2 = new StringBuilder().append(s1).append("\n ").toString();
        String s3 = new StringBuilder().append(s2).append("FROM ").append(this.name).append("\n WHERE\n").toString();
        for (Clause c : a) {
            s3 = new StringBuilder().append(s3).append("\t").append(c.toString()).append("\n").toString();
        }
        String s4 = s3.substring(0, s3.length() - 1);
        String sw = new StringBuilder().append(s4).append(";").toString();
        ResultSet rs = db.executeQuery(sw);
        try {
            return rs.next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public int existsClausesId(DataBase db, Clause... a) {
        String[] a0 = this.columns;
        int i = a0.length;
        String s1 = "SELECT id";
        String s2 = new StringBuilder().append(s1).append("\n ").toString();
        String s3 = new StringBuilder().append(s2).append("FROM ").append(this.name).append("\n WHERE\n").toString();
        a[0].first=true;
        for (Clause c : a) {
            s3 = new StringBuilder().append(s3).append("\t").append(c.toString()).append("\n").toString();
        }
        String s4 = s3.substring(0, s3.length() - 1);
        String sw = new StringBuilder().append(s4).append(";").toString();
        ResultSet rs = db.executeQuery(sw);
        try {
            return rs.getInt("id");
        } catch (SQLException ex) {
            return -1;
        }
    }

    public String grabAllClauses(java.util.ArrayList a) {
        String[] a0 = this.columns;
        int i = a0.length;
        String s = "SELECT ";
        int i0 = 0;
        while (i0 < i) {
            String s0 = a0[i0];
            s = new StringBuilder().append(s).append(s0).append(", ").toString();
            i0 = i0 + 1;
        }
        String s1 = s.substring(0, s.length() - 2);
        String s2 = new StringBuilder().append(s1).append(" ").toString();
        String s3 = new StringBuilder().append(s2).append("FROM ").append(this.name).append("\n WHERE\n").toString();
        Object a1 = a.iterator();
        while (((java.util.Iterator) a1).hasNext()) {
            appguru.db.Clause a2 = (appguru.db.Clause) ((java.util.Iterator) a1).next();
            s3 = new StringBuilder().append(s3).append("\t").append(a2.toString()).append("\n").toString();
        }
        String s4 = s3.substring(0, s3.length() - 1);
        return new StringBuilder().append(s4).append(";").toString();
    }

    public java.sql.ResultSet searchEquals(appguru.db.DataBase a, Object[] a0) {
        int i = a0.length;
        int i0 = 0;
        int i1 = 0;
        while (i1 < i) {
            if (a0[i1] != null) {
                i0 = i0 + 1;
            }
            i1 = i1 + 1;
        }
        String[] a1 = new String[i0];
        appguru.db.Clause[] a2 = new appguru.db.Clause[i0];
        int i2 = 0;
        int i3 = 0;
        while (i3 < a2.length) {
            if (a0[i3] != null) {
                String s = a0[i3].toString();
                if (a0[i3].getClass() == String.class) {
                    s = new StringBuilder().append("'").append(s).append("'").toString();
                }
                a1[i2] = this.c2[i3];
                a2[i2] = new appguru.db.Clause(this.c2[i3], " = ", s);
                i2 = i2 + 1;
            }
            i3 = i3 + 1;
        }
        return a.executeQuery(appguru.db.DataBase.buildSearchQuery(this.name, a1, a2));
    }

    public boolean exists(appguru.db.DataBase a, Object[] a0) {
        try {
            return this.searchUnsortedEquals(a, a0).next();
        } catch (java.sql.SQLException a1) {
            appguru.info.Console.errorMessage(appguru.db.DataBase.class.getName(), new StringBuilder().append("MALFORMED QUERY - FURTHER LOG : ").append(a1.getMessage()).toString());
            return false;
        }
    }

    public void delete(appguru.db.DataBase a, int i) {
        a.execute(new StringBuilder().append("DELETE\nFROM\n\t").append(this.name).append("\nWHERE\n\tid = ").append(Integer.toString(i)).append(";").toString());
    }

    public java.sql.ResultSet orderedQuery(appguru.db.DataBase a, String s, int[] a0, boolean[] a1) {
        String[] a2 = new String[a0.length];
        int i = 0;
        while (i < a0.length) {
            a2[i] = this.columns[a0[i]];
            i = i + 1;
        }
        return a.executeQuery(new StringBuilder().append(s).append("\n").append(appguru.db.DataBase.buildOrderBy(a2, a1)).toString());
    }

    public ArrayList<ArrayList> retrieveSelection(java.sql.ResultSet a0) {
        ArrayList<ArrayList> a1 = new java.util.ArrayList();
        Object a2 = a0;
        try {
            while (((java.sql.ResultSet) a2).next()) {
                java.util.ArrayList a3 = new java.util.ArrayList();
                int i = 0;
                while (i < this.columns.length) {
                    int i0 = this.types[i];
                    switch (i0) {
                        case 4: {
                            try {
                                a3.add((Object) new java.net.URL(((java.sql.ResultSet) a2).getString(this.columns[i])));
                                break;
                            } catch (java.net.MalformedURLException ignoredException) {
                                a3.add((Object) "Malformed url.");
                                break;
                            }
                        }
                        case 2: {
                            a3.add((Object) ((java.sql.ResultSet) a2).getString(this.columns[i]));
                            break;
                        }
                        case 1: {
                            a3.add((Object) Integer.valueOf(((java.sql.ResultSet) a2).getInt(this.columns[i])));
                            break;
                        }
                        case 0: {
                            a3.add((Object) Float.valueOf(((java.sql.ResultSet) a2).getFloat(this.columns[i])));
                            break;
                        }
                    }
                    i = i + 1;
                }
                a1.add(a3);
            }
        } catch (java.sql.SQLException ignoredException0) {
            appguru.info.Console.errorMessage(((Object) this).getClass().getName(), "CORRUPTED RESULT SET");
        }
        return a1;
    }

    public void insertAll(DataBase a, Object[][] a1, String unique, byte mode) {
        try {
            //System.out.println(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
            a.conn.setAutoCommit(false);
            for (Object[] a0 : a1) {
                if (a0 == null) {
                    continue;
                }
                for (int i = 0; i < a0.length; i++) {
                    if (a0[i] == null) {
                        //System.out.println(c2[i]+" - "+assignments[types[i+1]]);
                        a0[i] = DataBase.NULLS[types[i + 1]];
                        //System.out.println(c2[i]+" - "+DataBase.NULLS[types[i+1]]);
                    } else if (types[i + 1] == DataBase.TEXT || types[i + 1] == DataBase.LINK) {
                        a0[i] = a0[i].toString().replace("'", "''");
                    }
                }
                switch (mode) {
                    case MODE_ADD_NEW:
                        if (this.existsClauses(a, new Clause(types[indexes.get(unique) + 1] >= DataBase.TEXT, true, false, false, unique, "=", a0[indexes.get(unique) + 1].toString()))) {
                            break;
                        }
                    case MODE_UPDATE:
                        int cid = this.existsClausesId(a, new Clause(types[indexes.get(unique) + 1] >= DataBase.TEXT, true, false, false, unique, "=", a0[indexes.get(unique) + 1].toString()));
                        if (cid >= 0) {
                            for (int g = 0; g < a0.length; g++) {
                                if (a0[g] != null) {
                                    PreparedStatement prepared = a.conn.prepareStatement(buildUpdateQuery(cid, g, a0[g]));
                                    prepared.executeUpdate();
                                }
                            }
                            break;
                        }
                    case MODE_OVERWRITE:
                        int cid2 = this.existsClausesId(a, new Clause(types[indexes.get(unique) + 1] > DataBase.TEXT, true, false, false, unique, "=", a0[indexes.get(unique) + 1].toString()));
                        if (cid2 > 0) {
                            this.delete(a, cid2);
                        }
                    default:
                        PreparedStatement stmt=a.conn.prepareStatement(appguru.db.DataBase.buildInsertQuery(this.name, this.c2, a0));
                        stmt.executeUpdate();
                        break;
                }
            }
            a.conn.commit();
        } catch (SQLException ex) {
            Console.errorMessage(this.getClass().getName(), "SQL INSERT");
            System.out.println(ex);
        }
    }
}

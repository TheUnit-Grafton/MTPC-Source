package appguru;

import appguru.db.Clause;
import appguru.db.DataBase;
import appguru.db.Table;
import appguru.graphics.swing.BasicTableModel;
import appguru.graphics.swing.SwingHelper;
import appguru.graphics.swing.Window;
import appguru.graphics.swing.gui.basic.BasicButton;
import appguru.graphics.swing.gui.basic.BasicCheckButton;
import appguru.graphics.swing.gui.basic.BasicColorScheme;
import appguru.graphics.swing.gui.basic.BasicComboBox;
import appguru.graphics.swing.gui.basic.BasicEntry;
import appguru.graphics.swing.gui.basic.BasicFormspec;
import appguru.graphics.swing.gui.basic.BasicList;
import appguru.graphics.swing.gui.basic.BasicListCellRenderer;
import appguru.graphics.swing.gui.basic.BasicNumberBox;
import appguru.graphics.swing.gui.basic.BasicRoundBorder;
import appguru.graphics.swing.gui.basic.BasicToggleButton;
import appguru.timer.SchedulerTest;
import appguru.timer.Timer;
import java.awt.Color;
import java.awt.event.ActionListener;
import appguru.graphics.swing.gui.basic.BasicOptionPane;
import appguru.graphics.swing.gui.basic.BasicPanel;
import appguru.helper.FileHelper;
import appguru.helper.Saver;
import appguru.info.Console;
import appguru.net.crawler.DBCrawler;
import appguru.net.crawler.Crawlers;
import appguru.net.crawler.MTForumDBCrawler;
import appguru.net.crawler.MTForumMemberDBCrawler;
import appguru.net.crawler.TableDBCrawler;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

public class AGE {

    //public static String subgame_path = "/home/lars/.minetest" + File.separator + "games";
    public static Window w;
    public static appguru.graphics.swing.Window application;
    //public static appguru.Main main;
    public static Timer global_timer;
    public static Graphics GRAPHICS;
    public static File games = new File("/home/lars/.minetest" + File.separator + "gamesas");
    public static JTable table;
    public static Vector<ArrayList> clauses;
    public static String[][] order_columns;
    public static Object[][][] order_values;
    public static int previous;
    public static ArrayList<DBCrawler> crawlers = new ArrayList();

    public AGE() {
        super();
    }

    public static void addDisableListener(AbstractButton a, AbstractButton available, AbstractButton installed, AbstractButton to_upgrade, ButtonGroup group) {
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                group.setSelected(available.getModel(), true);
                installed.setEnabled(false);
                to_upgrade.setEnabled(false);
            }
        });
    }

    public static void addEnableListener(AbstractButton a, AbstractButton available, AbstractButton installed, AbstractButton to_upgrade, ButtonGroup group) {
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                group.setSelected(available.getModel(), true);
                installed.setEnabled(true);
                to_upgrade.setEnabled(true);
            }
        });
    }

    public static void installedDB(String file, String conf, String addit, int dbn, int pn, DataBase db) {
        File[] list = new File(file).listFiles();
        if (list == null) {
            return;
        }
        int co = 0;
        for (File f : list) {
            if (f.isDirectory()) {
                co++;
            }
        }
        Object[][] objs = new Object[co][db.tables.get(dbn).c2.length];
        int c = 0;
        for (File f : list) {
            if (f.isDirectory()) {
                label0:
                {
                    ArrayList<ArrayList> probably = db.tables.get(pn).retrieveSelection(db.executeQuery(db.tables.get(pn).grabAllClauses(new Clause(true, true, false, false, "name", "=", f.getName()))));
                    if (!probably.isEmpty()) {
                        System.arraycopy(probably.get(0).toArray(), 1, objs[c], 0, probably.get(0).size() - 1);
                        break label0;
                    }
                    Object[] fill_in = new Object[db.tables.get(dbn).c2.length];
                    for (int i = 1; i < db.tables.get(dbn).columns.length; i++) {
                        if (db.tables.get(dbn).types[i] == DataBase.INTEGER) {
                            fill_in[i - 1] = 1111111111;
                        } else if (db.tables.get(dbn).types[i] == DataBase.REAL) {
                            fill_in[i - 1] = new Float(11111.11111);
                        } else if (db.tables.get(dbn).types[i] == DataBase.LINK) {
                            try {
                                fill_in[i - 1] = new URL("https://www.example.com");
                            } catch (MalformedURLException ex) {
                                fill_in[i - 1] = "IMPOSSIBLE ERROR - PLEASE CONTACT THE DEVELOPER";
                            }
                        } else if (db.tables.get(dbn).types[i] == DataBase.TEXT) {
                            fill_in[i - 1] = "No Value/Unknown";
                        }
                    }
                    System.arraycopy(fill_in, 0, objs[c], 0, objs[c].length);
                }
                String s = "name=" + f.getName() + "\n" + addit;
                String a = FileHelper.readFile(f.getAbsolutePath() + File.separator + conf + ".conf");
                s = (a == null) ? s : s + a;
                if (s == null) {
                    s = "";
                }
                String[] strings = s.split("\n");
                for (String s2 : strings) {
                    String[] well = s2.split("[ ]*=[ ]*");
                    int i = 0;
                    for (String column : db.tables.get(dbn).c2) {
                        if (column.equals(well[0])) {
                            objs[c][i] = DataBase.convert(well[1], db.tables.get(dbn).types[i + 1]);
                            break;
                        }
                        i++;
                    }
                }
                c++;
            }
        }
        //long t = System.currentTimeMillis();
        db.tables.get(dbn).insertAllSorted(db, objs);
        //System.out.println(System.currentTimeMillis() - t);
    }

    public static void upgradedDB(String file, String conf, String addit, int dbn, int pn, DataBase db) {
        int version_index = 0;
        int dindex = 0;
        for (String s : db.tables.get(dbn).c2) {
            if (s.equals("version")) {
                break;
            }
            version_index++;
        }
        for (String s : db.tables.get(dbn).c2) {
            if (s.equals("download")) {
                break;
            }
            dindex++;
        }
        File[] list = new File(file).listFiles();
        int co = 0;
        for (File f : list) {
            if (f.isDirectory()) {
                co++;
            }
        }
        Object[][] objs = new Object[co][db.tables.get(dbn).c2.length];
        int c = 0;
        for (File f : list) {
            if (f.isDirectory()) {
                ArrayList<ArrayList> probably = db.tables.get(pn).retrieveSelection(db.executeQuery(db.tables.get(pn).grabAllClauses(new Clause(true, true, false, false, "name", "=", f.getName()))));
                if (!probably.isEmpty()) {
                    System.arraycopy(probably.get(0).toArray(), 1, objs[c], 0, probably.get(0).size() - 1);
                } else {
                    continue;
                }
                String s = "name=" + f.getName() + "\n" + addit;
                String a = FileHelper.readFile(f.getAbsolutePath() + File.separator + conf + ".conf");
                s = (a == null) ? s : s + a;
                if (s == null) {
                    s = "";
                }
                s += "\n" + "download=" + objs[c][dindex];
                String[] strings = s.split("\n");
                for (String s2 : strings) {
                    String[] well = s2.split("[ ]*=[ ]*");
                    int i = 0;
                    for (String column : db.tables.get(dbn).c2) {
                        if (column.equals(well[0])) {
                            objs[c][i] = DataBase.convert(well[1], db.tables.get(dbn).types[i + 1]);
                            break;
                        }
                        i++;
                    }
                }
                if (((Float) objs[c][version_index] >= (Float) (probably.get(0).get(version_index + 1)))) {
                    objs[c] = null;
                }
                c++;
            }
        }
        //long t = System.currentTimeMillis();
        db.tables.get(dbn).insertAllSorted(db, objs);
        //System.out.println(System.currentTimeMillis() - t);
    }

    public static void modifyTable(String name, String screenie, int number, BasicTableModel mods, DataBase db) {
        clauses.set(previous, mods.cls);
        order_columns[previous] = new String[mods.order_columns.length];
        System.arraycopy(mods.order_columns, 0, order_columns[previous], 0, mods.order_columns.length);
        order_values[previous] = new Object[1][mods.order_values[0].length];
        System.arraycopy(mods.order_values[0], 0, order_values[previous][0], 0, mods.order_values[0].length);
        previous = number;
        mods.name = name;
        mods.image = screenie;
        mods.dbTable = (Table) mods.db.tables.get(previous);
        mods.cls = new ArrayList();
        mods.cls.addAll(clauses.get(previous));
        mods.order_columns = new String[order_columns[previous].length];
        System.arraycopy(order_columns[previous], 0, mods.order_columns, 0, order_columns[previous].length);
        mods.order_values = new Object[1][order_values[previous][0].length];
        System.arraycopy(order_values[previous][0], 0, mods.order_values[0], 0, order_values[previous][0].length);
        mods.updateTable();
    }

    public static void addActionListener(AbstractButton a, String name, String screenie, int number, BasicTableModel mods, DataBase db) {
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mods.toggleInstallOption("Install", true);
                modifyTable(name, screenie, number, mods, db);
            }
        });
    }

    public static HashMap column_assignments = new HashMap<String, String>();

    static {
        column_assignments.put("GitHub:", "github");
        column_assignments.put("Website:", "homepage");
        column_assignments.put("Interests:", "info");
    }

    public static void main(String[] a) throws Exception {
        appguru.db.DataBase db = new appguru.db.DataBase("MTPC_Database.db");
        //Document mods_page = Jsoup.connect("https://forum.minetest.net/viewforum.php?f=11").get();
        //Element members_amount = mods_page.selectFirst("div.pagination");
        //MTForumDBCrawler mods_mtf_crawler = new MTForumDBCrawler(Table.MODE_ADD_NEW, "https://forum.minetest.net/viewforum.php?f=11", "mod", "div.pagination");
        //mods_mtf_crawler.launchCrawl(db, db.tables.get(0));

        //MTForumMemberDBCrawler members_mtf_crawler = new MTForumMemberDBCrawler(Table.MODE_ADD_NEW, "https://forum.minetest.net/viewforum.php?f=11", "div.pagination");
        //members_mtf_crawler.launchCrawl(db, db.tables.get(4));
        TableDBCrawler mods_crawler = new TableDBCrawler(Table.MODE_ADD_NEW, "https://wiki.minetest.net/List_of_Mods", "table.wikitable.sortable", TableDBCrawler.parse("Name-title\nShort name-name\nAuthor-author\nDescription-description\nForum thread-homepage-link\nCategory-type"), db.tables.get(0));
        //mods_crawler.launchCrawl(db, db.tables.get(0));
        TableDBCrawler games_crawler = new TableDBCrawler(Table.MODE_ADD_NEW, "https://wiki.minetest.net/List_of_Games", "table.wikitable.sortable", TableDBCrawler.parse("Code name-name\nName-title\nAuthor-author\nDescription-description\nWeb page-homepage-link\nType-type\nGenre-type"), db.tables.get(1));
        //new Crawlers(db, new Table[]{db.tables.get(0), db.tables.get(4), db.tables.get(0), db.tables.get(1)}, mods_mtf_crawler, members_mtf_crawler, mods_crawler, games_crawler).start();
        //games_crawler.launchCrawl(db,db.tables.get(1));
        //Document mt_games=Jsoup.connect("").get();
        //Element mt_games_el=mt_games.selectFirst("table.wikitable.sortable");
        //System.out.println(mt_games);

        //System.out.println(members_table);
        //ExtensionLoader.executePythonExtension("src/appguru/extensions/python/sample_extension.py");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        global_timer = new Timer(true);
        global_timer.addEvent(5000, new SchedulerTest());
        //ExtensionLoader.executeLuaExtension("res/extensions/lua/sample_extension.lua");
        //ExtensionLoader.executeJSExtension("res/extensions/javascript/sample_extension.js");
        //ExtensionLoader.compileJavaExtension("res/extensions/java/SampleExtension.java");
        //ExtensionLoader.executeJavaExtension("res/extensions/java/SampleExtension");
        /*java.io.File[] a0 = new java.io.File(subgame_path).listFiles();
        int i = a0.length;
        int i0 = 0;
        while(i0 < i) {
            java.io.File a1 = a0[i0];
            System.out.println(a1.getName());
            i0 = i0 + 1;
        }*/
        //appguru.helper.Zipper.unzip("chinook.zip", "chinook");
        ///appguru.helper.FileHelper.moveFolder("chinook", "test");
        order_columns = new String[db.tables.size()][];
        order_values = new Object[db.tables.size()][][];
        clauses = new Vector();
        for (int i = 0; i < db.tables.size(); i++) {
            clauses.add(new ArrayList());
            Table dbTable = db.tables.get(i);
            order_columns[i] = new String[dbTable.columns.length];
            System.arraycopy(dbTable.columns, 0, order_columns[i], 0, dbTable.columns.length);
            order_values[i] = new Object[1][dbTable.columns.length];
            for (int iw = 0; iw < order_values[i][0].length; iw++) {
                order_values[i][0][iw] = "Ignore";
            }
            //System.out.println(Arrays.deepToString(order_columns[i]));
        }
        application = new appguru.graphics.swing.Window("MTPC - Minetest Package Control");
        application.mainframe = true;
        application.setVisible(true);
        GRAPHICS = application.getGraphics();
        application.setVisible(false);
        try {
            application.setIconImage(ImageIO.read(new File("res/textures/logo256x256.png")));
        } catch (Exception e) {
            Console.errorMessage(AGE.class.getName(), "UNABLE TO SET ICON");
        }
        //application.window.setOpacity(1.0f);
        BasicTableModel table_model = new appguru.graphics.swing.BasicTableModel((appguru.db.Table) db.tables.get(0), db);
        /*BasicTableModel authors = new appguru.graphics.swing.BasicTableModel((appguru.db.Table) db.tables.get(1), db);
        authors.setListener(2, new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                System.out.println("yay");
                javax.swing.JFrame a0 = new javax.swing.JFrame(new StringBuilder().append("MTPC - Minetest Package Control - Author information : ").append(authors.getValueAt(authors.parent.getSelectedRow(), 1)).toString());
                BasicPanel a1 = new BasicPanel();
                a1.setLayout(new javax.swing.BoxLayout(a1, 3));
                int i = 0;
                while (i < authors.getColumnCount()) {
                    String s = authors.getColumnName(i);
                    BasicPanel a2 = new BasicPanel(new java.awt.FlowLayout(0));
                    a2.add(new javax.swing.JLabel(new StringBuilder().append(authors.getColumnName(i)).append(" : ").toString()));
                    Object a3 = authors.getValueAt(authors.parent.getSelectedRow(), i);
                    Class a4 = a3.getClass();
                    label0:
                    {
                        label1:
                        {
                            label2:
                            if (a4 != java.net.URL.class) {
                                if (!"github".equals(s)) {
                                    if (!"mt_forums".equals(s)) {
                                        a2.add(new javax.swing.JLabel(a3.toString()));
                                        break label0;
                                    } else {
                                        try {
                                            java.net.URL a8 = new java.net.URL("https://forum.minetest.net/memberlist.php?mode=viewprofile&un=" + a3);
                                            a2.add(new appguru.graphics.swing.BasicLink(a8.toString(), a8));
                                        } catch (java.net.MalformedURLException ignoredException) {
                                            break label2;
                                        }
                                        break label0;
                                    }
                                } else {
                                    try {
                                        java.net.URL a9 = new java.net.URL(new StringBuilder().append("https://www.github.com/").append(a3.toString()).toString());
                                        a2.add(new appguru.graphics.swing.BasicLink(a9.toString(), a9));
                                        System.out.println("GH ");
                                    } catch (java.net.MalformedURLException ignoredException0) {
                                        break label1;
                                    }
                                    break label0;
                                }
                            } else {
                                java.net.URL a10 = (java.net.URL) a3;
                                a2.add((java.awt.Component) new appguru.graphics.swing.BasicLink(a10.toString(), a10));
                                break label0;
                            }
                            appguru.info.Console.errorMessage(appguru.AGE.class.getName(), "IMPOSSIBLE ERROR - CONTACT DEVELOPER");
                            a2.add(new javax.swing.JLabel("IMPOSSIBLE ERROR - CONTACT DEVELOPER"));
                            break label0;
                        }
                        appguru.info.Console.errorMessage(appguru.AGE.class.getName(), "IMPOSSIBLE ERROR - CONTACT DEVELOPER");
                        a2.add(new javax.swing.JLabel("IMPOSSIBLE ERROR - CONTACT DEVELOPER"));
                    }
                    a1.add(a2);
                    i = i + 1;
                }
                BasicPanel a11 = new BasicPanel();
                a11.add(new javax.swing.JLabel(new javax.swing.ImageIcon(appguru.net.Downloader.downloadImage((java.net.URL) authors.getValueAt(authors.parent.getSelectedRow(), authors.parent.getColumn("profile_picture").getModelIndex())))));
                a1.add(a11);
                a0.add(a1);
                a0.pack();
                a0.setVisible(true);
            }
        });
        javax.swing.JTable authors_table = new javax.swing.JTable(authors);
        javax.swing.JScrollPane a6 = new javax.swing.JScrollPane(authors_table);
        a6.setBackground(BasicColorScheme.CS.alt_bg);
        a6.setViewportView(authors_table);
        authors_table.setFillsViewportHeight(true);*/
        table = new javax.swing.JTable(table_model);
        table.setRowHeight(30);
        table.setSelectionBackground(Color.GREEN);
        table.setGridColor(Color.BLACK);
        table.getColumn("options").setCellRenderer(table_model.br);
        table.getColumn("options").setCellEditor(table_model.be);
        //mods.setEditor();
        javax.swing.JScrollPane parent = new javax.swing.JScrollPane(table);
        parent.setVerticalScrollBar(new JScrollBar());
        //a8.setBackground(BasicColorScheme.CS.alt_bg);
        //System.out.println(parent.getComponentCount());
        parent.setBorder(new BasicRoundBorder(Color.BLACK, 8));
        parent.setViewportView(table);
        table.setFillsViewportHeight(true);

        table_model.setParent(table);
        new BasicPanel(new java.awt.FlowLayout(2)).add(parent);
        BasicPanel lower_buttons = new BasicPanel();
        lower_buttons.setLayout(new javax.swing.BoxLayout(lower_buttons, 2));
        BasicButton sort_mods = new BasicButton("Sorting Ruleset");
        BasicButton search_mods = new BasicButton("Search Query");
        BasicButton add_mods = new BasicButton("Add entry");
        table_model.attachAdding(add_mods);
        table_model.attachSearching(search_mods);
        BasicButton config = new BasicButton("Config");
        BasicButton crawling = new BasicButton("Crawlers");
        crawlers = new ArrayList();
        crawling.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                Window a0 = new Window("MTPC - Crawlers");
                a0.getContentPane().setBackground(Color.WHITE);
                BasicButton a1 = new BasicButton("Launch Crawlers");
                BasicButton a2 = new BasicButton("Cancel");
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a1, a0);
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a2, a0);
                javax.swing.JList clauses = new javax.swing.JList();
                clauses.setMinimumSize(new Dimension(1, 200));
                clauses.setBackground(Color.WHITE);
                clauses.setCellRenderer(new BasicListCellRenderer());
                String[] a4 = new String[crawlers.size()];
                int i = 0;
                while (i < crawlers.size()) {
                    a4[i] = crawlers.get(i).toNiceString();
                    i = i + 1;
                }
                clauses.setListData(a4);
                a1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        Crawlers.createCrawlers(db, crawlers).launch(table_model);
                        /*String s = (cls.isEmpty()) ? dbTable.grabAll() : dbTable.grabAllClauses(cls);
                        System.out.println(s);
                        values = dbTable.retrieveSelection(db.executeQuery(s));
                        Object a0 = values.iterator();
                        while (((java.util.Iterator) a0).hasNext()) {
                            ((java.util.ArrayList) ((java.util.Iterator) a0).next()).add((Object) "Options");
                        }*/
                        //parent.repaint();
                    }
                });
                BasicPanel a5 = new BasicPanel(new java.awt.FlowLayout(1));
                BasicButton del = new BasicButton("Delete");
                BasicButton add_member_crawler = new BasicButton("Add Minetest Forum Memberlist Crawler");
                BasicButton add_mt_forums_crawler = new BasicButton("Add Minetest Forum Topic Crawler");
                BasicButton add_table_crawler = new BasicButton("Add Table Crawler");
                add_member_crawler.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BasicFormspec window = new BasicFormspec("Grid 3 2 wrapped wrapped wrapped wrapped right left\nlabel1=Label(\"Insertion mode : \")\nu=ComboBox(\"Only add new;Update existing;Replace existing\")\nlabel=Label(\"Content percentage : \")\nh=NumberBox(\"1.0\" \"10.0\" \"0.0\" \"0.5\")\nir=Button(\"Add\")\niq=Button(\"Cancel\")");
                        SwingHelper.attachButtonCloseListener((BasicButton) window.variables.get("iq"), window.showWindow("MTPC - Crawlers - Add Minetest Forum Memberlist Crawler - Settings"));
                        ((BasicButton) window.variables.get("ir")).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MTForumMemberDBCrawler c = new MTForumMemberDBCrawler((byte) ((BasicComboBox) window.variables.get("u")).getSelectedIndex(), "https://forum.minetest.net/memberlist.php?sid=449cd10ee54595cf35acfccb69f9bcef", "li.rightside.pagination", db.tables.get(4));
                                c.amount *= ((BasicNumberBox) window.variables.get("h")).getWert();
                                crawlers.add(c);
                                String[] a4 = new String[crawlers.size()];
                                int i = 0;
                                while (i < crawlers.size() - 1) {
                                    a4[i] = crawlers.get(i).toNiceString();
                                    i = i + 1;
                                }
                                a4[crawlers.size() - 1] = crawlers.get(crawlers.size() - 1).toNiceString();
                                clauses.setListData(a4);
                            }
                        });
                    }
                });
                add_mt_forums_crawler.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BasicFormspec window = new BasicFormspec("Grid 6 2 wrapped wrapped wrapped wrapped wrapped wrapped wrapped wrapped wrapped wrapped right left\nlabel1=Label(\"Insertion mode : \")\nu=ComboBox(\"Only add new;Update existing;Replace existing\")\nlabel=Label(\"Content percentage : \")\nh=NumberBox(\"1.0\" \"10.0\" \"0.0\" \"0.5\")\nlabeli=Label(\"Table : \")\nk=ComboBox(\"Mods;Subgames;Texture Packs;Forks;Authors\")\nlabeli2=Label(\"Required type : \")\nf=Entry(\"mod\")\nlabeli2=Label(\"URL : \")\nurl=Entry(\"https://forum.minetest.net/viewforum.php?f=11\")\nir=Button(\"Add\")\niq=Button(\"Cancel\")");
                        SwingHelper.attachButtonCloseListener((BasicButton) window.variables.get("iq"), window.showWindow("MTPC - Crawlers - Add Minetest Forum Topic Crawler - Settings"));
                        ((BasicButton) window.variables.get("ir")).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String entry = ((BasicEntry) window.variables.get("f")).getText();
                                String url = ((BasicEntry) window.variables.get("url")).getText();
                                if (entry.isEmpty() || url.isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Error : Field left blank", "Please fill in the fields first !", (byte) 0);
                                    return;
                                }
                                ((BasicButton) window.variables.get("ir")).addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MTForumDBCrawler c = new MTForumDBCrawler((byte) ((BasicComboBox) window.variables.get("u")).getSelectedIndex(), url, entry, "div.pagination", db.tables.get(((BasicComboBox) window.variables.get("k")).getSelectedIndex()));
                                        c.amount *= ((BasicNumberBox) window.variables.get("h")).getWert();
                                        crawlers.add(c);
                                        String[] a4 = new String[crawlers.size()];
                                        int i = 0;
                                        while (i < crawlers.size() - 1) {
                                            a4[i] = crawlers.get(i).toNiceString();
                                            i = i + 1;
                                        }
                                        a4[crawlers.size() - 1] = crawlers.get(crawlers.size() - 1).toNiceString();
                                        clauses.setListData(a4);
                                    }
                                });
                            }
                        });
                    }
                });
                add_table_crawler.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BasicFormspec window = new BasicFormspec("vertical wrapped wrapped wrapped center\nshit=Formspec(\"Grid 4 2 wrapped wrapped wrapped wrapped wrapped wrapped wrapped wrapped\nlabel1=Label('Insertion mode : ')\nu=ComboBox('Only add new;Update existing;Replace existing')\nlabeli=Label('Table : ')\nbox=ComboBox('Mods;Subgames;Texture Packs;Forks;Authors')\nlabeli2=Label('CSS Table Selector : ')\ncss=Entry('table.wikitable.sortable')\nlabeli3=Label('URL : ')\nurl=Entry('https://wiki.minetest.net/List_of_Mods')\")\nad=Button(\"Add\")|del=Button(\"Delete\")\nlist=ScrollPane()\nir=Button(\"Add\")|iq=Button(\"Cancel\")");
                        final BasicComboBox req = (BasicComboBox) (((BasicFormspec) window.variables.get("shit")).variables.get("box"));
                        req.setSelectedIndex(0);
                        final BasicComboBox req2 = (BasicComboBox) (((BasicFormspec) window.variables.get("shit")).variables.get("u"));
                        req2.setSelectedIndex(0);
                        ((JScrollPane) window.variables.get("list")).setViewportView(new BasicList(new String[]{}));
                        ((JScrollPane) window.variables.get("list")).setBackground(Color.WHITE);
                        ((JScrollPane) window.variables.get("list")).setBorder(new BasicRoundBorder(Color.BLACK, 5));
                        final HashMap<String, String> cas = new HashMap();
                        ((BasicButton) window.variables.get("ad")).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                BasicFormspec win = new BasicFormspec("vertical wrapped wrapped center\nl=Label(\"DB : \")|e=ComboBox(\"A;B;C\")\nl=Label(\"HTML : \")|e2=Entry(\"HTML Column Header\")\nok=Button(\"Add\")|c=Button(\"Cancel\")");
                                BasicComboBox kombi = (BasicComboBox) win.variables.get("e");
                                kombi.removeAllItems();

                                for (String s : db.tables.get(req.getSelectedIndex()).c2) {
                                    kombi.addItem(s);
                                }
                                Window w = win.showWindow("MTPC - DB Crawler - Add column assignment");
                                SwingHelper.attachButtonCloseListener((BasicButton) win.variables.get("c"), w);
                                ((BasicButton) win.variables.get("ok")).addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        BasicEntry z = ((BasicEntry) win.variables.get("e2"));
                                        if (z.getText().isEmpty()) {
                                            BasicOptionPane.showMessage("MTPC - Error : Field left blank", "Please fill in the field first !", (byte) 0);
                                            return;
                                        }
                                        cas.put(z.getText(), ((BasicComboBox) win.variables.get("e")).getSelectedItem().toString());
                                        BasicList l = ((BasicList) ((JViewport) ((JScrollPane) window.variables.get("list")).getComponent(0)).getComponent(0));
                                        String[] data = new String[cas.size()];
                                        Iterator values = cas.values().iterator();
                                        Iterator keys = cas.keySet().iterator();
                                        for (int o = 0; o < data.length; o++) {
                                            data[o] = keys.next().toString() + " → " + values.next().toString();
                                        }
                                        l.setListData(data);
                                        ((JScrollPane) window.variables.get("list")).repaint();
                                        w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
                                    }
                                });
                            }
                        });
                        ((BasicButton) window.variables.get("ir")).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (cas.isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Error : No Column Assignments", "Please add some column assignments first !", (byte) 0);
                                    return;
                                }
                                String url = ((BasicEntry) (((BasicFormspec) window.variables.get("shit")).variables.get("url"))).getText();
                                String css = ((BasicEntry) (((BasicFormspec) window.variables.get("shit")).variables.get("css"))).getText();
                                if (url.isEmpty() || css.isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Error : Field left blank", "Please fill in the field first !", (byte) 0);
                                    return;
                                }
                                crawlers.add(new TableDBCrawler((byte) req2.getSelectedIndex(), url, css, cas, db.tables.get(req.getSelectedIndex())));
                                String[] a4 = new String[crawlers.size()];
                                int i = 0;
                                while (i < crawlers.size() - 1) {
                                    a4[i] = crawlers.get(i).toNiceString();
                                    i = i + 1;
                                }
                                a4[crawlers.size() - 1] = crawlers.get(crawlers.size() - 1).toNiceString();
                                clauses.setListData(a4);
                            }
                        });
                        SwingHelper.attachButtonCloseListener((BasicButton) window.variables.get("iq"), window.showWindow("MTPC - Crawlers - Add HTML Table Crawler - Settings"));
                    }
                });
                del.addActionListener(new ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        if (clauses.getModel().getSize() <= 1) {
                            if (clauses.getModel().getSize() == 1) {
                                clauses.setListData(new String[0]);
                                int i = 0;
                                while (i < crawlers.size()) {
                                    crawlers.remove(i);
                                    i = i + 1;
                                }
                            }
                        } else {
                            crawlers.remove(clauses.getSelectedIndex());
                            Object[] a1 = new Object[clauses.getModel().getSize() - 1];
                            int i0 = 0;
                            while (i0 < a1.length + 1) {
                                if (i0 >= clauses.getSelectedIndex()) {
                                    if (i0 > clauses.getSelectedIndex()) {
                                        a1[i0 - 1] = clauses.getModel().getElementAt(i0);
                                        if (i0 == 1) {
                                            a1[i0 - 1] = crawlers.get(0).toNiceString();
                                        }
                                    }
                                } else {
                                    a1[i0] = clauses.getModel().getElementAt(i0);
                                }
                                i0 = i0 + 1;
                            }
                            clauses.setListData(a1);
                        }
                        clauses.repaint();
                    }
                });
                BasicPanel a8 = new BasicPanel(new java.awt.FlowLayout(1));
                a8.add(a1);
                a8.add(a2);
                a5.add(add_member_crawler);
                a5.add(add_mt_forums_crawler);
                a5.add(add_table_crawler);
                a5.add(del);
                javax.swing.JScrollPane a9 = new javax.swing.JScrollPane(clauses);
                a9.setBackground(Color.WHITE);
                a9.setBorder(new BasicRoundBorder(Color.BLACK, 4));
                a9.setViewportView(clauses);
                a9.setPreferredSize(new java.awt.Dimension(600, 200));
                a0.getContentPane().setLayout(new javax.swing.BoxLayout(a0.getContentPane(), 3));
                a0.getContentPane().add(a8);
                a0.getContentPane().add(a9);
                a0.getContentPane().add(a5);
                a0.pack();
                a0.setVisible(true);
            }
        });
        BasicButton help = new BasicButton("Help");
        BasicFormspec a14 = new BasicFormspec("Absolute 100 40 unwrapped,0.0,0.0,0.5,0.5 unwrapped,0.5,0.0,1,0.5 unwrapped,0.0,0.5,0.5,1.0 unwrapped,0.5,0.5,1,1\nh=NumberBox(\"5.0\" \"1000.0\" \"0.0\" \"1.0\")\nb=Link(\"Hello World!\" \"https://www.example.com\")\nc=Button(\"Hello World!\")\nd=Button(\"Hello World!\")");
        BasicButton a15 = new BasicButton("Update repository");
        BasicNumberBox a16 = new BasicNumberBox(5.0, 1000.0, 0.0, 1.0);
        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window w = new Window("MTPC - Configuration");
                BasicFormspec fs = new BasicFormspec("Vertical Center Center Center\ncgc=Button(\"GUI Color\")|mtp=Button(\"Minetest Games Path\")\ncgc2=Label(\"Select Game : \")|mtp2=ComboBox(\"Specify;Gamepath;first;then;restart;config;window.\")\ncb=CheckButton(\"Break lines\")|cgc2=Button(\"Row Height\")");

                w.add(fs.panel);
                ((BasicCheckButton) fs.variables.get("cb")).setSelected(BasicTableModel.multiple_rows);
                ((BasicCheckButton) fs.variables.get("cb")).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BasicTableModel.multiple_rows = !BasicTableModel.multiple_rows;
                        table.repaint();
                    }
                });
                ((BasicButton) fs.variables.get("cgc2")).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Window w = new Window("MTPC - Change Row Height");
                        BasicFormspec fs2 = new BasicFormspec("Vertical Center Center Center\nnb=NumberBox(\"30\" \"200\" \"10\" \"5\")\nok=Button(\"Ok\")|c=Button(\"Cancel\")");
                        ((BasicNumberBox) fs2.variables.get("nb")).setWert(table.getRowHeight());
                        ((BasicButton) fs2.variables.get("ok")).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                table.setRowHeight((int) (((BasicNumberBox) fs2.variables.get("nb")).getWert()));
                            }
                        });
                        SwingHelper.attachButtonCloseListener(w, ((BasicButton) fs2.variables.get("c")));
                        w.add(fs2.panel);
                        w.pack();
                        w.setVisible(true);
                    }
                });
                ArrayList a = BasicTableModel.obtainSGS();
                if (!a.isEmpty()) {
                    ((BasicComboBox) fs.variables.get("mtp2")).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (((JComboBox) e.getSource()).getSelectedItem() == null) {
                                return;
                            }
                            BasicTableModel.SELECTED_SUBGAME = ((JComboBox) e.getSource()).getSelectedItem().toString();
                            System.out.println(BasicTableModel.SELECTED_SUBGAME);
                        }
                    });
                    ((BasicComboBox) fs.variables.get("mtp2")).removeAllItems();
                    for (String s : BasicTableModel.obtainSGS()) {
                        ((BasicComboBox) fs.variables.get("mtp2")).addItem(s);
                    }
                }
                if (!BasicTableModel.SELECTED_SUBGAME.isEmpty()) {
                    ((BasicComboBox) fs.variables.get("mtp2")).setSelectedItem(BasicTableModel.SELECTED_SUBGAME);
                }
                ((BasicButton) fs.variables.get("cgc")).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
                        Color c = JColorChooser.showDialog(null, "Choose GUI Color", BasicColorScheme.CS.bg);
                        if (c != null) {
                            BasicColorScheme.CS = new BasicColorScheme(c);
                            BasicOptionPane.showMessage("MTPC - Restarting", "In order to update the entire GUI, MTPC is being restarted.", (byte) 2);
                            application.dispatchEvent(new WindowEvent(application, WindowEvent.WINDOW_CLOSING));
                            Thread t = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        main(null);
                                    } catch (Exception ex) {
                                        Logger.getLogger(AGE.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            t.start();
                            return;
                        }
                    }
                });
                ((BasicButton) fs.variables.get("mtp")).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser choose = new JFileChooser();
                        choose.setCurrentDirectory(games);
                        choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int i = choose.showOpenDialog(null);
                        if (i == JFileChooser.APPROVE_OPTION) {
                            games = choose.getSelectedFile();
                        }
                    }
                });
                w.pack();
                w.setVisible(true);
            }
        });
        
        help.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                BasicOptionPane.showMessage("MTPC - Help", "Help\nMTPC - Minetest Package Control\nMTPC is a Package Control for Minetest with a Database in background.\nIt aims at being simple to use.\nIt offers many options to deal with packages.\nPlus, you can use crawlers.\n\nFirst Steps : \nBefore you can use MTPC properly, you have to specify the Minetest Game Folder Path in the Configuration. By the way, you can also specify your favorite GUI Color there.\n\nMake sure you also check out the crawlers !\n© Lars Müller<appgurueu.github.io> @appguru.eu", (byte) 3);
                //javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Help\nThis is MTPC.\n\u00a9 Lars M\u00fcller", "Help", 3);
            }
        });
        table_model.attachSorting(sort_mods);
        lower_buttons.add(sort_mods);
        lower_buttons.add(search_mods);
        lower_buttons.add(add_mods);
        //BasicRelativePanel panel=new BasicRelativePanel(new Dimension(100,20));
        //panel.add(, new Rect(0,0,1,1));
        //lower_buttons.add(new BasicComboBox(new String[]{"Hello", "Nice", "TO", "MEET", "yOu"}));
        //lower_buttons.add(new BasicPanel(a14.panel));
        //lower_buttons.add(a15);
        //lower_buttons.add(a16);
        lower_buttons.add(config);
        lower_buttons.add(crawling);
        lower_buttons.add(help);
        BasicPanel types_row = new BasicPanel();
        types_row.setLayout(new javax.swing.BoxLayout(types_row, 2));
        javax.swing.ButtonGroup types_buttongroup = new javax.swing.ButtonGroup();
        BasicToggleButton type_mods = new BasicToggleButton("Mods");
        types_buttongroup.add(type_mods);
        BasicToggleButton type_subgames = new BasicToggleButton("Games");
        types_buttongroup.add(type_subgames);
        BasicToggleButton type_tp = new BasicToggleButton("Texture Packs");
        BasicToggleButton type_forks = new BasicToggleButton("Forks");
        BasicToggleButton type_authors = new BasicToggleButton("Authors");
        types_buttongroup.add(type_forks);
        types_buttongroup.add(type_tp);
        types_buttongroup.add(type_authors);
        types_buttongroup.setSelected(type_mods.getModel(), true);
        types_row.add(type_mods);
        types_row.add(type_subgames);
        types_row.add(type_tp);
        types_row.add(type_forks);
        types_row.add(type_authors);
        BasicPanel a22 = new BasicPanel();
        a22.setLayout(new javax.swing.BoxLayout(a22, 2));
        javax.swing.ButtonGroup states_buttongroup = new javax.swing.ButtonGroup();
        javax.swing.JToggleButton available = new BasicToggleButton("Available");

        states_buttongroup.add(available);
        javax.swing.JToggleButton installed = new BasicToggleButton("Installed");
        available.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_model.toggleInstallOption("Install", false);
                if (type_mods.isSelected()) {
                    modifyTable("Mod", "screenshot", 0, table_model, db);
                } else if (type_subgames.isSelected()) {
                    modifyTable("Subgame", "screenshot", 1, table_model, db);
                }
            }
        });
        installed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_model.toggleInstallOption("Deinstall", false);
                if (type_mods.isSelected()) {
                    db.tables.get(5).delete(db);
                    db.tables.get(5).create(db);
                    File[] sgs = games.listFiles();
                    for (File p : sgs) {
                        if (!p.isDirectory()) {
                            continue;
                        }
                        String gameconf = FileHelper.readFile(p.getAbsolutePath() + File.separator + "game.conf");
                        if (gameconf == null) {
                            continue;
                        }
                        String gname = p.getName();
                        for (String gcline : gameconf.split("\n")) {
                            //gcline=gcline.replace("[ ]*=[ ]*", "=");
                            String[] parts = gcline.split("[ ]*=[ ]*");
                            if (parts[0].equals("name")) {
                                gname = parts[1];
                                break;
                            }
                        }
                        installedDB(p.getAbsolutePath() + File.separator + "mods", "mod", "\nsubgame=" + gname, 5, 0, db);
                    }
                    modifyTable("Mod", "screenshot", 5, table_model, db);
                } else {
                    db.tables.get(6).delete(db);
                    db.tables.get(6).create(db);
                    installedDB(games.getAbsolutePath(), "game", "", 6, 1, db);
                    modifyTable("Game", "screenshot", 6, table_model, db);
                }
            }
        });
        states_buttongroup.add(installed);
        javax.swing.JToggleButton to_upgrade = new BasicToggleButton("To update");
        to_upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_model.toggleInstallOption("Upgrade", true);
                if (type_mods.isSelected()) {
                    db.tables.get(5).delete(db);
                    db.tables.get(5).create(db);
                    File[] sgs = games.listFiles();
                    for (File p : sgs) {
                        if (!p.isDirectory()) {
                            continue;
                        }
                        String gameconf = FileHelper.readFile(p.getAbsolutePath() + File.separator + "game.conf");
                        String gname = p.getName();
                        for (String gcline : gameconf.split("\n")) {
                            String[] parts = gcline.split("[ ]*=[ ]*");
                            if (parts[0].equals("name")) {
                                gname = parts[1];
                                break;
                            }
                        }
                        upgradedDB(p.getAbsolutePath() + File.separator + "mods", "mod", "subgame=" + gname + "\n", 5, 0, db);
                    }
                    modifyTable("Mod", "screenshot", 5, table_model, db);
                } else {
                    db.tables.get(6).delete(db);
                    db.tables.get(6).create(db);
                    upgradedDB(games.getAbsolutePath(), "game", "", 6, 1, db);
                    modifyTable("Game", "screenshot", 6, table_model, db);
                }
            }
        });
        states_buttongroup.add(to_upgrade);
        states_buttongroup.setSelected(available.getModel(), true);
        a22.add(available);
        a22.add(installed);
        a22.add(to_upgrade);
        previous = 0;
        addActionListener(type_mods, "Mod", "screenshot", 0, table_model, db);
        addEnableListener(type_mods, available, installed, to_upgrade, states_buttongroup);
        addActionListener(type_subgames, "Game", "screenshot", 1, table_model, db);
        addEnableListener(type_subgames, available, installed, to_upgrade, states_buttongroup);
        addActionListener(type_tp, "Texture Pack", "screenshot", 2, table_model, db);
        addDisableListener(type_tp, available, installed, to_upgrade, states_buttongroup);
        addActionListener(type_forks, "Fork", "screenshot", 3, table_model, db);
        addDisableListener(type_forks, available, installed, to_upgrade, states_buttongroup);
        addActionListener(type_authors, "Author", "profile_picture", 4, table_model, db);
        addDisableListener(type_authors, available, installed, to_upgrade, states_buttongroup);
        BasicPanel a27 = new BasicPanel();
        a27.setLayout(new javax.swing.BoxLayout(a27, 3));
        a27.add(types_row);
        a27.add(a22);
        a27.add(parent);
        a27.add(lower_buttons);
        application.add(a27);
        application.pack();
        application.setMinimumSize(new Dimension(table.getMinimumSize().width + 20, 100));
        application.setFocusable(true);
        application.requestFocus();
        //application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.revalidate();
        application.setResizable(true);
        application.setVisible(true);
        global_timer.start();
        /*main=new Main();
        GLPane gpane=new GLPane(GLPane.STANDARD_CAPS,60,main);
        gpane.animator.add(gpane);
        w=new Window("AGE - Appguru General Engine - Appguru Logo");
        w.setSize(800, 800);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setResizable(true);
        w.setVisible(true);
        w.getContentPane().add(gpane);
        w.setFocusable(true);
        w.requestFocus();
        Main.events=new EventHandler(w);
        w.addMouseListener(Main.events);
        w.addMouseWheelListener(Main.events);
        w.addKeyListener(Main.events);
        Console.successMessage(Main.class.getName(), "FARTHER");
        gpane.start();*/
    }
}

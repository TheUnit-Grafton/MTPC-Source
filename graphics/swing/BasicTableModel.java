package appguru.graphics.swing;

import appguru.AGE;
import appguru.db.Clause;
import appguru.db.DataBase;
import appguru.graphics.swing.Window;
import appguru.graphics.swing.gui.basic.BasicButton;
import appguru.graphics.swing.gui.basic.BasicCheckButton;
import appguru.graphics.swing.gui.basic.BasicColorScheme;
import appguru.graphics.swing.gui.basic.BasicEntry;
import appguru.graphics.swing.gui.basic.BasicListCellRenderer;
import appguru.graphics.swing.gui.basic.BasicOptionPane;
import appguru.graphics.swing.gui.basic.BasicPanel;
import appguru.graphics.swing.gui.basic.BasicRoundBorder;
import appguru.graphics.swing.gui.basic.BasicSlider;
import appguru.graphics.swing.gui.basic.BasicComboBox;
import appguru.graphics.swing.gui.basic.BasicRadioButton;
import appguru.helper.FileHelper;
import appguru.helper.Zipper;
import appguru.net.Downloader;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

public class BasicTableModel extends javax.swing.table.AbstractTableModel {

    public String name = "Mod";
    public String image = "screenshot";
    final public static BasicButton BT;
    final public static java.awt.Color SFG;
    final public static java.awt.Color SBG;
    final public static java.awt.Color FG;
    final public static java.awt.Color BG;
    public static BufferedImage filled;
    public static BufferedImage outline;

    static {
        outline = new BufferedImage(20 * 5, 20, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D stg = outline.createGraphics();
        stg.setRenderingHints(BasicButton.AA);
        stg.setColor(Color.BLACK);
        for (int i = 0; i < 5; i += 1) {
            stg.drawOval(i * 20, 0, outline.getHeight() - 1, outline.getHeight() - 1);
        }
        filled = new BufferedImage(outline.getWidth(), outline.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        stg = filled.createGraphics();
        stg.setRenderingHints(BasicButton.AA);
        stg.setColor(Color.YELLOW);
        for (int i = 0; i < 5; i += 1) {
            stg.fillOval(i * 20, 0, outline.getHeight() - 1, outline.getHeight() - 1);
        }
        BT = new BasicButton("");
        SFG = java.awt.Color.BLACK;
        SBG = new java.awt.Color(75, 75, 150);
        FG = java.awt.Color.BLACK;
        BG = new java.awt.Color(150, 150, 255);
    }

    public java.util.ArrayList<appguru.db.Clause> cls;
    public String[] names;
    public ActionListener[] listeners;
    public int[] order;
    public boolean[] ascending;
    public javax.swing.JTable parent;
    public appguru.db.Table dbTable;
    public appguru.db.DataBase db;
    public boolean[] editable;
    public boolean[] unique;
    public String[] columns;
    public String[] order_columns;
    public Object[][] order_values;
    public ArrayList<ArrayList> values;
    public ButtonRenderer br;
    public ButtonEditor be;
    public ActionListener INSTALL;
    public ActionListener DEINSTALL;
    public static String SELECTED_SUBGAME = "";

    public void toggleInstallOption(String suck, boolean install) {
        names[5] = suck;
        listeners[5] = install ? INSTALL : DEINSTALL;
        be = new ButtonEditor(new JCheckBox());
    }

    class NotifyCellEditor extends javax.swing.DefaultCellEditor {

        public NotifyCellEditor(BasicEntry a0, Class a1) {
            super(a0);
        }

        public Object getCellEditorValue() {
            int i = ((Integer) getValueAt(parent.getSelectedRow(), 0));
            Object resultval = super.getCellEditorValue();
            try {
                switch (dbTable.types[parent.getSelectedColumn()]) {
                    case DataBase.INTEGER:
                        resultval = Integer.parseInt(resultval.toString());
                        break;
                    case DataBase.REAL:
                        resultval = Float.parseFloat(resultval.toString());
                        break;
                    case DataBase.LINK:
                        resultval = new URL(resultval.toString());
                        break;
                }
                dbTable.updateColumn(db, i, parent.getSelectedColumn(), resultval);
            } catch (Exception ee) {
                BasicOptionPane.showMessage("MTPC - Add entry - Error", "Error : Fields have to be formatted properly. Please format the field \"" + getColumnName(parent.getSelectedColumn()) + "\"", (byte) 0);
                return parent.getValueAt(parent.getSelectedRow(), parent.getSelectedColumn());
            }
            return resultval;
        }
    }

    public static DecimalFormat FORMAT = new DecimalFormat("#########.######");
    public static boolean multiple_rows = true;

    class NormalRenderer implements javax.swing.table.TableCellRenderer {

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable a, Object a0, boolean b, boolean hasFocus, int row, int column) {
            BasicPanel l = new BasicPanel();
            l.setLayout(new BoxLayout(l,BoxLayout.PAGE_AXIS));
            String content = "";
            Class<?> a0_class = a0.getClass();
            if (a0_class == Float.class) {
                content = BasicTableModel.FORMAT.format((float) a0);
            } else if (a0_class == Double.class) {
                content = BasicTableModel.FORMAT.format((double) a0);
            } else {
                content = a0.toString();
            }
            if (!multiple_rows) {
                int lw = parent.getColumn(getColumnName(column)).getWidth() - 15;
                int xp = 0;
                FontMetrics m = parent.getGraphics().getFontMetrics();
                boolean similar = true;
                for (int x = 0; x < content.length(); x++) {
                    char c = content.charAt(x);
                    xp += m.charWidth(c);
                    if (xp > lw) {
                        similar = false;
                        if (x == 0) {
                            content = "…";
                            break;
                        }
                        content = content.substring(0, x - 1) + "…";
                        break;
                    }
                }
                if (similar) {
                    l.add(new JLabel(content));
                } else {
                    l.setLayout(new FlowLayout(FlowLayout.LEFT));
                    l.add(new JLabel(content), BorderLayout.WEST);
                }

            } else {
                int lw = parent.getColumn(getColumnName(column)).getWidth()-20;
                FontMetrics m = parent.getGraphics().getFontMetrics();
                boolean similar = true;
                int lx=0;
                String until="";
                for (int x = 0; x < content.length(); x++) {
                    char c = content.charAt(x);
                    until+=c;
                    int xp=m.stringWidth(until);
                    if (xp > lw) {
                        similar = false;
                        if (x==0) {
                            continue;
                        }
                        l.add(new JLabel(until.substring(0, until.length()-1)));
                        until=""+c;
                    }
                }
                if (similar) {
                    l.add(new JLabel(content));
                }
                else {
                    l.add(new JLabel(until));
                }
            }
            if (b) {
                l.setBackground(BasicColorScheme.CS.bg);
            } else {
                l.setBackground(BasicColorScheme.CS.alt_bg);
            }
            if (hasFocus) {
                l.setBackground(BasicColorScheme.CS.bgp);
            }
            return l;
        }

    }

    class RatingRenderer implements javax.swing.table.TableCellRenderer {

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable a, Object a0, boolean b, boolean hasFocus, int i, int i0) {
            BufferedImage rendered = new BufferedImage(20 * 5, 20, BufferedImage.TYPE_INT_RGB);
            Graphics2D rg = rendered.createGraphics();
            if (b) {
                rg.setBackground(BasicColorScheme.CS.bg);
            } else {
                rg.setBackground(BasicColorScheme.CS.alt_bg);
            }
            if (hasFocus) {
                rg.setBackground(BasicColorScheme.CS.bgp);
            }
            rg.clearRect(0, 0, 5 * 20, 20);
            if (a0 instanceof Float) {
                rg.clipRect(0, 0, (int) ((Float) a0 * 20), 20);
            } else {
                rg.clipRect(0, 0, (int) ((Integer) a0 * 20), 20);

            }
            rg.drawImage(filled, null, 0, 0);
            rg.setClip(null);
            rg.drawImage(outline, null, 0, 0);
            BasicPanel l = new BasicPanel();
            l.add(new JLabel(new ImageIcon(rendered)));
            if (b) {
                l.setBackground(BasicColorScheme.CS.bg);
            } else {
                l.setBackground(BasicColorScheme.CS.alt_bg);
            }
            if (hasFocus) {
                l.setBackground(BasicColorScheme.CS.bgp);
            }
            return l;
        }

    }

    class ButtonRenderer implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable a, Object a0, boolean b, boolean b0, int i, int i0) {
            /*if (b) {
                this.setForeground(a.getSelectionForeground());
                this.setBackground(a.getSelectionBackground());
            } else {
                this.setForeground(appguru.graphics.swing.BasicTable.BT.getForeground());
                this.setBackground(appguru.graphics.swing.BasicTable.BT.getBackground());
            }*/
            return new BasicButton((a0 != null) ? a0.toString() : "");
        }
    }

    public class ButtonEditor extends javax.swing.DefaultCellEditor {

        public BasicButton button;
        public BasicButton[] jbuttons;

        public ButtonEditor(javax.swing.JCheckBox a0) {
            super(a0);
            this.button = new BasicButton("Ivegottatext");
            this.jbuttons = new BasicButton[names.length];
            int i = 0;
            while (i < names.length) {
                this.jbuttons[i] = new BasicButton(names[i]);
                Object a1 = listeners[i];
                if (a1 != null) {
                    BasicButton a2 = this.jbuttons[i];
                    Object a3 = listeners[i];
                    a2.addActionListener((java.awt.event.ActionListener) a3);
                }
                i = i + 1;
            }
            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent a) {
                    Window a0 = new Window("MTPC - Select option for " + name + " : " + getValueAt(parent.getSelectedRow(), 3));
                    java.awt.FlowLayout a1 = new java.awt.FlowLayout();
                    a1.setAlignment(0);
                    BasicPanel a2 = new BasicPanel(a1);
                    BasicButton[] a3 = jbuttons;
                    int i = a3.length;
                    int i0 = 0;
                    while (i0 < i) {
                        BasicButton a4 = a3[i0];
                        appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a4, a0);
                        a2.add(a4);
                        i0 = i0 + 1;
                    }
                    a0.getContentPane().add(a2, "South");
                    a0.pack();
                    a0.setVisible(true);
                }
            });
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable a, Object a0, boolean b, int i, int i0) {
            /*if (b) {
                this.button.setForeground(a.getSelectionForeground());
                this.button.setBackground(a.getSelectionBackground());
            } else {
                this.button.setForeground(this.button.getForeground());
                this.button.setBackground(this.button.getBackground());
            }*/
            this.button.setText((a0 != null) ? a0.toString() : "");
            return this.button;
        }

        @Override
        public Object getCellEditorValue() {
            return this.button.getText();
        }

        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        public void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public void removeRow(int i) {
        values.remove(parent.getSelectedRow());
        parent.setModel(this);
        parent.remove(parent.getComponentCount() - 1);
    }

    public boolean downloadAndUnpack(String narg, URL download) {
        if (narg.equals("Author")) {
            BasicOptionPane.showMessage("MTPC - Unavailable option for " + name + "s selected", name + "s can't be downloaded & unpacked !", (byte) 0);
            return false;
        }
        //Downloader.downloadTo(getValueAt(parent.getSelectedRow(),parent.getColumn("download").getModelIndex()).toString(),"MTPC_Downloads"+File.separator+name+"s"+File.separator+getValueAt(parent.getSelectedRow(),parent.getColumn("name").getModelIndex()).toString()+".zip");
        boolean success = Downloader.downloadTo(download.toString(), "MTPC_Downloads" + File.separator + name + "s" + File.separator + narg + ".zip");
        if (!success) {
            BasicOptionPane.showMessage("MTPC - " + narg + " download failed : " + narg, narg + " could not be downloaded.\nPossible reasons :\n1. Invalid link\n2. No connection\n3. Target file/folder is protected.\nIf none of these matches, please contact the developer.", (byte) 0);
            return false;
        }
        success = Zipper.unzip("MTPC_Downloads" + File.separator + name + "s" + File.separator + narg + ".zip", "MTPC_Downloads" + File.separator + name + "s" + File.separator + narg, true, name + "s");
        if (success) {
            BasicOptionPane.showMessage("MTPC - " + narg + " downloaded & unpacked : " + narg, narg + " was downloaded & unpacked successfully !", (byte) 4);
        } else {
            BasicOptionPane.showMessage("MTPC - " + narg + " unpacking failed : " + narg, narg + " could not be unpacked.\nPossible reasons :\n1. Invalid file format\n2. Corrupted file\n3. Target file/folder is protected.\nIf none of these matches, please contact the developer.", (byte) 0);
        }
        return success;
    }

    public boolean install(String sg, String narg) {
        ArrayList object = dbTable.retrieveSelection(db.executeQuery(dbTable.grabAllClauses(new Clause(true, true, false, false, "name", "=", narg)))).get(0);
        boolean b = downloadAndUnpack(narg, (URL) object.get(columnIndex("download")));
        if (!b) {
            return false;
        }
        boolean b2 = FileHelper.moveFolder("MTPC_Downloads" + File.separator + name + "s" + File.separator + narg, AGE.games.getAbsolutePath() + File.separator + sg + File.separator + "mods" + File.separator + narg);
        if (b2) {
            BasicOptionPane.showMessage("MTPC - " + name + " installed : " + narg, narg + " was installed successfully !", (byte) 4);
        } else {
            BasicOptionPane.showMessage("MTPC - " + name + " could not be installed : " + narg, narg + " could not be installed.", (byte) 0);

        }
        String[] dependencies = ((String) object.get(columnIndex("depends"))).toLowerCase().split("[ ]*,[ ]*");
        for (String depend : dependencies) {
            if (depend.equals("none") || depend.charAt(depend.length() - 1) == '?') {
                continue;
            }
            //System.out.println(depend);
            File[] lfs = new File(AGE.games.getAbsolutePath() + File.separator + sg + File.separator + "mods").listFiles();
            label0:
            {
                for (File f : lfs) {
                    if (f.getName().equals(depend)) {
                        break label0;
                    }
                }
                install(sg, depend);
            }
        }
        return b2;
    }

    public boolean installSG() {
        String narg = (String) getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex());
        ArrayList object = dbTable.retrieveSelection(db.executeQuery(dbTable.grabAllClauses(new Clause(true, true, false, false, "name", "=", narg)))).get(0);
        boolean b = downloadAndUnpack(narg, (URL) object.get(columnIndex("download")));
        if (!b) {
            return false;
        }
        boolean b2 = FileHelper.moveFolder("MTPC_Downloads" + File.separator + name + "s" + File.separator + narg, AGE.games.getAbsolutePath() + File.separator + narg);
        if (b2) {
            BasicOptionPane.showMessage("MTPC - " + name + " installed : " + narg, narg + " was installed successfully !", (byte) 4);
        } else {
            BasicOptionPane.showMessage("MTPC - " + name + " could not be installed : " + narg, narg + " could not be installed.", (byte) 0);
        }

        return b2;
    }

    public String obtainSGPath(String sgname) {
        String game_name = sgname;
        File[] sgs = AGE.games.listFiles();
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
            if (gname.equals(game_name)) {
                game_name = p.getName();
                break;
            }
        }
        return game_name;
    }

    public static ArrayList<String> obtainSGS() {
        ArrayList<String> sgsl = new ArrayList();
        File[] sgs = AGE.games.listFiles();
        if (sgs == null) {
            return sgsl;
        }
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
            sgsl.add(gname);
        }
        return sgsl;
    }

    public int columnIndex(String col) {
        return parent.getColumn(col).getModelIndex();
    }

    public BasicTableModel(appguru.db.Table a, appguru.db.DataBase a0) {
        super();
        this.cls = new java.util.ArrayList();
        String[] a1 = new String[]{"Delete", "Rate", "Info", "Download", "Download & Unpack", "Install"};
        this.names = a1;
        listeners = new java.awt.event.ActionListener[6];
        this.listeners[0] = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                dbTable.delete(db, (int) (getValueAt(parent.getSelectedRow(), 0)));
                removeRow(parent.getSelectedRow());
                //BasicOptionPane.showMessage("MTPC - Minetest Package Control - "+name+" entry deleted", name+" was deleted successfully !", (byte)4);
                parent.repaint();
            }
        };
        this.listeners[1] = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent a) {
                Window a0 = new Window(new StringBuilder().append("MTPC - Rate " + name + " : ").append(getValueAt(parent.getSelectedRow(), 3)).toString());
                BasicSlider a1 = new BasicSlider(BasicSlider.HORIZONTAL);
                a1.setMinimum(0);
                a1.setMaximum(500);
                BasicPanel a2 = new BasicPanel();
                a2.setLayout(new javax.swing.BoxLayout(a2, 2));
                a2.add(new javax.swing.JLabel("Your rating : "));
                a2.add(a1);
                BasicButton a3 = new BasicButton("Rate");
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a3, a0);
                a3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        int i = parent.getColumn("rating").getModelIndex();
                        int i0 = parent.getColumn("ratings").getModelIndex();
                        int i1 = (int) (getValueAt(parent.getSelectedRow(), 0));
                        float f = ((Number) getValueAt(parent.getSelectedRow(), i)).floatValue();
                        int i2 = ((Number) getValueAt(parent.getSelectedRow(), i0)).intValue();
                        float f0 = (f * i2 + a1.getValue() / 100f) / (i2 + 1);
                        setValueAt(Float.valueOf(f0), parent.getSelectedRow(), i);
                        setValueAt(Integer.valueOf(i2 + 1), parent.getSelectedRow(), i0);
                        dbTable.updateColumn(db, i1, i, Float.valueOf(f0));
                        dbTable.updateColumn(db, i1, i0, Integer.valueOf(i2 + 1));
                        parent.repaint();
                    }
                });
                a2.add(a3);
                a2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
                a0.add(a2, "South");
                a0.pack();
                a0.setVisible(true);
            }
        };
        this.listeners[2] = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                String cname = name.equals("Author") ? "author" : "name";
                Window a0 = new Window("MTPC - " + name + " information : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()));
                BasicPanel a1 = new BasicPanel();
                a1.setLayout(new javax.swing.BoxLayout(a1, 3));
                int i = 0;
                int author_index = parent.getColumn("author").getModelIndex();
                while (i < getColumnCount()) {
                    BasicPanel a2 = new BasicPanel(new java.awt.FlowLayout(0));
                    a2.add(new javax.swing.JLabel(new StringBuilder().append(getColumnName(i)).append(" : ").toString()));
                    Object a3 = getValueAt(parent.getSelectedRow(), i);
                    if (a3.getClass() != java.net.URL.class) {
                        a2.add(new javax.swing.JLabel(a3.toString()));
                    } else {
                        java.net.URL a4 = (java.net.URL) a3;
                        a2.add((java.awt.Component) new appguru.graphics.swing.BasicLink(a4.toString(), a4));
                    }
                    if (i == author_index) {
                        BasicButton info = new BasicButton("Info");
                        a2.add(new BasicButton("Info"));
                    }
                    a1.add(a2);
                    i++;
                }
                BasicPanel a5 = new BasicPanel();
                try {
                    a5.add(new javax.swing.JLabel(new javax.swing.ImageIcon(appguru.net.Downloader.downloadImage((java.net.URL) getValueAt(parent.getSelectedRow(), parent.getColumn(image).getModelIndex())))));
                } catch (Exception e) {
                }
                a1.add(a5);
                a0.add(a1);
                a0.pack();
                a0.setVisible(true);
            }
        };
        this.listeners[3] = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                if (name.equals("Author")) {
                    BasicOptionPane.showMessage("MTPC - Unavailable option for " + name + "s selected.", name + "s can't be downloaded !", (byte) 0);
                    return;
                }
                boolean success = Downloader.downloadTo(getValueAt(parent.getSelectedRow(), parent.getColumn("download").getModelIndex()).toString(), "MTPC_Downloads" + File.separator + name + "s" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString() + ".zip");
                String cname = name.equals("Author") ? "author" : "name";
                if (success) {
                    BasicOptionPane.showMessage("MTPC - " + name + " downloaded : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()), name + " was downloaded successfully !", (byte) 4);
                } else {
                    BasicOptionPane.showMessage("MTPC - " + name + " download failed : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()), name + " could not be downloaded.\nPossible reasons :\n1. Invalid link\n2. No connection\n3. Target file/folder is protected.\nIf none of these matches, please contact the developer.", (byte) 0);
                }
            }
        };
        this.listeners[4] = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent f) {
                /*if (name.equals("Author")) {
                    BasicOptionPane.showMessage("MTPC - Minetest Package Control - Unavailable option for " + name + "s selected", name + "s can't be downloaded & unpacked !", (byte) 0);
                    return;
                }
                //Downloader.downloadTo(getValueAt(parent.getSelectedRow(),parent.getColumn("download").getModelIndex()).toString(),"MTPC_Downloads"+File.separator+name+"s"+File.separator+getValueAt(parent.getSelectedRow(),parent.getColumn("name").getModelIndex()).toString()+".zip");
                boolean success = Downloader.downloadTo(getValueAt(parent.getSelectedRow(), parent.getColumn("download").getModelIndex()).toString(), "MTPC_Downloads" + File.separator + name + "s" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString() + ".zip");
                String cname = name.equals("Author") ? "author" : "name";
                if (!success) {
                    BasicOptionPane.showMessage("MTPC - Minetest Package Control - " + name + " download failed : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()), name + " could not be downloaded.\nPossible reasons :\n1. Invalid link\n2. No connection\n3. Target file/folder is protected.\nIf none of these matches, please contact the developer.", (byte) 0);
                    return;
                }
                success = Zipper.unzip("MTPC_Downloads" + File.separator + name + "s" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString() + ".zip", "MTPC_Downloads" + File.separator + name + "s" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString(), true, name + "s");
                if (success) {
                    BasicOptionPane.showMessage("MTPC - Minetest Package Control - " + name + " downloaded & unpacked : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()), name + " was downloaded & unpacked successfully !", (byte) 4);
                } else {
                    BasicOptionPane.showMessage("MTPC - Minetest Package Control - " + name + " unpacking failed : " + getValueAt(parent.getSelectedRow(), parent.getColumn(cname).getModelIndex()), name + " could not be unpacked.\nPossible reasons :\n1. Invalid file format\n2. Corrupted file\n3. Target file/folder is protected.\nIf none of these matches, please contact the developer.", (byte) 0);
                }*/
                downloadAndUnpack((String) getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()), (URL) getValueAt(parent.getSelectedRow(), parent.getColumn("download").getModelIndex()));

            }
        };
        /*a2[5] = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a2[4].actionPerformed(null);
                String game_name = "wl";
                FileHelper.moveFolder("MTPC_Downloads" + File.separator + name + "s" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString(), AGE.games.getAbsolutePath() + File.separator + game_name + File.separator + "mods" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()));
                BasicOptionPane.showMessage("MTPC - Minetest Package Control - " + name + " installed : " + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()), name + " was installed successfully !", (byte) 4);

            }
        };*/
        SELECTED_SUBGAME = "LOTH";
        DEINSTALL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.equals("Mod")) {
                    listeners[0].actionPerformed(null);
                    String game_name = obtainSGPath(getValueAt(parent.getSelectedRow(), parent.getColumn("subgame").getModelIndex()).toString());
                    FileHelper.deleteAll(new File(AGE.games.getAbsolutePath() + File.separator + game_name + File.separator + "mods" + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex())));
                } else if (name.equals("Game")) {
                    listeners[0].actionPerformed(null);
                    FileHelper.deleteAll(new File(AGE.games.getAbsolutePath() + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex())));

                } else {
                    BasicOptionPane.showMessage("MTPC - Unavailable option for " + name + "s selected.", name + "s can't be deinstalled !", (byte) 0);
                    return;
                }
                BasicOptionPane.showMessage("MTPC - " + name + " deinstalled : " + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()), name + " was deinstalled successfully !", (byte) 4);
            }
        };
        INSTALL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.equals("Mod")) {
                    install(obtainSGPath(SELECTED_SUBGAME), getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex()).toString());
                } else if (name.equals("Game")) {
                    installSG();
                    //listeners[0].actionPerformed(null);
                    //FileHelper.deleteAll(new File(AGE.games.getAbsolutePath() + File.separator + getValueAt(parent.getSelectedRow(), parent.getColumn("name").getModelIndex())));

                } else {
                    BasicOptionPane.showMessage("MTPC - Unavailable option for " + name + "s selected.", name + "s can't be installed !", (byte) 0);
                    return;
                }
            }
        };
        this.listeners[5] = INSTALL;
        this.br = new ButtonRenderer();
        this.be = new ButtonEditor(new javax.swing.JCheckBox());
        this.db = a0;
        this.dbTable = a;
        this.columns = new String[a.columns.length + 1];
        System.arraycopy(this.dbTable.columns, 0, this.columns, 0, this.dbTable.columns.length);
        this.columns[this.dbTable.columns.length] = "options";
        this.values = this.dbTable.retrieveSelection(a0.executeQuery(this.dbTable.grabAll()));
        Object a3 = this.values.iterator();
        while (((java.util.Iterator) a3).hasNext()) {
            ((Collection) ((java.util.Iterator) a3).next()).add("Options");
        }
        this.order_columns = new String[this.dbTable.columns.length];
        System.arraycopy(this.dbTable.columns, 0, this.order_columns, 0, this.dbTable.columns.length);
        this.order_values = new String[1][this.dbTable.columns.length];
        int i = 0;
        while (i < this.dbTable.columns.length) {
            this.order_values[0][i] = "Ignore";
            i += 1;
        }
        editable = new boolean[getColumnCount()];
        unique = new boolean[getColumnCount()];
        setNotEditable("id", "rating", "ratings");
        setUnique("name");
    }

    public void setListener(int i, java.awt.event.ActionListener a) {
        this.listeners[i] = a;
        this.be = new ButtonEditor(new javax.swing.JCheckBox());
    }

    public void setParent(JTable parent) {
        this.parent = parent;
        for (int i = 0; i < getColumnCount(); i++) {
            parent.getColumn(this.getColumnName(i)).setCellRenderer(new NormalRenderer());
        }
        FontMetrics m = outline.createGraphics().getFontMetrics();
        for (int i = 0; i < columns.length; i++) {
            int w = m.stringWidth(this.getColumnName(i));
            parent.getColumn(this.getColumnName(i)).setMinWidth(w + 20);
        }
        parent.getColumn("options").setCellRenderer(br);
        parent.getColumn("options").setCellEditor(be);
        try {
            TableColumn c = parent.getColumn("description");
            if (c != null) {
                c.setMinWidth(160);
            }
        } catch (Exception e) {
        }
        parent.getColumn("options").setMinWidth(80);
        parent.getColumn("rating").setCellRenderer(new RatingRenderer());
        parent.getColumn("rating").setMinWidth(120);
        setEditor();
    }

    public void updateTable() {
        for (int i = 0; i < columns.length; i++) {
            parent.getTableHeader().getColumnModel().removeColumn(parent.getColumn(parent.getColumnName(0)));
        }
        columns = new String[dbTable.columns.length + 1];
        System.arraycopy(dbTable.columns, 0, columns, 0, dbTable.columns.length);
        columns[columns.length - 1] = "options";
        String grab_query = cls.isEmpty() ? dbTable.grabAll() : dbTable.grabAllClauses(cls);
        ResultSet s = order == null || order.length == 0 ? db.executeQuery(grab_query) : dbTable.orderedQuery(db, grab_query, order, ascending);
        ArrayList<ArrayList> a = dbTable.retrieveSelection(s);
        for (ArrayList z : a) {
            z.add("Options");
        }
        for (int i = 0; i < columns.length; i++) {
            TableColumn t = new TableColumn(i);
            t.setIdentifier((String) columns[i]);
            t.setHeaderValue((String) columns[i]);
            parent.getTableHeader().getColumnModel().addColumn(t);
        }
        this.values = new ArrayList();
        this.values.addAll(a);
        //parent=new JTable(this);
        parent.repaint();
        setParent(this.parent);
    }

    public void attachAdding(AbstractButton a) {
        a.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                Window frame = new Window("MTPC - Add entry");
                BasicPanel cpane = new BasicPanel();
                cpane.setLayout(new javax.swing.BoxLayout(cpane, 3));
                cpane.setBackground(Color.WHITE);
                int i = -1;
                while (i < getColumnCount() - 2) {
                    i++;
                    boolean rating = false;
                    if (editable[i]) {
                        if (getColumnName(i).equals("rating") == false) {
                            continue;
                        } else {
                            rating = true;
                        }
                    }
                    BasicPanel line = new BasicPanel(new java.awt.FlowLayout(0));
                    String linetitle = getColumnName(i);
                    byte type = dbTable.types[i];
                    String entry_example = "";
                    switch (type) {
                        case DataBase.INTEGER:
                            linetitle += "(integer number)";
                            entry_example = "11111111111111111111";
                            break;
                        case DataBase.REAL:
                            linetitle += "(floating point number)";
                            entry_example = "1111111111.1111111111";
                            break;
                        case DataBase.LINK:
                            linetitle += "(url)";
                            entry_example = "https://www.example.com";
                            break;
                        default:
                            linetitle += "(plain text)";
                            entry_example = "No Value/Unknown";
                    }
                    if (unique[i] == true) {
                        linetitle += "(unique)";
                    }
                    line.add(new javax.swing.JLabel(linetitle + " : "));
                    if (!rating) {
                        BasicEntry bee = new BasicEntry(entry_example);
                        bee.setSize(200, bee.getHeight());
                        //bee.setPreferredSize(new Dimension(200,bee.getHeight()));
                        line.add(bee);
                    } else {
                        BasicSlider bs = new BasicSlider(BasicSlider.HORIZONTAL);
                        bs.setMinimum(0);
                        bs.setMaximum(500);
                        line.add(bs);
                    }
                    line.setBackground(Color.WHITE);
                    cpane.add(line);
                }
                BasicPanel line = new BasicPanel(new FlowLayout(FlowLayout.CENTER));
                line.setBackground(Color.WHITE);
                BasicButton add = new BasicButton("Add");
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int i = 0;
                        int ci = 0;
                        Object[] wvalues = new Object[getColumnCount() - 2];
                        while (i < getColumnCount() - 2) {
                            i++;
                            boolean rating = false;
                            if (editable[i]) {
                                if (getColumnName(i).equals("rating") == false) {
                                    wvalues[i - 1] = 1;
                                    continue;
                                } else {
                                    rating = true;
                                }
                            }
                            BasicPanel atci = (BasicPanel) cpane.getComponent(ci);
                            Object resultval;
                            if (!rating) {
                                resultval = ((JTextComponent) atci.getComponent(1)).getText();
                                if (((String) resultval).isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Add entry - Error", "Error : You may not leave fields blank. Please fill in field \"" + getColumnName(i) + "\"", (byte) 0);
                                    return;
                                }
                                byte type = dbTable.types[i];
                                try {
                                    switch (type) {
                                        case DataBase.INTEGER:
                                            resultval = Integer.parseInt((String) resultval);
                                            break;
                                        case DataBase.REAL:
                                            resultval = Float.parseFloat((String) resultval);
                                            break;
                                        case DataBase.LINK:
                                            resultval = new URL((String) resultval);
                                            break;
                                        default:
                                            resultval = resultval;
                                    }
                                } catch (Exception ee) {
                                    BasicOptionPane.showMessage("MTPC - Add entry - Error", "Error : Fields have to be formatted properly. Please format the field \"" + getColumnName(i) + "\"", (byte) 0);

                                    //JOptionPane.showMessageDialog(null, "Error : Fields have to be formatted properly. Please format the field \"" + getColumnName(i) + "\"", "MTPC - Minetest Package Control - Add entry - Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                resultval = ((JSlider) atci.getComponent(1)).getValue() / 100.0f;
                            }
                            if (unique[i] == true) {
                                Clause c = new Clause(true, resultval instanceof String, false, false, getColumnName(i), "=", resultval.toString());
                                ArrayList<Clause> claus = new ArrayList();
                                claus.add(c);
                                if (!dbTable.retrieveSelection(db.executeQuery(dbTable.grabAllClauses(claus))).isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Add entry - Error", "Error : Some fields have to be unique. Please change the value of field \"" + getColumnName(i) + "\"", (byte) 0);

                                    //JOptionPane.showMessageDialog(null, "Error : Some fields have to be unique. Please change the value of field \"" + getColumnName(i) + "\"", "MTPC - Minetest Package Control - Add entry - Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            wvalues[i - 1] = resultval;
                            ci++;
                            cpane.add(line);
                        }
                        //System.out.println(Arrays.toString(wvalues));
                        dbTable.insertAllSorted(db, wvalues);
                        updateTable();
                        BasicOptionPane.showMessage("MTPC - Add entry - Success", "Entry was added to DB successfully!", (byte) 4);

                    }
                });
                BasicButton cancel = new BasicButton("Cancel");
                SwingHelper.attachButtonCloseListener(frame, cancel);
                line.add(add);
                line.add(cancel);
                cpane.add(line);
                //BasicPanel a5 = new BasicPanel();
                //a5.add(new javax.swing.JLabel((javax.swing.Icon) (Object) new javax.swing.ImageIcon((java.awt.Image) appguru.net.Downloader.downloadImage((java.net.URL) getValueAt(parent.getSelectedRow(), parent.getColumn((Object) "screenshot").getModelIndex())))));
                //cpane.add(a5);
                frame.add(cpane);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public void attachSearching(javax.swing.AbstractButton a) {
        a.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                Window a0 = new Window("MTPC - Change Search Query");
                a0.getContentPane().setBackground(Color.WHITE);
                BasicButton a1 = new BasicButton("Search");
                BasicButton a2 = new BasicButton("Cancel");
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a1, a0);
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a2, a0);
                javax.swing.JList clauses = new javax.swing.JList();
                clauses.setBackground(Color.WHITE);
                clauses.setCellRenderer(new BasicListCellRenderer());
                String[] a4 = new String[cls.size()];
                int i = 0;
                while (i < cls.size()) {
                    a4[i] = cls.get(i).toNiceString();
                    i = i + 1;
                }
                clauses.setListData(a4);
                a1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        /*String s = (cls.isEmpty()) ? dbTable.grabAll() : dbTable.grabAllClauses(cls);
                        System.out.println(s);
                        values = dbTable.retrieveSelection(db.executeQuery(s));
                        Object a0 = values.iterator();
                        while (((java.util.Iterator) a0).hasNext()) {
                            ((java.util.ArrayList) ((java.util.Iterator) a0).next()).add((Object) "Options");
                        }*/
                        updateTable();
                        //parent.repaint();
                    }
                });
                BasicPanel a5 = new BasicPanel(new java.awt.FlowLayout(1));
                BasicButton a6 = new BasicButton("Delete");
                BasicButton a7 = new BasicButton("Add");
                a7.addActionListener(new ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        Window add_dialog = new Window("MTPC - Add Search Clause");
                        add_dialog.getContentPane().setLayout(new javax.swing.BoxLayout(add_dialog.getContentPane(), 3));
                        BasicRadioButton and = new BasicRadioButton("And");
                        BasicRadioButton a2 = new BasicRadioButton("Or");
                        BasicPanel a3 = new BasicPanel(new java.awt.FlowLayout(1));
                        a3.add(and);
                        a3.add(a2);
                        javax.swing.ButtonGroup a4 = new javax.swing.ButtonGroup();
                        a4.add(and);
                        a4.add(a2);
                        a4.setSelected(and.getModel(), true);
                        BasicCheckButton invert = new BasicCheckButton("Negate");
                        invert.setSelected(false);
                        BasicComboBox jc = new BasicComboBox(dbTable.columns);
                        BasicComboBox op = new BasicComboBox(appguru.db.Clause.NUMERICAL_OPS);
                        BasicEntry v = new BasicEntry("");
                        add_dialog.getContentPane().add(invert);
                        add_dialog.getContentPane().add(a3);
                        add_dialog.getContentPane().add(jc);
                        add_dialog.getContentPane().add(op);
                        add_dialog.getContentPane().add(v);
                        jc.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent a) {
                                String[] a0 = null;
                                op.removeAllItems();
                                int i = dbTable.types[jc.getSelectedIndex()];
                                label2:
                                {
                                    label0:
                                    {
                                        label1:
                                        {
                                            if (i == 0) {
                                                break label1;
                                            }
                                            int i0 = dbTable.types[jc.getSelectedIndex()];
                                            if (i0 != 1) {
                                                break label0;
                                            }
                                        }
                                        a0 = appguru.db.Clause.NUMERICAL_OPS;
                                        break label2;
                                    }
                                    a0 = appguru.db.Clause.STRING_DSP;
                                }
                                int i1 = a0.length;
                                int i2 = 0;
                                while (i2 < i1) {
                                    String s = a0[i2];
                                    op.insertItemAt(s, op.getItemCount());
                                    i2 = i2 + 1;
                                }
                                op.setSelectedIndex(0);
                                add_dialog.getContentPane().repaint();
                            }
                        });
                        BasicPanel a9 = new BasicPanel(new java.awt.FlowLayout(1));
                        BasicButton a10 = new BasicButton("Add");
                        a10.addActionListener(new ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent a) {
                                if (v.getText().isEmpty()) {
                                    BasicOptionPane.showMessage("MTPC - Add clause - Error", "Error - Please fill in field", (byte) 0);
                                    return;
                                }
                                int i = dbTable.types[jc.getSelectedIndex()];
                                label3:
                                {
                                    label4:
                                    {
                                        boolean b = false;
                                        switch (i) {
                                            case 1: {
                                                try {
                                                    Integer.parseInt(v.getText());
                                                } catch (Exception ignoredException) {
                                                    break label4;
                                                }
                                                break;
                                            }
                                            case 0: {
                                                try {
                                                    Float.parseFloat(v.getText());
                                                } catch (Exception ignoredException0) {
                                                    break label3;
                                                }
                                                break;
                                            }
                                        }
                                        boolean b0 = clauses.getModel().getSize() == 0;
                                        int i0 = dbTable.types[jc.getSelectedIndex()];
                                        label2:
                                        {
                                            label0:
                                            {
                                                label1:
                                                {
                                                    if (i0 == 2) {
                                                        break label1;
                                                    }
                                                    int i1 = dbTable.types[jc.getSelectedIndex()];
                                                    if (i1 != 4) {
                                                        break label0;
                                                    }
                                                }
                                                b = true;
                                                break label2;
                                            }
                                            b = false;
                                        }
                                        appguru.db.Clause a0 = new appguru.db.Clause(b0, b, invert.isSelected(), !and.isSelected(), (String) jc.getSelectedItem(), (String) op.getSelectedItem(), v.getText());
                                        cls.add(a0);
                                        appguru.graphics.swing.SwingHelper.addListEntry(clauses, a0.toNiceString());
                                        return;
                                    }
                                    BasicOptionPane.showMessage("MTPC - Add clause - Error", "Error - Please format field properly", (byte) 0);

                                    //javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) new Window("MTPC - Minetest Package Control - Invalid value format !"), (Object) "Error - Not a valid integer number", "MTPC - Minetest Package Control - Invalid value format", 0, 0);
                                    return;
                                }
                                BasicOptionPane.showMessage("MTPC - Add clause - Error", "Error - Please format field properly", (byte) 0);

                                //javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) new Window("MTPC - Minetest Package Control - Invalid value format !"), (Object) "Error - Not a valid floating point number", "MTPC - Minetest Package Control - Invalid value format", 0, 0);
                            }
                        });
                        a9.add(a10);
                        BasicButton a11 = new BasicButton("Cancel");
                        appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a11, add_dialog);
                        a9.add(a11);
                        add_dialog.getContentPane().add(a9);
                        add_dialog.pack();
                        add_dialog.setVisible(true);
                    }
                });
                a6.addActionListener(new ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        if (clauses.getModel().getSize() <= 1) {
                            if (clauses.getModel().getSize() == 1) {
                                clauses.setListData(new String[0]);
                                int i = 0;
                                while (i < cls.size()) {
                                    cls.remove(i);
                                    i = i + 1;
                                }
                            }
                        } else {
                            cls.remove(clauses.getSelectedIndex());
                            if (clauses.getSelectedIndex() == 0) {
                                appguru.db.Clause a0 = cls.get(0);
                                a0.first = true;
                                cls.set(0, a0);
                            }
                            Object[] a1 = new Object[clauses.getModel().getSize() - 1];
                            int i0 = 0;
                            while (i0 < a1.length + 1) {
                                if (i0 >= clauses.getSelectedIndex()) {
                                    if (i0 > clauses.getSelectedIndex()) {
                                        a1[i0 - 1] = clauses.getModel().getElementAt(i0);
                                        if (i0 == 1) {
                                            a1[i0 - 1] = cls.get(0).toNiceString();
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
                a5.add(a7);
                a5.add(a6);
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
    }

    public void attachSorting(javax.swing.AbstractButton a) {
        a.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                Window a0 = new Window("Change Sorting Ruleset");
                BasicButton save = new BasicButton("Save");
                BasicButton cancel = new BasicButton("Cancel");
                SwingHelper.attachButtonCloseListener(save, a0);
                appguru.graphics.swing.SwingHelper.attachButtonCloseListener(save, a0);
                javax.swing.JTable table = new javax.swing.JTable(order_values, order_columns);
                table.setSelectionBackground(BasicColorScheme.CS.bg);

                save.addActionListener(new ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent a) {
                        String[] columns_values = new String[dbTable.columns.length];
                        int order_amount = 0;
                        int i = 0;
                        while (i < dbTable.columns.length) {
                            String s = (String) table.getModel().getValueAt(0, i);
                            columns_values[i] = s;
                            if (!s.equals("Ignore")) {
                                order_amount = order_amount + 1;
                            }
                            ++i;
                        }
                        order = new int[order_amount];
                        ascending = new boolean[dbTable.columns.length];
                        int i1 = 0;
                        int iter = 0;
                        order_values = new String[1][order_values[0].length];
                        for (int iw = 0; iw < order_values[0].length; iw++) {
                            order_values[0][iw] = "Ignore";
                        }
                        while (iter < dbTable.columns.length) {
                            String s0 = (String) table.getColumnModel().getColumn(iter).getHeaderValue();
                            order_columns[iter] = s0;
                            int i3 = 0;
                            while (!dbTable.columns[i3].equals(s0)) {
                                i3 = i3 + 1;
                            }
                            String s1 = columns_values[i3];
                            //System.out.println("Header : " + s0 + ", Value : " + s1);
                            if (!s1.equals("Ignore")) {
                                order_values[0][iter] = s1;
                                order[i1] = i3;
                                ascending[iter] = true;
                                if (s1.equals("Ascending")) {
                                    ascending[iter] = false;
                                }
                                i1 = i1 + 1;
                            }
                            iter = iter + 1;
                        }
                        if (i1 > 0) {
                            /*values = dbTable.retrieveSelection(dbTable.orderedQuery(db, dbTable.grabAll(), order, ascending));
                            Object a1 = values.iterator();
                            while (((java.util.Iterator) a1).hasNext()) {
                                ((java.util.ArrayList) ((java.util.Iterator) a1).next()).add((Object) "Options");
                            }*/
                            updateTable();
                            //parent.repaint();
                        }
                    }
                });
                BasicComboBox a3 = new BasicComboBox();
                a3.setRenderer(new BasicListCellRenderer());
                a3.addItem("Ascending");
                a3.addItem("Descending");
                a3.addItem("Ignore");
                int i = 0;
                int alltogether = 0;
                FontMetrics m = outline.createGraphics().getFontMetrics();
                while (i < dbTable.columns.length) {
                    table.getColumnModel().getColumn(i).setCellEditor(new javax.swing.DefaultCellEditor(a3));
                    int w = m.stringWidth(getColumnName(i));
                    alltogether += w + 20;
                    table.getColumn(getColumnName(i)).setMinWidth(w + 20);
                    i = i + 1;
                }
                table.setMinimumSize(new java.awt.Dimension(alltogether + 20, 37));
                javax.swing.JScrollPane tableparent = new javax.swing.JScrollPane(table);
                tableparent.setViewportView(table);
                tableparent.setPreferredSize(new java.awt.Dimension(alltogether + 20, 38));
                table.setFillsViewportHeight(true);
                BasicPanel a5 = new BasicPanel();
                a5.setLayout(new BoxLayout(a5, BoxLayout.PAGE_AXIS));
                a5.add(tableparent);
                BasicPanel sc = new BasicPanel(save, cancel);
                a5.add(sc);
                a0.getContentPane().add(a5);
                a0.pack();
                a0.setVisible(true);
            }
        });
    }

    public void setNotEditable(String... c) {
        for (String i : c) {
            editable[findColumn(i)] = true;
        }
    }

    public void setUnique(String... c) {
        for (String i : c) {
            if (findColumn(i) != -1) {
                unique[findColumn(i)] = true;
            }
        }
    }

    public void setEditor() {
        int i = 0;
        while (i < this.columns.length - 1) {
            this.parent.getColumn(this.columns[i]).setCellEditor(new NotifyCellEditor(new BasicEntry(""), this.getColumnClass(i)));
            i += 1;
        }
    }

    public boolean isCellEditable(int i, int i0) {
        return !editable[i0];
    }

    public void setValueAt(Object a, int i, int i0) {
        if (i >= this.values.size() || i0 >= this.columns.length) {
            return;
        }
        this.values.get(i).set(i0, a);
    }

    public String getColumnName(int i) {
        return this.columns[i];
    }

    public int getRowCount() {
        return this.values.size();
    }

    public int getColumnCount() {
        return this.columns.length;
    }

    public Object getValueAt(int i, int i0) {
        if (this.values == null) {
            return "";
        }
        if (i >= this.values.size() || i0 >= this.columns.length) {
            return "";
        }
        return this.values.get(i).get(i0);
    }

    @Override
    public Class getColumnClass(int i) {
        return this.getValueAt(0, i).getClass();
    }

}

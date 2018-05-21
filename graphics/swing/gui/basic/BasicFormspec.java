/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import appguru.graphics.swing.Window;
import appguru.info.Console;
import appguru.math.Rect;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author lars
 */
public class BasicFormspec {

    public HashMap<String, Object> variables;
    public JPanel panel;

    public Window showWindow(String title) {
        Window frame = new Window(title);
        frame.getContentPane().add(this.panel);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public static ArrayList<String> split(String s, final char delimiter) {
        ArrayList<String> r = new ArrayList();
        boolean literals = false;
        int last_n = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"') {
                literals = !literals;
            } else if (c == delimiter) {
                if (!literals) {
                    r.add(s.substring(last_n, i));
                    last_n = i + 1;
                }
            }
        }
        if (last_n != s.length()) {
            r.add(s.substring(last_n, s.length()));
        }
        return r;
    }

    public static ArrayList<String> split(String s, String delimiter) {
        ArrayList<String> r = new ArrayList();
        boolean literals = false;
        int di = 0;
        int last_n = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"') {
                literals = !literals;
            } else if (c == delimiter.charAt(di)) {
                if (!literals) {
                    di++;
                    if (di == delimiter.length()) {
                        di = 0;
                        r.add(s.substring(last_n, i - di));
                        last_n = i + 1;
                    }
                }
            }
        }
        if (last_n != s.length()) {
            r.add(s.substring(last_n, s.length()));
        }
        return r;
    }

    public BasicFormspec(String formspec) {
        super();
        variables = new HashMap();
        ArrayList<String> lines = split(formspec, '\n');
        Object[] os = split(lines.get(0).toUpperCase(), ' ').toArray();
        String[] properties = new String[os.length];
        for (int u = 0; u < os.length; u++) {
            properties[u] = os[u].toString();
        }
        Rect[] rects = null;
        int[] align = new int[lines.size() - 1];
        int o = 1;
        if (properties[0].equals("ABSOLUTE")) {
            o = 3;
            rects = new Rect[align.length];
            panel = new BasicRelativePanel(new Dimension(Integer.parseInt(properties[1]), Integer.parseInt(properties[2])));
            for (int i = 0; i < rects.length; i++) {
                String[] salad = properties[i + o].split(",");
                rects[i] = new Rect(Float.parseFloat(salad[1]), Float.parseFloat(salad[2]), Float.parseFloat(salad[3]), Float.parseFloat(salad[4]));
                if (salad[0].equals("WRAPPED")) {
                    align[i] = -1;
                } else if (salad[0].equals("UNWRAPPED")) {
                    align[i] = -2;
                } else if (salad[0].equals("RIGHT")) {
                    align[i] = FlowLayout.RIGHT;
                } else if (salad[0].equals("CENTER")) {
                    align[i] = FlowLayout.CENTER;
                } else if (salad[0].equals("LEFT")) {
                    align[i] = FlowLayout.LEFT;
                }
            }
        } else {
            panel = new BasicPanel();
            if (properties[0].equals("GRID")) {
                panel.setLayout(new GridLayout(Integer.parseInt(properties[1]), Integer.parseInt(properties[2])));
                o = 3;
            } else if (properties[0].equals("VERTICAL")) {
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            } else if (properties[0].equals("HORIZONTAL")) {
                panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
            }
            for (int i = 0; i < align.length; i++) {
                if (properties[i + o].equals("WRAPPED")) {
                    align[i] = -1;
                } else if (properties[i + o].equals("UNWRAPPED")) {
                    align[i] = -2;
                } else if (properties[i + o].equals("RIGHT")) {
                    align[i] = FlowLayout.RIGHT;
                } else if (properties[i + o].equals("CENTER")) {
                    align[i] = FlowLayout.CENTER;
                } else if (properties[i + o].equals("LEFT")) {
                    align[i] = FlowLayout.LEFT;
                }
            }
        }
        for (int j = 1; j < lines.size(); j++) {
            ArrayList<String> lp = split(lines.get(j), ")|");
            BasicPanel linepanel = new BasicPanel();
            if (align[j - 1] >= 0) {
                linepanel = new BasicPanel(new FlowLayout(align[j - 1]));
            }
            for (String s : lp) {
                ArrayList<String> declar = split(s, '=');
                ArrayList<String> parts = split(declar.get(1), '(');
                Class<?> c;
                try {
                    //System.out.println("appguru.graphics.swing.gui.basic.Basic" + parts.get(0));
                    c = Class.forName("appguru.graphics.swing.gui.basic.Basic" + parts.get(0));
                } catch (ClassNotFoundException ex) {
                    Console.errorMessage(BasicFormspec.class.getName(), "INVALID GUI ELEMENT : APPGURU BASIC GUI : " + parts.get(0));
                    try {
                        //System.out.println("appguru.graphics.swing.gui.basic.Basic" + parts.get(0));
                        c = Class.forName("javax.swing.J" + parts.get(0));
                    } catch (ClassNotFoundException ex2) {
                        Console.errorMessage(BasicFormspec.class.getName(), "INVALID GUI ELEMENT : JAVA SWING GUI");
                        return;
                    }
                }
                ArrayList<String> args = parts.get(1).length() < 2 ? new ArrayList() : split(parts.get(1).substring(0, parts.get(1).length()-1), ' ');
                for (int i = 0; i < args.size(); i++) {
                    args.set(i, args.get(i).substring(1, args.get(i).length() - 1).replace("'","\""));
                }
                Constructor<?>[] cs = c.getConstructors();
                for (Constructor cons : cs) {
                    if (cons.getParameterCount() == args.size()) {
                        Object[] insert = new Object[cons.getParameterCount()];
                        Class[] args2 = cons.getParameterTypes();
                        for (int i = 0; i < args2.length; i++) {
                            try {
                                label0:
                                {
                                    if (args2[i].isPrimitive()) {
                                        if (args2[i] == char.class) {
                                            insert[i] = new Character(args.get(i).charAt(0));
                                            break label0;
                                        }
                                        if (args2[i] == int.class) {
                                            insert[i] = new Integer(args.get(i));
                                            break label0;
                                        } else {
                                            String name = args2[i].getName();
                                            try {
                                                Class<?> c2 = Class.forName("java.lang." + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()));
                                                insert[i] = c2.getConstructor(String.class).newInstance(args.get(i));
                                            } catch (ClassNotFoundException ex) {
                                                Console.errorMessage(BasicFormspec.class.getName(), "FAILING IS HARD");
                                            }
                                            break label0;
                                        }
                                    }
                                    if (args2[i].isArray()) {
                                        Class<?> ct = args2[i].getComponentType();
                                        String[] splutted = args.get(i).split(";");
                                        insert[i] = Array.newInstance(ct, splutted.length);
                                        int i2 = 0;
                                        for (String splut : splutted) {
                                            if (args2[i].isPrimitive()) {
                                                if (ct == char.class) {
                                                    ((Object[]) insert[i])[i2] = new Character(splut.charAt(0));
                                                } else if (ct == int.class) {
                                                    ((Object[]) insert[i])[i2] = new Integer(splut);
                                                } else {
                                                    String name = args2[i].getName();
                                                    try {
                                                        Class<?> c2 = Class.forName("java.lang." + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()));
                                                        ((Object[]) insert[i])[i2] = c2.getConstructor(String.class).newInstance(splut);
                                                    } catch (ClassNotFoundException ex) {
                                                        Console.errorMessage(BasicFormspec.class.getName(), "FAILING IS HARD");
                                                    }
                                                }
                                                continue;
                                            }
                                            ((Object[]) insert[i])[i2] = ct.getConstructor(String.class).newInstance(splut);
                                            i2++;
                                        }
                                        break label0;
                                    }
                                    try {
                                        insert[i] = args2[i].getConstructor(String.class).newInstance(args.get(i));
                                    } catch (Exception e) {
                                        System.out.println(args.get(i));
                                    }
                                }
                            } catch (NoSuchMethodException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            } catch (SecurityException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            } catch (InstantiationException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            } catch (IllegalAccessException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            } catch (IllegalArgumentException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            } catch (InvocationTargetException ex) {
                                Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            }
                        }
                        try {
                            Object ni;
                            Object ex=cons.newInstance(insert);
                            if (c == BasicFormspec.class) {
                                ni=((BasicFormspec)ex).panel;
                            }
                            else {
                                ni=ex;
                            }
                            if (rects == null) {
                                variables.put(declar.get(0), (align[j - 1] == -2 ? panel : linepanel).add((Component) ni));
                            } else {
                                variables.put(declar.get(0), (align[j - 1] == -2 ? ((BasicRelativePanel) panel).add((Component) ni, rects[j - 1]) : linepanel.add((Component) ni)));
                            }
                            if (c == BasicFormspec.class) {
                                System.out.println(declar.get(0));
                                variables.put(declar.get(0),ex);
                            }
                        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Console.errorMessage(BasicFormspec.class.getName(), "FAIL");
                            return;
                        }
                    }
                }
            }
            if (align[j - 1] >= -1 && rects == null) {
                panel.add(linepanel);
            } else if (rects != null) {
                ((BasicRelativePanel) panel).add(linepanel, rects[j - 1]);
            }
        }
    }
}

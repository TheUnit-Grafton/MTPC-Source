package appguru.graphics.swing;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

class CloseWindowListener implements java.awt.event.ActionListener {

    public Window window;

    public CloseWindowListener(Window a0) {
        this.window = a0;
    }

    public void actionPerformed(java.awt.event.ActionEvent a) {
        this.window.dispatchEvent((java.awt.AWTEvent) new java.awt.event.WindowEvent((java.awt.Window) this.window, 201));
        if (window.mainframe) {
        System.exit(0);
        }
    }
}

class MinimizeWindowListener implements java.awt.event.ActionListener {

    public javax.swing.JFrame window;

    public MinimizeWindowListener(javax.swing.JFrame a0) {
        this.window = a0;
    }

    public void actionPerformed(java.awt.event.ActionEvent a) {
        this.window.setState(Frame.ICONIFIED);
    }
}

class MaximizeWindowListener implements java.awt.event.ActionListener {

    public Window window;

    public MaximizeWindowListener(Window a0) {
        this.window = a0;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent a) {
        Dimension d;
        if (window.previous == null) {
            window.previous = window.getSize();
            d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            d = new Dimension(window.previous.width, window.previous.height);
            window.previous = null;
        }
        this.window.setSize(d);
    }
}

public class SwingHelper {

    public static appguru.graphics.swing.SwingHelper statichelper;

    public SwingHelper() {
        super();
    }

    public static void addListEntry(javax.swing.JList a, String s) {
        Object[] a0 = new Object[a.getModel().getSize() + 1];
        int i = 0;
        while (i < a0.length - 1) {
            a0[i] = a.getModel().getElementAt(i);
            i = i + 1;
        }
        a0[a0.length - 1] = s;
        a.setListData(a0);
    }

    public static void attachButtonStayPressedListener(javax.swing.AbstractButton a) {
        a.addActionListener((java.awt.event.ActionListener) (Object) new appguru.graphics.swing.SwingHelper$1());
    }

    public void attachButtonCloseWindowListener(javax.swing.AbstractButton a, Window a0) {
        a.addActionListener((java.awt.event.ActionListener) (Object) new CloseWindowListener(a0));
    }

    public void attachButtonMinimizeWindowListener(javax.swing.AbstractButton a, javax.swing.JFrame a0) {
        a.addActionListener((java.awt.event.ActionListener) (Object) new MinimizeWindowListener(a0));
    }

    public void attachButtonMaximizeWindowListener(javax.swing.AbstractButton a, Window a0) {
        a.addActionListener((java.awt.event.ActionListener) (Object) new MaximizeWindowListener(a0));
    }

    public static void attachButtonCloseListener(Window a, javax.swing.AbstractButton... a0) {
        int i = a0.length;
        int i0 = 0;
        while (i0 < i) {
            appguru.graphics.swing.SwingHelper.attachButtonCloseListener(a0[i0], a);
            i0 = i0 + 1;
        }
    }

    public static void attachButtonCloseListener(javax.swing.AbstractButton a, Window a0) {
        statichelper.attachButtonCloseWindowListener(a, a0);
    }

    public static void attachButtonMinimizeListener(javax.swing.AbstractButton a, javax.swing.JFrame a0) {
        statichelper.attachButtonMinimizeWindowListener(a, a0);
    }

    public static void attachButtonMaximizeListener(javax.swing.AbstractButton a, Window a0) {
        statichelper.attachButtonMaximizeWindowListener(a, a0);
    }

    static {
        statichelper = new appguru.graphics.swing.SwingHelper();
    }
}

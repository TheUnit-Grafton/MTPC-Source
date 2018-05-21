package appguru.graphics.swing.gui.basic;

import java.awt.event.MouseListener;

public class BasicLink extends javax.swing.JLabel {

    public static java.util.HashMap UNDERLINE;
    public static java.util.HashMap NO_UNDERLINE;
    public java.net.URL link;

    public BasicLink(String s, java.net.URL a) {
        super(s);
        this.link = a;
        ((javax.swing.JLabel) this).setForeground(java.awt.Color.BLUE);
        ((javax.swing.JLabel) this).setCursor(java.awt.Cursor.getPredefinedCursor(12));
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(java.awt.event.MouseEvent a) {
                boolean b = java.awt.Desktop.isDesktopSupported();
                label0:
                {
                    label1:
                    {
                        label2:
                        if (b) {
                            try {
                                try {
                                    java.awt.Desktop.getDesktop().browse(link.toURI());
                                } catch (java.io.IOException ignoredException) {
                                    break label2;
                                }
                            } catch (java.net.URISyntaxException ignoredException0) {
                                break label1;
                            }
                            break label0;
                        } else {
                            appguru.info.Console.warningMessage(BasicLink.class.getName(), "DESKTOP NOT SUPPORTED - CANT OPEN WEBBROWSER");
                            javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Your desktop isn't supported.", "MTPC - Minetest Package Control - Warning", 2);
                            break label0;
                        }
                        javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Invalid url.", "MTPC - Minetest Package Control - Warning", 2);
                        break label0;
                    }
                    javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Invalid url.", "MTPC - Minetest Package Control - Warning", 2);
                }
            }

            public void mousePressed(java.awt.event.MouseEvent a) {
            }

            public void mouseReleased(java.awt.event.MouseEvent a) {
                boolean b = java.awt.Desktop.isDesktopSupported();
                label0:
                {
                    label1:
                    {
                        label2:
                        if (b) {
                            try {
                                try {
                                    java.awt.Desktop.getDesktop().browse(link.toURI());
                                } catch (java.io.IOException ignoredException) {
                                    break label2;
                                }
                            } catch (java.net.URISyntaxException ignoredException0) {
                                break label1;
                            }
                            break label0;
                        } else {
                            appguru.info.Console.warningMessage(((Object) this).getClass().getName(), "DESKTOP NOT SUPPORTED - CANT OPEN WEBBROWSER");
                            javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Your desktop isn't supported.", "MTPC - Minetest Package Control - Warning", 2);
                            break label0;
                        }
                        javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Invalid url.", "MTPC - Minetest Package Control - Warning", 2);
                        break label0;
                    }
                    javax.swing.JOptionPane.showMessageDialog((java.awt.Component) null, (Object) "Couldn't open webbrowser : Invalid url.", "MTPC - Minetest Package Control - Warning", 2);
                }
            }

            public void mouseEntered(java.awt.event.MouseEvent a) {
                javax.swing.JLabel a0 = (javax.swing.JLabel) a.getSource();
                a0.setFont(a0.getFont().deriveFont((java.util.Map) (Object) appguru.graphics.swing.BasicLink.UNDERLINE));
                a0.setCursor(java.awt.Cursor.getPredefinedCursor(12));
            }

            public void mouseExited(java.awt.event.MouseEvent a) {
                javax.swing.JLabel a0 = (javax.swing.JLabel) a.getSource();
                a0.setFont(a0.getFont().deriveFont((java.util.Map) (Object) appguru.graphics.swing.BasicLink.NO_UNDERLINE));
            }
        });
    }

    static {
        UNDERLINE = new java.util.HashMap();
        NO_UNDERLINE = new java.util.HashMap();
        UNDERLINE.put((Object) java.awt.font.TextAttribute.UNDERLINE, (Object) java.awt.font.TextAttribute.UNDERLINE_LOW_DOTTED);
        NO_UNDERLINE.put((Object) java.awt.font.TextAttribute.UNDERLINE, (Object) Integer.valueOf(-1));
    }
}

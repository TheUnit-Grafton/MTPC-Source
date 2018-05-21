/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import appguru.graphics.swing.SwingHelper;
import appguru.graphics.swing.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author lars
 */
public class BasicOptionPane {

    public static final Color[] CSHEME = {new Color(225, 0, 0), new Color(225, 225, 0), new Color(0, 0, 225), new Color(125, 125, 225), new Color(0, 225, 0)};

    public static void showMessage(String title, String message, byte message_type) {
        Window frame = new Window(title, CSHEME[message_type], Color.WHITE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        String[] splut = message.split("\n");
        for (String s : splut) {
            JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(new JLabel(s));
            frame.getContentPane().add(p);
        }
        JPanel z=new JPanel(new FlowLayout(FlowLayout.CENTER));
        BasicButton o = new BasicButton("Ok");
        z.add(o);
        frame.getContentPane().add(z);
        SwingHelper.attachButtonCloseListener(frame, o);
        frame.pack();
        frame.revalidate();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        d.width -= frame.getWidth();
        d.height -= frame.getHeight();
        frame.setLocation(d.width / 2, d.height / 2);
        frame.setVisible(true);
    }
}

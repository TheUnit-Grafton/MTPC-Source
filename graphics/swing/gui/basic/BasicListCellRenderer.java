/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.geom.RoundRectangle2D;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author lars
 */
public class BasicListCellRenderer implements ListCellRenderer {

    public BasicListCellRenderer() {
    }

    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        list.setSelectionBackground(BasicColorScheme.CS.bgon);
        BasicPanel SWAG = new BasicPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(value.toString());
        if (isSelected) {
            label.setBackground(BasicColorScheme.CS.bg);
            SWAG.setBackground(BasicColorScheme.CS.bg);
        } else {
            label.setBackground(BasicColorScheme.CS.alt_bg);
            SWAG.setBackground(BasicColorScheme.CS.alt_bg);
        }
        if (cellHasFocus) {
            label.setBackground(BasicColorScheme.CS.bgon);
            SWAG.setBackground(BasicColorScheme.CS.bgon);
        }
        SWAG.add(label);
        if (SWAG.getGraphics() != null) {
            System.out.println("Invocation");
            SWAG.getGraphics().setClip(new RoundRectangle2D.Float(0, 0, SWAG.getWidth(), SWAG.getHeight(), 8, 8));
        }
        return SWAG;
    }
}

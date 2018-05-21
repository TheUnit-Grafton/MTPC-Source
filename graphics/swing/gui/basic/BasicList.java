/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import javax.swing.JList;

/**
 *
 * @author lars
 */
public class BasicList extends JList {
    public static BasicColorScheme CS;
    public BasicList() {
        super();
        super.setForeground(BasicColorScheme.CS.fg);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        super.setCellRenderer(new BasicListCellRenderer());
    }
    public BasicList(String[] s) {
        super(s);
        super.setForeground(BasicColorScheme.CS.fg);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        super.setCellRenderer(new BasicListCellRenderer());
    }
}

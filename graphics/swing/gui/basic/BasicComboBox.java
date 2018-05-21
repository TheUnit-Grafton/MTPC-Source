/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.metal.MetalComboBoxButton;

/**
 *
 * @author lars
 */
public class BasicComboBox extends JComboBox {
    public static BasicColorScheme CS;
    class BasicCellRendererPane extends CellRendererPane {
        public BasicCellRendererPane() {
            super();
        }
        public void paintComponent(Graphics g) {
            
        }
    }
    class BasicComboBoxButton extends MetalComboBoxButton {
        
        public BasicComboBoxButton(JComboBox arg0, Icon arg1, CellRendererPane arg2, JList arg3) {
            super(arg0, arg1, arg2, arg3);
            super.setBackground(BasicColorScheme.CS.bg);
            super.setForeground(BasicColorScheme.CS.bg);
            super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        }
        
        /*@Override
        public void paintComponent(Graphics g) {
            g.setClip(new RoundRectangle2D.Float(0,0,super.getWidth(),super.getHeight(),8,8));
            super.paintComponent(g);
            g.setClip(null);
            super.paintBorder(g);
        }*/
    }
    public BasicComboBox() {
        super();
        super.setForeground(BasicColorScheme.CS.fg);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        //System.super.getComponents()
        super.remove(0);
        Component comp=super.getComponent(0);
        super.remove(0);
        //super.add(new BasicComboBoxButton(previous.getComboBox(),previous.getComboIcon(),new CellRendererPane(), new JList()));
        super.add(comp);
        super.removeAll();
        //.(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        super.setRenderer(new BasicListCellRenderer());
    }
    public BasicComboBox(String[] s) {
        super(s);
        super.setForeground(BasicColorScheme.CS.fg);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        super.setSelectedIndex(0);
        //super.setPreferredSize(new Dimension(20,super.getWidth()));
        /*MetalComboBoxButton comp=(MetalComboBoxButton)super.getComponent(0);
        //super.remove(0);
        comp.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,0));
        comp.setBackground(BasicColorScheme.CS.bg);
        comp.setForeground(BasicColorScheme.CS.fg);
        CellRendererPane b=(CellRendererPane)super.getComponent(1);
        b.setBackground(BasicColorScheme.CS.alt_bg);
        System.out.println(b);
        super.remove(0);
        super.remove(0);
        JList list=new JList(s);
        list.setCellRenderer(new BasicListCellRenderer());
        super.add((MetalComboBoxButton)new BasicComboBoxButton(this,comp.getComboIcon(),b, list));
        super.add(b);*/
        //super.removeAll();
        //super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5));
        /*CellRendererPane b=(CellRendererPane)super.getComponent(1);
        b.setBackground(BasicColorScheme.CS.alt_bg);*/
        //super.add(new JLabel("All okay?"));
        super.setBorder(new BasicRoundBorder(BasicColorScheme.CS.b,5,true));
        CellRendererPane b=(CellRendererPane)super.getComponent(1);
        b.setBackground(BasicColorScheme.CS.alt_bg);
        super.setRenderer(new BasicListCellRenderer());
        this.setUI(new BasicComboBoxUI(this));
    }
}

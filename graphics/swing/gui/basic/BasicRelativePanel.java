/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;
import appguru.info.Console;
import appguru.math.Rect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
import javax.swing.JPanel;
/**
 *
 * @author lars
 */
public class BasicRelativePanel extends JPanel{
    public Vector<Rect> components_pos;
    
    @Override
    public Component add(Component c) {
        Console.warningMessage(this.getClass().getName(),"ADDING COMPONENT WITHOUT POSITION");
        return super.add(c);
    }
    
    public Component add(Component c, Rect r) {
        components_pos.add(r);
        return super.add(c);
    }
    
    @Override
    public void remove(int index) {
        components_pos.remove(index);
        super.remove(index);
    }
    
    public BasicRelativePanel(Dimension d) {
        super();
        super.setBackground(Color.WHITE);
        components_pos=new Vector();
        setLayout(null);
        super.setPreferredSize(d);
        addCL(this);
    }
    public BasicRelativePanel(FlowLayout fl) {
        super(fl);
        super.setBackground(Color.WHITE);
        components_pos=new Vector();
    }
    
    public static void addCL(BasicRelativePanel p) {
        p.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (int i=0; i < p.components_pos.size(); i++) {
                    Component c=p.getComponent(i);
                    Rect r=p.components_pos.get(i);
                    Dimension bounds=((BasicRelativePanel)e.getSource()).getSize();
                    System.out.println(bounds);
                    c.setBounds((int)(r.x*bounds.width), (int)(r.y*bounds.height),(int)((r.w*bounds.width)-(r.x*bounds.width)),(int)((r.h*bounds.height)-(r.y*bounds.height)));
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }
    
    public BasicRelativePanel(Component... comps) {
        super(new FlowLayout(FlowLayout.CENTER));
        super.setBackground(Color.WHITE);
        for (Component comp:comps) {
            add(comp);
        }
        components_pos=new Vector();
    }
    public void add(Component... comps) {
        for (Component comp:comps) {
            this.add(comp);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 *
 * @author lars
 */
public class BasicComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {

    public BasicComboBox parent;

    public BasicComboBoxUI(BasicComboBox combo) {
        super();
        super.squareButton=false;
        parent = combo;
    }

    public void setPopupVisible(JComboBox b, boolean truew) {
        if (truew) {
            BasicButton arrow = (BasicButton) parent.getComponent(0);
            if (arrow.getText().equals("▴")) {
                arrow.setText("▾");
            } else {
                arrow.setText("▴");
            }
        }
        super.setPopupVisible(b, truew);
    }

    @Override
    protected JButton createArrowButton() {
        BasicButton arrow2 = new BasicButton("▾");
        //arrow2.setMaximumSize(new Dimension(20,100));
        //arrow2.setPreferredSize(new Dimension(20,100));
        /*b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b.getText().equals("▴")) {
                    b.setText("▾");
                } else {
                    b.setText("▴");
                }
            }
        });*/
        return arrow2;
        /*return new BasicArrowButton(
            BasicArrowButton.SOUTH,
            Color.cyan, Color.magenta,
            Color.yellow, Color.blue);*/
    }

}

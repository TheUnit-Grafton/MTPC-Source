/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import static appguru.graphics.swing.gui.basic.BasicEntry.ROUNDNESS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.Border;

/**
 *
 * @author lars
 */
public class BasicRoundBorder implements Border {

        public int r;
        public Color color;
        public boolean inset;

        public BasicRoundBorder(Color c, int radius) {
            this.color = c;
            this.r = radius;
        }
        public BasicRoundBorder(Color c, int radius, boolean inset) {
            this.color = c;
            this.r = radius;
            this.inset=inset;
        }

        @Override
        public void paintBorder(Component c, Graphics gw, int x, int y, int width, int height) {
            Graphics2D g = (Graphics2D) gw;
            g.setColor(color);
            g.setRenderingHints(BasicRadioButton.AA);
            g.drawRoundRect(x, y, width - 1, height - 1, r, r);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            if (inset) {
                return new Insets(0,0,0,0);
            }
            return new Insets(r, r, r, r);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

    }

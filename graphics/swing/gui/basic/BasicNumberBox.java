/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.graphics.swing.gui.basic;

import appguru.AGE;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BoxLayout;

/**
 *
 * @author lars
 */
public class BasicNumberBox extends BasicPanel {

    public static final DecimalFormat FORMAT = (DecimalFormat)NumberFormat.getInstance(Locale.ENGLISH);
    public double wert;
    public BasicEntry content;

    class PlusButton extends BasicButton {

        public long trigger_time;
        public Thread t;

        class ML implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    double d = Double.parseDouble(content.getText());

                    if (d >= min && d <= max) {
                        setWert(d);
                    }
                } catch (NumberFormatException r) {

                }
                double tmp = wert + step;
                if (tmp <= max) {
                    setWert(tmp);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    double d = Double.parseDouble(content.getText());

                    if (d >= min && d <= max) {
                        setWert(d);
                    }
                } catch (NumberFormatException r) {

                }
                trigger_time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - trigger_time > 200) {
                        break;
                    }
                }
                t = new Thread() {
                    @Override
                    public synchronized void run() {
                        long last_invoc = System.currentTimeMillis();
                        while (true) {
                            if (System.currentTimeMillis() - last_invoc >= 100 - Math.sqrt(System.currentTimeMillis() - trigger_time)) {
                                double tmp = wert + step;
                                if (tmp <= max) {
                                    setWert(tmp);
                                }
                                last_invoc = System.currentTimeMillis();
                            }
                        }
                    }
                };
                t.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                t.stop();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }

        public PlusButton(String s) {
            super(s);
            addMouseListener(new ML());
        }

    }

    class MinusButton extends BasicButton {

        public long trigger_time;
        public Thread t;

        class ML implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    double d = Double.parseDouble(content.getText());

                    if (d >= min && d <= max) {
                        setWert(d);
                    }
                } catch (NumberFormatException r) {

                }
                double tmp = wert - step;
                if (tmp >= min) {
                    setWert(tmp);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    double d = Double.parseDouble(content.getText());

                    if (d >= min && d <= max) {
                        setWert(d);
                    }
                } catch (NumberFormatException r) {

                }
                trigger_time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - trigger_time > 200) {
                        break;
                    }
                }
                t = new Thread() {
                    @Override
                    public synchronized void run() {
                        long last_invoc = System.currentTimeMillis();
                        while (true) {
                            if (System.currentTimeMillis() - last_invoc >= 100 - Math.sqrt(System.currentTimeMillis() - trigger_time)) {
                                double tmp = wert - step;
                                if (tmp >= min) {
                                    setWert(tmp);
                                }
                                last_invoc = System.currentTimeMillis();
                            }
                        }
                    }
                };
                t.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                t.stop();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }

        public MinusButton(String s) {
            super(s);
            addMouseListener(new ML());

        }

    }

    public double getWert() {
        return wert;
    }

    public void setWert(double wert) {
        this.wert = wert;
        BasicEntry be = (BasicEntry) getComponent(1);
        be.setText(FORMAT.format(wert));
    }

    public double max;
    public double min;
    public double step;

    public BasicNumberBox(double value, double max, double min, double step) {
        super();
        super.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.wert = value;
        this.max = max;
        this.min = min;
        this.step = step;
        content = new BasicEntry(FORMAT.format(max));
        content.setPreferredSize(new Dimension(10 + AGE.GRAPHICS.getFontMetrics().stringWidth(FORMAT.format(max)), content.getHeight() - 1));
        content.setText(FORMAT.format(value));
        content.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                double d = Double.parseDouble(content.getText());
                if (d >= min && d <= max) {
                    setWert(d);
                }
                }catch(NumberFormatException ee) {}
            }
        });
        PlusButton plus = new PlusButton("+");
        MinusButton minus = new MinusButton("-");
        //attachPlusListener(plus, this);
        this.add(minus, content, plus);
    }

}

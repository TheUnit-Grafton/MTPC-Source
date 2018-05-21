package appguru.graphics.swing;

import appguru.AGE;
import appguru.graphics.swing.gui.basic.BasicButton;
import appguru.graphics.swing.gui.basic.BasicColorScheme;
import appguru.graphics.swing.gui.basic.BasicPanel;
import appguru.timer.TimeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

class WindowButton extends JButton {

        public Color n;
        public Color on;
        public Color p;

        public WindowButton(Color c, int r) {
            super();
            n=c;
            on=new Color(Math.min(255,c.getRed()+30),Math.min(255,c.getGreen()+30),Math.min(255,c.getBlue()+30));
            p=new Color(Math.max(0,c.getRed()-60),Math.max(0,c.getGreen()-60),Math.max(0,c.getBlue()-60));
            super.setPreferredSize(new Dimension(r,r));
            super.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2));
            super.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void paintComponent(Graphics gw) {
            Graphics2D g = (Graphics2D) gw;
            g.setRenderingHints(BasicButton.AA);
            Color c;
            g.setPaintMode();
            g.setBackground(this.getParent().getBackground());
            g.clearRect(0, 0, getWidth(), getHeight());
            if (super.getModel().isPressed()) {
                c=p;
            } else if (super.getModel().isRollover()) {
                c=on;

            } else {
                c=n;
            }
            g.setColor(c);
            g.fillOval(0, 0, this.getWidth()-1, this.getHeight()-1);
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, this.getWidth()-1, this.getHeight()-1);
            //super.paintComponent(g);
        }
    }

public class Window extends JFrame {
    public JPanel window_bar;
    public JLabel window_title;
    public String title;
    public Dimension previous;
    public Point mouse;
    public boolean mainframe=false;
    //public Point loc;
    
    @Override
    public void setTitle(String s) {
        window_title.setText(s);
        window_title.repaint();
    }
    
    public Window(String s) {
        super(s);
        Container p=super.getContentPane();
        p.setBackground(BasicColorScheme.CS.alt_bg);
        super.setContentPane(p);
        super.setUndecorated(true);
        super.setBackground(BasicColorScheme.CS.alt_bg);
        this.title=s;
        /*AGE.global_timer.addEvent(40, new TimeListener() {
            @Override
            public void invoke(long arg) {
                if (loc != null) {
                setLocation(loc);
                loc=null;
                }
            }
        });*/
        window_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        WindowButton closebutton=new WindowButton(new Color(225,0,0),17);
        SwingHelper.attachButtonCloseListener(closebutton, this);
        window_bar.add(closebutton);
        
        WindowButton minbutton=new WindowButton(new Color(225,225,0),17);
        SwingHelper.attachButtonMinimizeListener(minbutton, this);
        window_bar.add(minbutton);
        
        WindowButton maxbutton=new WindowButton(new Color(0,225,0),17);
        SwingHelper.attachButtonMaximizeListener(maxbutton, this);
        window_bar.add(maxbutton);
        window_title=new JLabel(s);
        window_bar.add(window_title);
        //window_bar.add(new WindowButton(new Color(200,0,0),22));
        
        window_bar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouse=e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        window_bar.setBackground(BasicColorScheme.CS.bg);
        window_bar.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y + height - 1, x + width, y + height - 1);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 1, 0);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });
        window_bar.setPreferredSize(new Dimension(this.getWidth(), 26));
        super.getContentPane().add(window_bar, BorderLayout.NORTH);
        addML(this);
        addCL(this);
        /*this.window.setSize(i, i0);
        this.window.setDefaultCloseOperation(3);
        this.window.setResizable(true);
        this.window.setVisible(true);*/
    }
    public Window(String s, Color bar_color, Color bg_color) {
        super(s);
        super.setContentPane(new BasicPanel(true));
        super.setUndecorated(true);
        super.setBackground(bg_color);
        this.title=s;
        super.getContentPane().setBackground(bg_color);
        /*AGE.global_timer.addEvent(40, new TimeListener() {
            @Override
            public void invoke(long arg) {
                if (loc != null) {
                setLocation(loc);
                loc=null;
                }
            }
        });*/
        window_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        WindowButton closebutton=new WindowButton(new Color(225,0,0),17);
        SwingHelper.attachButtonCloseListener(closebutton, this);
        window_bar.add(closebutton);
        
        WindowButton minbutton=new WindowButton(new Color(225,225,0),17);
        SwingHelper.attachButtonMinimizeListener(minbutton, this);
        window_bar.add(minbutton);
        
        WindowButton maxbutton=new WindowButton(new Color(0,225,0),17);
        SwingHelper.attachButtonMaximizeListener(maxbutton, this);
        window_bar.add(maxbutton);
        window_title=new JLabel(s);
        window_bar.add(window_title);
        //window_bar.add(new WindowButton(new Color(200,0,0),22));
        
        window_bar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouse=e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        window_bar.setBackground(bar_color);
        window_bar.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y + height - 1, x + width, y + height - 1);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 1, 0);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });
        window_bar.setPreferredSize(new Dimension(this.getWidth(), 26));
        super.getContentPane().add(window_bar, BorderLayout.NORTH);
        addML(this);
        addCL(this);
        /*this.window.setSize(i, i0);
        this.window.setDefaultCloseOperation(3);
        this.window.setResizable(true);
        this.window.setVisible(true);*/
    }
    
    public static void addML(Window w) {
        w.window_bar.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point l=w.getLocation();
                int dx=e.getX()-w.mouse.x+l.x;
                int dy=e.getY()-w.mouse.y+l.y;
                w.setLocation(dx,dy);
                //w.loc=new Point(dx,dy);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }
    
    public static void addCL(Window w) {
        w.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension d=e.getComponent().getSize();
                w.window_bar.setSize(new Dimension(d.width, 26));
                w.window_bar.setPreferredSize(new Dimension(d.width, 26));
                w.setShape(new RoundRectangle2D.Float(0,0,d.width,d.height,8,8));
                int lw=d.width-110;
                int xp=0;
                if (w.getGraphics() == null) {
                    return;
                }
                FontMetrics m=w.getGraphics().getFontMetrics();
                String lt=w.title;
                for (int x=0; x < w.title.length(); x++) {
                    char c=w.title.charAt(x);
                    xp+=m.charWidth(c);
                    if (xp > lw) {
                        if (x==0) {
                            lt="...";
                            break;
                        }
                        lt=w.title.substring(0,x-1)+"...";
                        break;
                    }
                }
                //JLabel label=(JLabel)((JPanel)(w.getContentPane().getComponent(0))).getComponent(3);
                //System.out.println(label.getText());
                w.window_title.setText(lt);
                w.window_title.repaint();
                w.window_bar.revalidate();
                //w.window_bar.repaint();
                w.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    @Override
    public void paint(Graphics gw) {
        //gw.setClip(new RoundRectangle2D.Float(-1,-1,getWidth()-1,getHeight()-1,8,8));
        paintComponents(gw);
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(BasicButton.AA);
        g.setColor(BasicColorScheme.CS.b);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
    }
    

    @Override
    public void paintAll(Graphics gw) {
        super.paintAll(gw); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g = (Graphics2D) gw;
        g.setRenderingHints(BasicButton.AA);
        g.setColor(BasicColorScheme.CS.b);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        super.getGraphics().fillOval(0,0,super.getWidth(),super.getHeight());
    }

    @Override
    public void paintComponents(Graphics gw) {
        Graphics2D g=(Graphics2D)gw;
        g.setRenderingHints(BasicButton.AA);
        super.paintComponents(gw);
        g.setColor(BasicColorScheme.CS.b);
        g.drawRoundRect(0,0, getWidth()-1, getHeight()-1, 8, 8);
    }
    
    public void addGLPane(appguru.graphics.swing.GLPane arg) {/*error*/
        getContentPane().add(arg);
    }
}

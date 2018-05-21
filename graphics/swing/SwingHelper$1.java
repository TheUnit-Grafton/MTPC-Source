package appguru.graphics.swing;

final class SwingHelper$1 implements java.awt.event.ActionListener {
    SwingHelper$1() {
        super();
    }
    
    public void actionPerformed(java.awt.event.ActionEvent a) {
        if (!((javax.swing.JButton)a.getSource()).getModel().isPressed()) {
            ((javax.swing.JButton)a.getSource()).getModel().setPressed(true);
        }
    }
}

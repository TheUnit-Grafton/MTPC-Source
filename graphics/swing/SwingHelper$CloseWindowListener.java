package appguru.graphics.swing;

class SwingHelper$CloseWindowListener implements java.awt.event.ActionListener {
    public javax.swing.JFrame window;
    final appguru.graphics.swing.SwingHelper this$0;
    
    public SwingHelper$CloseWindowListener(appguru.graphics.swing.SwingHelper a, javax.swing.JFrame a0) {
              super();
        this.this$0 = a;
        this.window = a0;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent a) {
        this.window.dispatchEvent((java.awt.AWTEvent)new java.awt.event.WindowEvent((java.awt.Window)this.window, 201));
    }
}

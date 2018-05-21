package appguru.graphics.swing;

public class EventHandler implements java.awt.event.KeyListener, java.awt.event.MouseListener, java.awt.event.MouseWheelListener {
    public javax.swing.JFrame pane;
    final public static int NUM_KEYS = 1024;
    final public static int MOUSE_BUTTON_LEFT = 0;
    final public static int MOUSE_BUTTON_MIDDLE = 1;
    final public static int MOUSE_BUTTON_RIGHT = 2;
    public boolean mouseOn;
    public boolean mouseEntered;
    public boolean mouseExited;
    public int[] mouse;
    public java.awt.Point[] mouseDraggingStart;
    public java.awt.Point[] mouseDraggingEnd;
    public boolean[] mouseDragging;
    public static String[] keyNames;
    public java.util.List pressedKeys;
    public java.util.List pressedKeyChars;
    public int[] keys;
    public boolean anyKeyPressed;
    
    public EventHandler(javax.swing.JFrame a) {
        super();
        this.anyKeyPressed = false;
        this.keys = new int[1024];
        this.pressedKeys = (java.util.List)(Object)new java.util.ArrayList();
        this.pressedKeyChars = (java.util.List)(Object)new java.util.ArrayList();
        this.pane = a;
        this.mouse = new int[java.awt.MouseInfo.getNumberOfButtons() + 3];
        this.pane.requestFocus();
    }
    
    public void mousePressed(java.awt.event.MouseEvent a) {
        this.mouse[a.getButton() - 1 + 1] = 1;
    }
    
    public void mouseClicked(java.awt.event.MouseEvent a) {
        this.mouse[a.getButton() - 1 + 1] = 2;
    }
    
    public void mouseReleased(java.awt.event.MouseEvent a) {
        this.mouse[a.getButton() - 1 + 1] = 3;
    }
    
    public void mouseEntered(java.awt.event.MouseEvent a) {
        this.mouseOn = true;
        this.mouseEntered = true;
    }
    
    public void mouseExited(java.awt.event.MouseEvent a) {
        this.mouseOn = false;
        this.mouseExited = true;
    }
    
    public void mouseWheelMoved(java.awt.event.MouseWheelEvent a) {
        this.mouse[this.mouse.length - 1] = a.getWheelRotation();
    }
    
    public void keyTyped(java.awt.event.KeyEvent a) {
        this.keys[a.getKeyCode()] = 2;
    }
    
    public void keyReleased(java.awt.event.KeyEvent a) {
        this.keys[a.getKeyCode()] = 3;
        this.anyKeyPressed = true;
    }
    
    public void keyPressed(java.awt.event.KeyEvent a) {
        this.keys[a.getKeyCode()] = 1;
    }
    
    public void addKey(int i) {
        Object a = this.pressedKeys.iterator();
        while(((java.util.Iterator)a).hasNext()) {
            if (i == ((Integer)((java.util.Iterator)a).next()).intValue()) {
                return;
            }
        }
        this.pressedKeys.add((Object)Integer.valueOf(i));
    }
    
    public void addKey(char a) {
        char[] a0 = new char[1];
        a0[0] = a;
        String s = new String(a0);
        Object a1 = this.pressedKeyChars.iterator();
        while(((java.util.Iterator)a1).hasNext()) {
            if (((String)((java.util.Iterator)a1).next()).equals((Object)s)) {
                return;
            }
        }
        this.pressedKeyChars.add((Object)s);
    }
    
    public int getKey(int i) {
        return this.keys[i];
    }
    
    public int getKey(String s) {
        int i = 0;
        while(i < keyNames.length) {
            if (((Object)keyNames).equals((Object)s)) {
                return this.keys[i];
            }
            i = i + 1;
        }
        return -1;
    }
    
    public boolean releasedKey(int i) {
        return this.getKey(i) == 3;
    }
    
    public boolean keyDown(int i) {
        return this.getKey(i) != 0;
    }
    
    public boolean keyDown(String s) {
        return this.getKey(s) != 0;
    }
    
    public int getMouseWheel() {
        return this.mouse[this.mouse.length - 1];
    }
    
    public int mouseX() {
        return this.mouse[0];
    }
    
    public int mouseY() {
        return this.mouse[1];
    }
    
    public int[] getMousePosition() {
        int[] a = new int[2];
        a[0] = (this.mouse[0] != 0) ? 1 : 0;
        a[1] = (this.mouse[1] != 0) ? 1 : 0;
        return a;
    }
    
    public int getMouseButton(int i) {
        return this.mouse[2 + i];
    }
    
    public boolean mouseOver() {
        return this.mouseOn;
    }
    
    public int popIndex() {
        if (this.pressedKeys.isEmpty()) {
            return -1;
        }
        int i = ((Integer)this.pressedKeys.get(0)).intValue();
        this.pressedKeys.remove(0);
        return i;
    }
    
    public String popName() {
        try {
            int i = ((Integer)this.pressedKeys.get(0)).intValue();
            this.pressedKeys.remove(0);
            return keyNames[i];
        } catch(ArrayIndexOutOfBoundsException ignoredException) {
            String s = null;
            return s;
        }
    }
    
    public String popChar() {
        try {
            String s = (String)this.pressedKeyChars.get(0);
            this.pressedKeyChars.remove(0);
            return s;
        } catch(ArrayIndexOutOfBoundsException ignoredException) {
            String s0 = null;
            return s0;
        }
    }
    
    public int pullIndex() {
        try {
            return ((Integer)this.pressedKeys.get(0)).intValue();
        } catch(ArrayIndexOutOfBoundsException ignoredException) {
            return -1;
        }
    }
    
    public String pullName() {
        try {
            int i = ((Integer)this.pressedKeys.get(0)).intValue();
            return keyNames[i];
        } catch(ArrayIndexOutOfBoundsException ignoredException) {
            String s = null;
            return s;
        }
    }
    
    public String pullChar() {
        try {
            return (String)this.pressedKeyChars.get(0);
        } catch(ArrayIndexOutOfBoundsException ignoredException) {
            String s = null;
            return s;
        }
    }
    
    public java.util.List getPressedKeys() {
        return this.pressedKeys;
    }
    
    public java.util.List getPressedKeyChars() {
        return this.pressedKeyChars;
    }
    
    public String typedKeys() {
        java.util.Iterator a = this.pressedKeys.iterator();
        String s = "";
        Object a0 = a;
        while(((java.util.Iterator)a0).hasNext()) {
            Integer a1 = (Integer)((java.util.Iterator)a0).next();
            s = new StringBuilder().append(s).append(keyNames[a1.intValue()]).toString();
        }
        return s;
    }
    
    public String typedKeyChars() {
        java.util.Iterator a = this.pressedKeyChars.iterator();
        String s = "";
        Object a0 = a;
        while(((java.util.Iterator)a0).hasNext()) {
            String s0 = (String)((java.util.Iterator)a0).next();
            s = new StringBuilder().append(s).append(s0).toString();
        }
        return s;
    }
    
    public void work() {
        if (this.pane.hasFocus()) {
            try {
                this.mouse[0] = this.pane.getMousePosition().x;
                this.mouse[1] = this.pane.getMousePosition().y;
            } catch(Exception ignoredException) {
            }
        }
    }
    
    public void reset() {
        this.anyKeyPressed = false;
        int i = 0;
        while(i < this.keys.length) {
            int i0 = this.keys[i];
            label2: {
                label3: {
                    if (i0 == 2) {
                        break label3;
                    }
                    if (this.keys[i] != 3) {
                        break label2;
                    }
                }
                this.keys[i] = 0;
            }
            i = i + 1;
        }
        java.util.Iterator a = this.pressedKeys.iterator();
        int i1 = 0;
        Object a0 = a;
        while(((java.util.Iterator)a0).hasNext()) {
            Integer a1 = (Integer)((java.util.Iterator)a0).next();
            int i2 = this.keys[a1.intValue()];
            label0: {
                label1: {
                    if (i2 == 2) {
                        break label1;
                    }
                    if (this.keys[a1.intValue()] != 3) {
                        break label0;
                    }
                }
                this.pressedKeys.remove(i1);
                this.pressedKeyChars.remove(i1);
            }
            i1 = i1 + 1;
        }
        int i3 = 2;
        while(i3 < this.mouse.length) {
            this.mouse[i3] = 0;
            i3 = i3 + 1;
        }
        this.mouseEntered = false;
        this.mouseExited = false;
    }
    
    static {
        keyNames = new String[1024];
        int i = 0;
        while(i < 1024) {
            keyNames[i] = java.awt.event.KeyEvent.getKeyText(400 + i);
            i = i + 1;
        }
    }
}

package appguru.sound;

import javax.sound.sampled.LineEvent.Type;

public class SoundListener implements javax.sound.sampled.LineListener {
    public boolean running;
    
    public SoundListener() {
        super();
        this.running = true;
    }
    
    @Override
    public void update(javax.sound.sampled.LineEvent a) {
        if (a.getType() == Type.OPEN) {
            this.running = true;
        }
        if (a.getType() == Type.CLOSE) {
            this.running = false;
        }
    }
}

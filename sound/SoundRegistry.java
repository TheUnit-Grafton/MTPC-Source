package appguru.sound;

import java.util.ArrayList;

public class SoundRegistry extends Thread {
    public javax.sound.sampled.AudioFormat format;
    public ArrayList<Sound> sounds;
    public ArrayList<Sound> active;
    public boolean terminate;
    public long last_play;
    public boolean volumeChanged;
    public float volume;
    
    public SoundRegistry() {
        super();
        this.setName("AGE Audio");
        this.sounds = new java.util.ArrayList();
        this.active = new java.util.ArrayList();
        this.terminate = false;
        this.volumeChanged = false;
        this.volume = 1f;
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "INITIALISATION");
    }
    
    public void terminate() {
        this.stop();
        appguru.info.Console.successMessage(((Object)this).getClass().getName(), "SHUTDOWN");
    }
    
    public int add(String s) {
        this.sounds.add(new Sound(s));
        return this.sounds.size() - 1;
    }
    
    public appguru.sound.Sound get(int i) {
        return this.sounds.get(i);
    }
    
    public synchronized void play(int i) {
        this.active.add(new Sound(this.get(i)));
        this.active.get(this.active.size() - 1).loop = false;
        this.active.get(this.active.size() - 1).start();
    }
    
    public void play(int i, boolean b) {
        this.active.add(this.get(i));
        (this.active.get(this.active.size() - 1)).loop = b;
        (this.active.get(this.active.size() - 1)).start();
        (this.active.get(this.active.size() - 1)).setVolume(3f);
    }
    
    public void changeVolume(float f) {
        this.volumeChanged = true;
        this.volume = f;
    }
    
    @Override
    public synchronized void run() {
        ArrayList a = new ArrayList();
        long j = System.currentTimeMillis();
        while(true) {
            if (System.currentTimeMillis() - j > 40L && System.currentTimeMillis() - this.last_play > 10L) {
                j = System.currentTimeMillis();
                int i = 0;
                while(i < this.active.size()) {
                    if (!(this.active.get(i)).status.running) {
                        a.add(i);
                    }
                    i++;
                }
                java.util.Collections.reverse((java.util.List)(Object)a);
                Object a0 = a.iterator();
                while(((java.util.Iterator)a0).hasNext()) {
                    int i0 = ((Integer)((java.util.Iterator)a0).next());
                    this.active.remove(i0);
                }
                if (this.volumeChanged) {
                    Object a1 = this.active.iterator();
                    while(((java.util.Iterator)a1).hasNext()) {
                        Sound a2 = (Sound) ((java.util.Iterator)a1).next();
                        a2.setVolume(this.volume);
                    }
                    this.volumeChanged = false;
                }
                a = new ArrayList();
            }
        }
    }
}

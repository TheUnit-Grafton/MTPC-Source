package appguru.sound;

import appguru.info.Console;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound extends Thread {

    public Clip Sound;
    public AudioInputStream sound;
    public SoundListener status;
    public FloatControl control;
    public boolean loop;

    public Sound(Sound a) {
        super();
        this.sound = a.sound;
        try {
            this.Sound = (Clip) AudioSystem.getLine(new Info(Clip.class, this.sound.getFormat()));
        } catch (LineUnavailableException ex) {
            Console.errorMessage(Sound.class.getName(), "INVALID AUDIO FORMAT(WRONG PARAMS)");
            setNull(this);
            return;
        }
        this.loop = false;
        this.status = new appguru.sound.SoundListener();
        this.Sound.addLineListener(this.status);
    }

    public Sound(String s) {
        super();
        try {
            this.sound = AudioSystem.getAudioInputStream(new File(s));
        } catch (UnsupportedAudioFileException ex) {
            Console.errorMessage(Sound.class.getName(), "WRONG FILETYPE");
            setNull(this);
            return;
        } catch (IOException ex) {
            Console.errorMessage(Sound.class.getName(), "CORRUPTED FILE");
            setNull(this);
            return;
        }
        try {
            this.Sound = (Clip) (Object) javax.sound.sampled.AudioSystem.getLine(new Info(Clip.class, this.sound.getFormat()));
        } catch (LineUnavailableException ex) {
            Console.errorMessage(Sound.class.getName(), "INVALID AUDIO FORMAT(WRONG PARAMS)");
            setNull(this);
            return;
        }
        this.loop = false;
        this.status = new SoundListener();
        this.Sound.addLineListener(this.status);
    }
    
    public static void setNull(Sound s) {
        s=null;
    }

    @Override
    public synchronized void run() {
        try {
            this.Sound.open(this.sound);
        } catch (LineUnavailableException ex) {
            Console.errorMessage(Sound.class.getName(), "ERROR PLAYING SOUND : NO AVAILABLE LINE. RESTARTING.");
            this.start();
            return;
        } catch (IOException ex) {
            Console.errorMessage(Sound.class.getName(), "ERROR PLAYING SOUND : CORRUPTED DATA. RESTARTING.");
            this.start();
            return;
        }
        this.control = (FloatControl) this.Sound.getControl(FloatControl.Type.VOLUME);
        this.Sound.setFramePosition(0);
        if (this.loop) {
            this.Sound.loop(-1);
        } else {
            this.Sound.start();
        }
        this.Sound.drain();
        this.Sound.close();
    }

    public void end() {
        this.Sound.close();
    }

    public synchronized void setVolume(float f) {
        while (true) {
            try {
                float f0 = Math.abs(this.control.getMaximum() - this.control.getMinimum());
                this.control.setValue(Math.min(this.control.getMaximum(), this.control.getMinimum() + f * f0));
                return;
            } catch (Exception ignoredException) {
                Console.errorMessage(Sound.class.getName(), "CANT ADJUST VOLUME. RESTARTING.");
            }
        }
    }
}

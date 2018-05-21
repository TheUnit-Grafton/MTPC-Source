package appguru.net;

import java.io.InputStream;

public class Downloader {
    public Downloader() {
        super();
    }
    
    public static String readURL(java.net.URL a) {
        try {
            java.io.InputStreamReader a0 = new java.io.InputStreamReader(a.openStream());
            String s = "";
            int i = a0.read();
            while(i != -1) {
                i = a0.read();
                s = new StringBuilder().append(s).append((char)i).toString();
            }
            return s;
        } catch(java.io.IOException ignoredException) {
            appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("CORRUPTED FILE : ").append((Object)a).toString());
            return "CORRUPTED FILE - NOT READABLE";
        }
    }
    
    public static java.awt.image.BufferedImage downloadImage(String s) {
        try {
            java.net.URL a = new java.net.URL(s);
            try {
                return javax.imageio.ImageIO.read(a);
            } catch(java.io.IOException ignoredException) {
            }
            appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("CORRUPTED FILE : ").append(s).toString());
        } catch(java.net.MalformedURLException ignoredException0) {
            appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("INVALID URL : ").append(s).toString());
        }
        java.awt.image.BufferedImage a0 = null;
        return a0;
    }
    
    public static java.awt.image.BufferedImage downloadImage(java.net.URL a) {
        try {
            return javax.imageio.ImageIO.read(a);
        } catch(java.io.IOException ignoredException) {
            appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("CORRUPTED FILE : ").append(a.toString()).toString());
            java.awt.image.BufferedImage a0 = null;
            return a0;
        }
    }
    
    public static boolean downloadTo(String s, String s0) {
        appguru.net.DownloaderThread a = new appguru.net.DownloaderThread(s0, s);
        ((Thread)a).setName("AGE Downloader");
        ((Thread)a).start();
        try {
        a.join();
        } catch (Exception e) {};
        return a.status;
    }
}

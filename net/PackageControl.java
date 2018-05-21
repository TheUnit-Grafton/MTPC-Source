package appguru.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PackageControl {
    final public static double VERSION = 0.1;
    public static java.net.URL VERSION_CONTROL_SERVER;
    final public static String VCS_ADRESS = "a";
    
    public PackageControl() {
        super();
    }
    
    public static String readURL(java.net.URL a) throws IOException {
        java.io.InputStreamReader a0 = new java.io.InputStreamReader(a.openStream());
        String s = "";
        while(true) {
            int i = (char)a0.read();
            s = new StringBuilder().append(s).append((char)i).toString();
        }
    }
    
    public static String[] deprecatedGetPackage(String s) {
        try {
            String[] a = appguru.net.PackageControl.readURL(VERSION_CONTROL_SERVER).split("\n");
            int i = a.length;
            int i0 = 0;
            while(i0 < i) {
                String[] a0 = a[i0].split(" ");
                if (a0[0].equals(s)) {
                    return a0;
                }
                i0++;
            }
            appguru.info.Console.warningMessage(((Object)new appguru.net.PackageControl()).getClass().getName(), "PACKAGE COULD NOT BE FOUND");
        } catch(java.io.IOException ignoredException) {
            appguru.info.Console.errorMessage(((Object)new appguru.net.PackageControl()).getClass().getName(), "VERSION CONTROL SERVER NOT READABLE");
        }
        String[] a1 = null;
        return a1;
    }
    
    static {
        try {
            VERSION_CONTROL_SERVER = new URL("a");
            appguru.info.Console.errorMessage(((Object)new appguru.net.PackageControl()).getClass().getName(), "CONNECTED");
        } catch(MalformedURLException ignoredException) {
            appguru.info.Console.errorMessage(((Object)new appguru.net.PackageControl()).getClass().getName(), "INVALID URL");
        }
    }
}

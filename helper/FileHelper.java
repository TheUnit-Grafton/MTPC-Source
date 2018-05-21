package appguru.helper;

import appguru.info.Console;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHelper {

    public FileHelper() {
        super();
    }

    public static void deleteAll(java.io.File a) {
        if (a.isDirectory()) {
            java.io.File[] a0 = a.listFiles();
            int i = a0.length;
            int i0 = 0;
            while (i0 < i) {
                FileHelper.deleteAll(a0[i0]);
                i0++;
            }
            a.delete();
        } else if (a.exists()) {
            a.delete();
        }
    }

    public static void delete(java.io.File a) {
        if (a.exists()) {
            FileHelper.deleteAll(a);
        }
    }

    public static boolean moveFolder(String s, String s0) {
        try {
            FileHelper.delete(new File(s0));
            //new File(s0).mkdir();
            Files.move(new File(s).toPath(), new File(s0).toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileHelper.delete(new File(s));
            return true;
        } catch (java.io.IOException a2) {
            Console.errorMessage(appguru.helper.FileHelper.class.getName(), "COULDNT MOVE FILES - FURTHER LOG : " + a2.getMessage());
        }
        return false;
    }

    public static String readFile(String s) {
        if (new File(s).exists() == false) {
            return null;
        }
        try {
            BufferedReader r = new BufferedReader(new FileReader(s));
            String re = "";
            int i = r.read();
            while (i != -1) {
                re += (char) i;
                i = r.read();
            }
            return re;
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean writeAt(String s, String s0, int i) {
        RandomAccessFile a;
        try {
            a = new RandomAccessFile(new File(s), "w");
        } catch (FileNotFoundException ex) {
            Console.errorMessage(FileHelper.class.getName(), "FILE NOT FOUND");
            return false;
        }
        byte[] a0 = s0.getBytes(Charset.forName("UTF-8"));
        try {
            a.write(a0, i, a0.length);
        } catch (IOException ex) {
            Console.errorMessage(FileHelper.class.getName(), "CORRUPTED FILE");
            return false;
        }
        return true;
    }
}

package appguru.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Zipper {

    public Zipper() {
        super();
    }

    public static boolean unzip(String zipfile, String target, boolean config, String type) {
        java.io.File a = new java.io.File(target);
        if (!a.exists()) {
            a.mkdir();
        }
        label0:
        {
            label1:
            try {
                try {
                    java.util.zip.ZipInputStream a0 = new java.util.zip.ZipInputStream((java.io.InputStream) new java.io.FileInputStream(zipfile));
                    java.util.zip.ZipEntry a1 = a0.getNextEntry();
                    byte[] a2 = new byte[1024];
                    while (a1 != null) {
                        String s1 = a1.getName();
                        java.io.File a3 = new java.io.File(new StringBuilder().append((Object) a).append(java.io.File.separator).append(s1).toString());
                        if (a1.isDirectory() == false) {
                            a3.createNewFile();
                            java.io.FileOutputStream a4 = new java.io.FileOutputStream(a3);
                            int i = a0.read(a2);
                            while (i > 0) {
                                a4.write(a2, 0, i);
                                i = a0.read(a2);
                            }
                        } else {
                            a3.mkdir();
                        }
                        a3.getParentFile().mkdirs();
                        a1 = a0.getNextEntry();
                    }
                } catch (java.io.FileNotFoundException ignoredException) {
                    appguru.info.Console.errorMessage(appguru.helper.Zipper.class.getName(), ignoredException.toString());
                    break label1;
                }
                break label0;
            } catch (java.io.IOException ignoredException0) {
                appguru.info.Console.errorMessage(appguru.helper.Zipper.class.getName(), "CORRUPTED ZIP");
                break label0;
            }
            appguru.info.Console.errorMessage(appguru.helper.Zipper.class.getName(), "FILE NOT FOUND");
            return false;
        }
        if (config) {
            File a2 = a;
            while (true) {
                File[] files = a2.listFiles();
                if (files == null) {
                    return true;
                }
                if (files.length == 1 && files[0].isDirectory()) {
                    a2 = files[0];
                } else {
                    //System.err.println("BINGO!");
                    FileHelper.moveFolder(a2.getAbsolutePath(), "MTPC_Downloads" + File.separator + type + File.separator + a.getName() + "2");
                    //System.out.println(new File("MTPC_Downloads" + File.separator + type + File.separator + a.getName() + "2").isDirectory());
                    return new File("MTPC_Downloads" + File.separator + type + File.separator + a.getName() + "2").renameTo(new File("MTPC_Downloads" + File.separator + type + File.separator + a.getName()));
                }
                /*for (File f : files) {
                    if (f.getName().equals("init.lua")) {
                        FileHelper.moveFolder(a2.getPath(), "MTPC_Downloads" + File.separator + type + File.separator + a.getName() + "2");
                        new File("MTPC_Downloads" + File.separator + type + File.separator + a.getName() + "2").renameTo(new File("MTPC_Downloads" + File.separator + type + File.separator + a.getName()));
                        return;
                    }
                }*/
            }
        }
        return true;
    }
}

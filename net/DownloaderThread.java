package appguru.net;

import java.io.File;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

final class DownloaderThread extends Thread {

    final String val$target;
    final String val$url;
    boolean status;

    DownloaderThread(String s, String s0) {
        this.val$target = s;
        this.val$url = s0;
        this.status=false;
    }

    @Override
    public synchronized void run() {
        File a = new File(this.val$target);
        if (a.isDirectory()) {
            return;
        }
        try {

            URL a0 = new URL(this.val$url);
            try {
                ReadableByteChannel a1 = Channels.newChannel(a0.openStream());
                try {
                    if (!a.exists()) {
                        a.getParentFile().mkdirs();
                        a.createNewFile();
                    }
                    if (!a.canWrite()) {
                        throw new java.io.FileNotFoundException("Not writable.");
                    }
                    new java.io.FileOutputStream(a).getChannel().transferFrom(a1, 0L, 9223372036854775807L);
                } catch (java.io.FileNotFoundException ignoredException) {
                    appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("CANT WRITE TO FILE : ").append(this.val$target).toString());
                    return;
                }
            } catch (java.io.IOException ignoredException0) {
                appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("CORRUPTED FILE : ").append(this.val$target).toString());
                System.out.println(ignoredException0);
                return;
            }
        } catch (java.net.MalformedURLException ignoredException1) {
            appguru.info.Console.errorMessage(appguru.net.Downloader.class.getName(), new StringBuilder().append("INVALID URL : ").append(this.val$url).toString());
            return;
        }
        this.status=true;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import appguru.db.DataBase;
import appguru.db.Table;
import appguru.graphics.swing.BasicTableModel;
import appguru.graphics.swing.gui.basic.BasicOptionPane;
import appguru.info.Console;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author lars
 */
public class Crawlers extends Thread {

    public Crawler[] crawlers;
    public DataBase db;

    class CompleteMessage extends Thread {

        public Crawlers crawler;
        
        public BasicTableModel table;
        
        public CompleteMessage(Crawlers crawler, BasicTableModel table) {
            this.crawler=crawler;
            this.table=table;
        }

        @Override
        public synchronized void run() {
            BasicOptionPane.showMessage("MTPC - Crawlers - Started", "The crawlers have been started.", (byte)3);
            crawler.start();
            try {
                crawler.join();
                this.table.updateTable();
                BasicOptionPane.showMessage("MTPC - Crawlers - Search completed", "The crawlers have completed their search.", (byte)4);
            } catch (InterruptedException ex) {
                Console.errorMessage(crawler.getClass().getName(), "WAITFOR INTERRUPT");
            }
        }
    }

    public Crawlers(DataBase db, DBCrawler... crawlers) {
        super();
        this.setName("AGE Crawlers");
        this.db = db;
        this.crawlers = new Crawler[crawlers.length];
        System.arraycopy(crawlers, 0, this.crawlers, 0, crawlers.length);
        Console.successMessage(Crawlers.class.getName(), "INIT");
    }

    public void launch(BasicTableModel table) {
        new CompleteMessage(this, table).start();
    }

    public static Crawlers createCrawlers(DataBase db, ArrayList<DBCrawler> crawlers) {
        DBCrawler[] crawlers2 = new DBCrawler[crawlers.size()];
        for (int i = 0; i < crawlers2.length; i++) {
            crawlers2[i] = crawlers.get(i);
        }
        return new Crawlers(db, crawlers2);
    }

    @Override
    public synchronized void run() {
        Console.infoMessage(Crawlers.class.getName(), "LAUNCHED CRAWLERS");
        for (int i = 0; i < crawlers.length; i++) {
            Crawler c = crawlers[i];
            Console.infoMessage(c.getClass().getName(), "CRAWLER LAUNCHED");
            c.launchCrawl(db);
            Console.successMessage(c.getClass().getName(), "CRAWLER FINISHED");
        }
        Console.infoMessage(Crawlers.class.getName(), "CRAWLERS FINISHED");
    }
}

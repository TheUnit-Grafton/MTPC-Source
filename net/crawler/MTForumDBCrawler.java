/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import appguru.db.DataBase;
import appguru.db.Table;
import appguru.info.Console;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author lars
 */
public class MTForumDBCrawler extends DBCrawler {

    public static final String MT_FORUMS="https://forum.minetest.net";
    
    public Document page;
    public String type;
    public int amount;
    public static boolean ENABLE_LOG=true;

    public MTForumDBCrawler(byte mode,String site, String type, String amount_selector, Table t) {
        super(t);
        this.mode=mode;
        try {
            page = Jsoup.connect(site).get();
        } catch (IOException ex) {
            Console.errorMessage(MTForumDBCrawler.class.getName(), "INIT FAILED : SITE NOT PROPER : "+site);
            return;
        }
        this.type = type;
        Element members_amount = page.selectFirst(amount_selector);
        String mat = members_amount.text();
        String m2 = "";
        for (int i = 0; i < mat.length(); i++) {
            char c = mat.charAt(i);
            if (c < 48 || c > 57) {
                break;
            }
            m2 += c;
        }
        this.amount = Integer.parseInt(m2);
    }
    
    @Override
    public String toNiceString() {
        return "MT Forum Topic Crawler - Type : "+type+" - Topic : "+page.title()+" - Mode : "+super.modeToString() + " - Amount : "+Integer.toString(amount);
    }
    

    @Override
    public void launchCrawl(DataBase db) {
        for (int i = 30; i < amount + 30; i += 30) {
            Elements links = page.select("a.topictitle");
            if (links == null) {
                Console.errorMessage(MTForumDBCrawler.class.getName(), "SITE NOT PROPER");
                continue;
            }
            Object[][] fill_in = new Object[links.size()][t.c2.length];
            for (int it=0; it < links.size(); it++) {
                Element e=links.get(it);
                String visit = e.attr("href");
                String title = e.text();
                Document doc;
                try {
                    doc = Jsoup.connect("https://forum.minetest.net" + visit.substring(1)).get();
                } catch (IOException ex) {
                    Console.errorMessage(MTForumDBCrawler.class.getName(), "SITE NOT PROPER : "+"https://forum.minetest.net" + visit.substring(1));
                    continue;
                }
                Elements o = doc.select("dl.postprofile a");
                Element content = doc.selectFirst("div.postbody div.content");
                Element additional = doc.selectFirst("dl.attachbox");
                Element first_img = content.selectFirst("img");
                Elements zipfile_links = content.select("a");
                if (additional != null) {
                    Elements addus = additional.select("a");
                    zipfile_links.addAll(addus);
                }
                String zipfile = null;
                String github = null;
                for (Element no : zipfile_links) {
                    String href = no.attr("href");
                    if (href.charAt(0) == '.') {
                        href = "https://forum.minetest.net" + href.substring(1);
                    }
                    //System.out.println(href);
                    if (href != null && !href.isEmpty()) {
                        try {
                            URLConnection conn = new URL(href).openConnection();
                            if (conn.getContentType().contains("zip")) {
                                zipfile = href;
                                break;
                            }
                        } catch (Exception NO) {
                        }
                        if (href.contains("github")) {
                            if (github == null) {
                                github = href + "/archive/master.zip";
                                try {
                                    new URL(github);
                                } catch (Exception gffrf) {
                                    github = null;
                                }
                            }
                        }
                    }
                }
                if (zipfile == null) {
                    zipfile = github;
                }
                String image_link = first_img == null ? null : (first_img.attr("src").startsWith(".") ? "https://forum.minetest.net"+first_img.attr("src").substring(1):first_img.attr("src"));
                //System.out.println(image_link);
                //System.out.println(table.types[table.indexes.get("screenshot")+1]);
                //System.out.println(DataBase.convert(image_link,table.types[table.indexes.get("screenshot")+1]));
                String author = o.get((o.size() == 1) ? 0 : 1).text();
                ArrayList<String> parts = new ArrayList<String>();
                boolean started = false;
                StringBuilder current = new StringBuilder();
                for (int t = 0; t < title.length(); t++) {
                    char c = title.charAt(t);
                    if (c=='[' || c==']') {
                        if (!current.toString().isEmpty() && !current.toString().matches("[ ]*")) {
                            for (int z = current.length() - 1; z > -1; z--) {
                                char ca = current.charAt(z);
                                if (ca != ' ') {
                                    break;
                                }
                                current.deleteCharAt(z);
                            }
                            for (int z = 0; z < current.length(); z++) {
                                char ca = current.charAt(z);
                                if (ca != ' ') {
                                    break;
                                }
                                current.deleteCharAt(z);
                            }
                            parts.add(current.toString());
                            current = new StringBuilder();
                        }
                        started = !started;
                    } else {
                        current.append(c);
                    }
                }
                if (current.length() != 0) {
                    parts.add(current.toString());
                }
                boolean correct=false;
                Float version = null;
                for (int p = parts.size() - 1; p > -1; p--) {
                    String lc = parts.get(p).toLowerCase();
                    if (type.equals(lc)) {
                        correct=true;
                        parts.remove(p);
                    }
                    else if (lc.equals("git") || lc.equals("github")) {
                        parts.remove(p);
                    } else {
                        try {
                            StringBuilder dots = new StringBuilder(lc);
                            int first = lc.indexOf('.');
                            for (int lp = dots.length() - 1; lp > -1; lp--) {
                                if (lp != first && dots.charAt(lp) == '.') {
                                    dots.deleteCharAt(lp);
                                }
                            }
                            version = Float.parseFloat(dots.toString());
                        } catch (NumberFormatException ign) {
                            continue;
                        }
                        parts.remove(p);
                    }
                }
                if (!correct || parts.isEmpty()) {
                    continue;
                }
                String tit = parts.get(0);
                String nam = (parts.size() >= 2) ? parts.get(1) : tit.toLowerCase();
                fill_in[it - 1][t.indexes.get("title")] = tit;
                fill_in[it - 1][t.indexes.get("name")] = nam;
                fill_in[it - 1][t.indexes.get("version")] = version;
                fill_in[it - 1][t.indexes.get("author")] = author;
                fill_in[it - 1][t.indexes.get("homepage")] = "https://forum.minetest.net" + visit.substring(1);
                fill_in[it - 1][t.indexes.get("download")] = zipfile;
                fill_in[it - 1][t.indexes.get("screenshot")] = image_link;
                //System.out.println("the mod " + tit + ", named " + nam + ((version == null) ? "" : ", version " + version) + " by " + author + ", homepage : " + "https://forum.minetest.net" + visit.substring(1) + ", download : " + zipfile);
            }
            t.insertAll(db, fill_in, t.name.equals("authors") ? "author":"name",mode);
            Console.infoMessage(MTForumDBCrawler.class.getName(), "PROCESSED 30 ENTRIES");
            try {
                page = Jsoup.connect("https://forum.minetest.net/viewforum.php?f=11&start=" + i).get();
            } catch (IOException ex) {
                Console.errorMessage(MTForumDBCrawler.class.getName(), "EXITING : SITE NOT PROPER : "+"https://forum.minetest.net/viewforum.php?f=11&start=" + i);
                return;
            }
        }
    }
}

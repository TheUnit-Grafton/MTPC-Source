/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import static appguru.AGE.column_assignments;
import appguru.db.Clause;
import appguru.db.DataBase;
import appguru.db.Table;
import appguru.info.Console;
import java.io.IOException;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author lars
 */
public class MTForumMemberDBCrawler extends DBCrawler {

    public static final String MT_FORUMS = "https://forum.minetest.net";

    public String site;
    public Document page;
    public int amount;

    public MTForumMemberDBCrawler(byte mode,String site, String amount_selector, Table t) {
        super(t);
        this.mode=mode;
        try {
            page = Jsoup.connect(site).get();
        } catch (IOException ex) {
            Console.errorMessage(MTForumMemberDBCrawler.class.getName(), "INIT FAILED : SITE NOT PROPER : " + site);
            return;
        }
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
        return "MT Forum Memberlist Crawler - Mode : "+super.modeToString() + " - Amount : "+Integer.toString(amount);
    }

    @Override
    public void launchCrawl(DataBase db) {
        for (int j = 30; j < amount; j += 30) {
            Element members_table = page.selectFirst("table.table1[id=memberlist]");
            Elements entries = members_table.select("tr");
            Object[][] fill_in = new Object[30][t.c2.length];
            for (int i = 1; i < entries.size(); i++) {
                Element e = entries.get(i).selectFirst("td");
                Document doc;
                try {
                    doc = Jsoup.connect("https://forum.minetest.net" + e.selectFirst("a").attributes().get("href").substring(1)).get();
                } catch (IOException ex) {
                    Console.errorMessage(MTForumMemberDBCrawler.class.getName(), "SITE NOT PROPER : "+"https://forum.minetest.net" + e.selectFirst("a").attributes().get("href").substring(1));
                    continue;
                }
                Element a=doc.selectFirst("dl.left-box dt img");
                String image= (a==null ? null:a.attr("src"));
                fill_in[i - 1][t.indexes.get("profile_picture")] = image;
                String nameval = doc.select("dl dd span").text();
                boolean exists = t.existsClauses(db, new Clause(true, true, false, false, "author", "=", nameval));
                if (exists) {
                    continue;
                }
                fill_in[i - 1][t.indexes.get("author")] = nameval;
                fill_in[i - 1][t.indexes.get("mt_forums")] = nameval;
                Elements headers = doc.select("dt");
                Elements infos = doc.select("dd");
                for (int k = 0; k < headers.size(); k++) {
                    String cr = (String) column_assignments.get(headers.get(k).text());
                    if (cr != null) {
                        fill_in[i - 1][t.indexes.get(cr)] = infos.get(k).text();
                    }
                    if (headers.get(k).text().equals("GitHub:")) {
                        label0:
                        {
                            Document ghi;
                            try {
                                ghi = Jsoup.connect("https://www.github.com/" + infos.get(k).text()).get();
                            } catch (Exception e2) {
                                break label0;
                            }
                            Element el = ghi.selectFirst("h1.vcard-names span.p-name.vcard-fullname.d-block.overflow-hidden");
                            if (el == null) {
                                break label0;
                            }
                            String name = el.text();
                            String[] names = name.split(" ");
                            switch (names.length) {
                                case 1:
                                    fill_in[i - 1][t.indexes.get("firstname")] = names[0];
                                    break;
                                case 2:
                                    fill_in[i - 1][t.indexes.get("firstname")] = names[0];
                                    fill_in[i - 1][t.indexes.get("lastname")] = names[1];
                                    break;
                                default:
                                    String fn = "";
                                    for (int h = 0; h < names.length - 1; h++) {
                                        fn += names[h];
                                    }
                                    fill_in[i - 1][t.indexes.get("firstname")] = fn;
                                    fill_in[i - 1][t.indexes.get("lastname")] = names[names.length - 1];
                            }
                            Element em = ghi.selectFirst("a.u-email");
                            if (em == null) {
                                break label0;
                            }
                            String e_mail = em.text();
                            fill_in[i - 1][t.indexes.get("email")] = e_mail;
                        }
                    } else if (headers.get(k).text().equals("Age:")) {
                        int birthday = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(infos.get(k).text());
                        fill_in[i - 1][t.indexes.get("birthday")] = birthday;
                    }
                    //System.out.println(headers.get(k).text() + infos.get(k).text());
                }
                //System.out.println("Hello : "+doc.select("div.panel.bg1 div.inner dl.left-box.details dd"));
            }
            t.insertAll(db, fill_in, t.name.equals("authors") ? "author":"name",mode);
            Console.infoMessage(MTForumDBCrawler.class.getName(), "PROCESSED 30 MEMBERS");
            try {
                //System.out.println("Page : " + j / 30 + " out of " + amount / 30);
                page = Jsoup.connect("https://forum.minetest.net/memberlist.php?start=" + Integer.toString(j)).get();
            } catch (IOException ex) {
                Console.errorMessage(MTForumMemberDBCrawler.class.getName(), "EXITING : SITE NOT PROPER : "+"https://forum.minetest.net/memberlist.php?start=" + Integer.toString(j));
                return;
            }
        }
    }

}

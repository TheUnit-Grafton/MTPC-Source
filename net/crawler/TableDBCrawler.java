/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import appguru.db.DataBase;
import appguru.db.Table;
import appguru.graphics.swing.gui.basic.BasicFormspec;
import appguru.info.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author lars
 */
public class TableDBCrawler extends DBCrawler {
    public Elements table_rows;
    public int[] indexes;
    public Document page;
    public TableDBCrawler(byte mode,String webpage, String table_selector, HashMap<String, String> column_assignments, Table t) {
        super(t);
        this.mode=mode;
        try {
        page=Jsoup.connect(webpage).get();
        } catch (Exception e) {
            Console.errorMessage(TableDBCrawler.class.getName(), "INVALID WEBPAGE : JSOUP CANT CONNECT");
        }
        table_rows=page.selectFirst(table_selector).select("tr");
        Elements headers=table_rows.get(0).select("td, th");
        table_rows.remove(0);
        indexes=new int[headers.size()];
        for (int i=0; i < headers.size(); i++){
            String represented=column_assignments.get(headers.get(i).text());
            if (represented == null) {
                indexes[i]=-1;
                continue;
            }
            String[] rep=represented.split("-");
            indexes[i]=t.indexes.get(rep[0]);
            if (rep.length > 1) {
                indexes[i]=-indexes[i];
            }
        }
    }
    public static HashMap parse(String ca_parse) {
        HashMap<String, String> column_assignments=new HashMap();
        for (String s:ca_parse.split("\n")) {
            ArrayList<String> sa=BasicFormspec.split(s, '-');
            for (int n=0; n < sa.size(); n++) {
                sa.set(n, sa.get(n).replace("\"",""));
            }
            String sp="";
            for (int i=1; i < sa.size(); i++) {
                sp+=sa.get(i)+"-";
            }
            sp=sp.substring(0,sp.length()-1);
            column_assignments.put(sa.get(0), sp);
        }
        return column_assignments;
    }
 
    public Object[][] retrieveCrawl(Table t) {
        Object[][] fill_in=new Object[table_rows.size()][t.c2.length];
        for (int i=0; i < table_rows.size(); i++) {
            Elements row=table_rows.get(i).select("td, th");
            for (int k=0; k < row.size(); k++) {
                int index=indexes[k];
                if (index == -1) {
                    continue;
                }
                if (index < 0) {
                    try {
                        fill_in[i][-index]=new URL(row.get(k).getElementsByAttribute("href").get(0).attr("href"));
                    } catch (MalformedURLException ex) {
                    }
                    continue;
                }
                if (fill_in[i][index] != null && fill_in[i][index].toString().isEmpty() == false) {
                    fill_in[i][index]=fill_in[i][index]+" | "+DataBase.convert(row.get(k).text(),t.types[index+1]);
                    continue;
                }
                fill_in[i][index]=DataBase.convert(row.get(k).text(),t.types[index+1]);
            }
        }
        return fill_in;
    }
    
    @Override
    public String toNiceString() {
        return "HTML Table Crawler - Topic : "+page.title()+" - Mode : "+super.modeToString();
    }
    
    @Override
    public void launchCrawl(DataBase db) {
        t.insertAll(db, retrieveCrawl(t), t.name.equals("authors") ? "author":"name",mode);
    }
}

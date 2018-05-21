/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import appguru.db.DataBase;

/**
 *
 * @author lars
 */
public interface Crawler {
    public void launchCrawl(DataBase db);
    public String toNiceString();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.net.crawler;

import appguru.db.DataBase;
import appguru.db.Table;

/**
 *
 * @author lars
 */
public abstract class DBCrawler implements Crawler {
    
    public byte mode=Table.MODE_ADD_NEW;
    public Table t;
    
    public DBCrawler(Table t) {
        this.t=t;
    }
    
    public String modeToString() {
        switch(mode) {
            case Table.MODE_ADD_NEW:
                return "Only add new";
            case Table.MODE_UPDATE:
                return "Update existing";
            case Table.MODE_OVERWRITE:
                return "Replace existing";
        }
        return "MODE NOT REGISTERED";
    }

    @Override
    public void launchCrawl(DataBase db) {
    }

    @Override
    public String toNiceString() {
        return "Has to be overridden !";
    }
    
}

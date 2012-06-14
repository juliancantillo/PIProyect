/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbhandler;

import java.io.Serializable;

/**
 *
 * @author julianacb
 */
public class DbData implements Serializable{
    
    public String host, port, db, user, password;

    public DbData(String host, String port, String db, String usr, String pass) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.user = usr;
        this.password = pass;
    }
        
}

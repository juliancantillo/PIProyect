/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;

/**
 *
 * @author julianacb
 */
public class msgLogin implements Serializable{
    
    private String user, pass;

    public msgLogin(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }
        
}

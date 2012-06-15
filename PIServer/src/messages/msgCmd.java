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
public class msgCmd implements Serializable{
    
    private String user;
    private String cmd;

    public msgCmd(String user, String cmd) {
        this.user = user;
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public String getUser() {
        return user;
    }
    
}

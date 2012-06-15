/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author julianacb
 */
public class msgUserMail implements Serializable{
    
    private String[] headers;
    private ArrayList data;

    public msgUserMail(String[] headers, ArrayList data) {
        this.headers = headers;
        this.data = data;
    }

    public ArrayList getData() {
        return data;
    }

    public String[] getHeaders() {
        return headers;
    }
    
}

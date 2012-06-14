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
public class msgEmail implements Serializable{
    
    private String from, to, subject, body, date;

    public msgEmail(String from, String to, String subject, String body, String date) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }
    
}

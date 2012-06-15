/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import java.net.Socket;

/**
 *
 * @author julianacb
 */
public class ClientSession{
    
    private String mail;
    private String user;
    private Socket socket;

    public ClientSession(String mail, String user, Socket socket) {
        this.mail = mail;
        this.user = user;
        this.socket = socket;
    }

    public String getMail() {
        return mail;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUser() {
        return user;
    }
        
}

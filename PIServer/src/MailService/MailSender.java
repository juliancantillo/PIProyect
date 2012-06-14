/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import java.io.ObjectOutputStream;
import java.net.Socket;
import piserver.MsgListener;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class MailSender  extends Thread{
    
    private MsgListener msgListener;
    private Socket client;
    private Object obj;
    
    public MailSender (MsgListener msg, Socket clientSocket, Object obj) {
        msgListener = msg;
        client = clientSocket;
        this.obj = obj;
    }

    @Override
    public void run() {

        try {
            client.setSoTimeout(5000);
            
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            
            out.writeObject(obj);
            out.flush();

        }
        catch (Exception e) {
            e.printStackTrace();
        }                
    }
}

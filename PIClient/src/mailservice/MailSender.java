/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailservice;

import piclient.PIClient;
import piclient.ServerConfig;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import messages.msgEmail;

/**
 *
 * @author julianacb
 */
public class MailSender extends Thread{
    
    private msgEmail mail;
    private Socket s;
    private ServerConfig conf;

    public MailSender(msgEmail mail) {
        this.mail = mail;
        s = PIClient.getMailSocket();
    }
    
    @Override
    public void run() {
        super.run();
        
        try {
            s = PIClient.getMailSocket();
            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(mail);
                        
            new MailReceiver(s).start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}

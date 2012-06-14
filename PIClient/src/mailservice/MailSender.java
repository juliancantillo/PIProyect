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
        conf = PIClient.getConfig();
    }
        
    @Override
    public void run() {
        super.run();
        
        try {
            s = new Socket(conf.getIp(), ServerConfig.SERVER_PORT);
            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(mail);
            
            System.out.print("Enviando mensaje a servidor");
            
            oos.close();
            os.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}

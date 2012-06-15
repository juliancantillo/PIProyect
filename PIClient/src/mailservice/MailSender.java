/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailservice;

import java.io.ObjectOutputStream;
import java.net.Socket;
import piclient.PIClient;
import piclient.ServerConfig;

/**
 *
 * @author julianacb
 */
public class MailSender extends Thread{
    
    private Object obj;
    private Socket s;
    
    public MailSender(Object obj) {
        this.obj = obj;
        s = PIClient.getMailSocket();
        
        if(s == null){
            try{
                s = new Socket(PIClient.getConfig().getIp(), ServerConfig.SERVER_PORT);
                PIClient.setMailSocket(s);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
        
    @Override
    public void run() {
        super.run();
        
        try {            
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            
            out.writeObject(obj);
            out.flush();
                        
            new MailReceiver(s).start();
            
        } catch (Exception e) {
            System.out.print("Se perdio la conexion con el servidor: " + e.getMessage());
        }

    }
    
}

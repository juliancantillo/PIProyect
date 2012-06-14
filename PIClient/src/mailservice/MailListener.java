/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailservice;

import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class MailListener extends Thread {

    private Socket s;
   
    public MailListener(Socket s) {
        this.s = s;
    }
      
    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            User u = (User) in.readObject();
            
            if(u != null){
                
            }else{
                JOptionPane.showMessageDialog(null, "Credenciales Erroneas");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailservice;

import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import messages.msgUserMail;
import piclient.PIClient;

/**
 *
 * @author julianacb
 */
public class MailReceiver extends Thread {

    private Socket s;
   
    public MailReceiver(Socket s) {
        this.s = s;
    }
      
    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            Object obj = in.readObject();

            if(obj instanceof String){
                JOptionPane.showMessageDialog(null, obj, "Error enviando correo", JOptionPane.WARNING_MESSAGE);
            }

            if(obj instanceof msgUserMail){
                PIClient.updateInbox((msgUserMail)obj);
            }
        } catch (Exception e) {
            System.out.print("Se perdio la conexion con el servidor" + e.getMessage());
        }
    }
}

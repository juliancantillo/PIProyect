/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailservice;

import java.io.ObjectInputStream;
import javax.swing.JOptionPane;
import messages.msgUserMail;
import piclient.PIClient;

/**
 *
 * @author julianacb
 */
public class MailReceiver extends Thread {

    private ObjectInputStream in;
   
    public MailReceiver(ObjectInputStream i) {
        this.in = i;
    }
      
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                Object obj = in.readObject();
                
                JOptionPane.showMessageDialog(null, "Recibido: "+ obj.getClass().getName());

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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import piserver.MsgListener;

/**
 *
 * @author julianacb
 */
public class MailListener extends Thread {

    private MsgListener msgListener;
    private Socket socket;
    private Object objPackage;

    public MailListener(MsgListener msg, Socket clientSocket) {
        msgListener = msg;
        socket = clientSocket;
    }

    @Override
    public void run() {
        super.run();

        while(!isInterrupted()){
            try {
                socket.setSoTimeout(5000);

                ObjectInputStream out = new ObjectInputStream(socket.getInputStream());
                
                objPackage = out.readObject();
                
                if(objPackage != null){
                    msgListener.inMsg(objPackage, socket);
                }
            }
            catch (Exception e) {
                //System.err.print("\nError en " +e.getLocalizedMessage());
            }
        }
    }
    
}

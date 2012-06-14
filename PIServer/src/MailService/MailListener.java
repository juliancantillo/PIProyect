/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import dbhandler.DbHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import messages.msgEmail;
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

        try {
            clientSocket.setSoTimeout(5000);
            
            InputStream is = clientSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            objPackage = ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
        
        if(objPackage != null){
            msgListener.inMsg(objPackage, socket);
        }
        
    }
    
}

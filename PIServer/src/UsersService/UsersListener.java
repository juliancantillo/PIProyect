/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UsersService;

import java.io.ObjectInputStream;
import java.net.Socket;
import messages.msgLogin;
import piserver.MsgListener;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class UsersListener extends Thread {

    private MsgListener msgListener;
    private Object objPackage;
    private Socket client;

    public UsersListener(MsgListener msg, Socket clientSocket) {
        msgListener = msg;
        client = clientSocket;
    }

    @Override
    public void run() {
        super.run();
        
        while(!isInterrupted()){   
            try {
                client.setSoTimeout(5000);

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                objPackage = in.readObject();
                
                msgListener.inMsg(objPackage, client);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UsersService;

import java.io.*;
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
    private msgLogin login;
    private Socket client;

    public UsersListener(MsgListener msg, Socket clientSocket) {

        msgListener = msg;
        client = clientSocket;

        try {
            clientSocket.setSoTimeout(5000);
            
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            
            login = (msgLogin) in.readObject();
            
            piserver.PIServer.addLog("Se esta autenticando un cliente con usuario "+login.getUser() );
            
            User u = dbhandler.DbHandler.getUser(login);
                                                
//            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
        
        if(login != null){
            msgListener.inMsg(login, client);
        }
        
    }
    
}

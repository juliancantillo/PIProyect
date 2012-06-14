/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UsersService;

import java.io.ObjectOutputStream;
import java.net.Socket;
import piserver.MsgListener;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class UsersSender extends Thread{
    
    private MsgListener msgListener;
    private Socket client;
    private User user;
    
    public UsersSender (MsgListener msg, Socket clientSocket, User u) {
        msgListener = msg;
        client = clientSocket;
        user = u;
    }

    @Override
    public void run() {

        try {
            client.setSoTimeout(5000);
            
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            
            out.writeObject(user);
            out.flush();
            
            if(user != null){
                piserver.PIServer.addLog("Cliente reconocido como "+user.getName() );
            }else{
                piserver.PIServer.addLog("Intendo de login fallido");
            }
                        
//            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }                
    }
    
}

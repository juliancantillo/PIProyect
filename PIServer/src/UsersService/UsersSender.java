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
    
    private Socket client;
    private Object obj;
    
    public UsersSender (MsgListener msg, Socket clientSocket, Object o) {        
        this.client = clientSocket;
        this.obj = o;
    }

    @Override
    public void run() {

        try {
            client.setSoTimeout(5000);
            
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            
            out.writeObject(obj);
            out.flush();
            
            if(obj instanceof User){
                User user = (User) obj;
                
                if(user != null){
                    piserver.PIServer.addLog("Cliente reconocido como "+user.getName() );
                }else{
                    piserver.PIServer.addLog("Intendo de login fallido");
                }
            }
                        
//            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }                
    }
    
}

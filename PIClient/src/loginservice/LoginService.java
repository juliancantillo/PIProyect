/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loginservice;

import piclient.PIClient;
import piclient.ServerConfig;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import messages.msgLogin;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class LoginService extends Thread{
    
    private ServerConfig conf;
    private msgLogin login;
    private Socket s;
    
    public LoginService(msgLogin login){
        this.login = login;
        conf = PIClient.getConfig();
    }
    
    
    public static void getLogin(Socket socket){
        LoginListener getUser = new LoginListener(socket);
        getUser.start();
    }
    
    public static void setLoggedUser(User u){
        PIClient.setUser(u);
        PIClient.startApp();
    }
    
    @Override
    public void run() {

        try {
            s = new Socket(conf.getIp(), ServerConfig.USERS_PORT);
                                    
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            
            out.writeObject(login);
            
            LoginService.getLogin(s);
                        
        }catch (java.net.ConnectException e) {
            String ip = PIClient.getConfig().getIp();
            JOptionPane.showMessageDialog(null, "Error :"+e.getMessage() + "\n\nVerifique que el servidor esté inicalizado\nVerifique que la direccion configurada sea la correcta\n\nIP Servidor <<"+ip+">>", "Error con el Servidor", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}

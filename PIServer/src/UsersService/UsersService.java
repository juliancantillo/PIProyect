/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UsersService;

import dbhandler.DbHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import messages.msgLogin;
import piserver.MsgListener;
import piserver.PIServer;
import piserver.ServerConstants;
import userhandler.User;

/**
 *
 * @author julianacb
 */
public class UsersService extends Thread implements MsgListener {

    public UsersService() {
    }

    @Override
    public void run() {
        try {

            // crear objeto ServerSocket para las conexiones entrantes
            ServerSocket socketServer = new ServerSocket(
                    ServerConstants.USERS_PORT, 100);

            PIServer.addLog("Servicio de usuarios inicializado en el puerto "
                    + ServerConstants.USERS_PORT + " ...");
            
            PIServer.addLog("IP Servidor "
                    + socketServer.getInetAddress().getHostName() + " ...");

            // escuchar constantemente en espera de clientes
            while (true) {

                // aceptar nueva conexi�n de cliente
                Socket socketClient = socketServer.accept();

                // crear nuevo objeto SubprocesoReceptor para recibir
                // mensajes del cliente
                new UsersListener(this, socketClient).start();

                // imprimir informaci�n sobre la conexi�n
                PIServer.addLog("Conexion recibida de: "
                        + socketClient.getInetAddress()+ ":" + socketClient.getPort());

            } // fin de instrucci�n while     

        } // fin del bloque try
        // manejar posible excepci�n al crear servidor y conectar clientes
        catch (java.net.BindException e){
            PIServer.addError("El servidor ya está inicializado");
        }
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }

    @Override
    public void inMsg(Object obj, Socket clientSocket) {

        if (obj instanceof msgLogin) {
            
            msgLogin login = (msgLogin) obj;
            
            piserver.PIServer.addLog("Se esta autenticando un cliente con usuario "+login.getUser() );
            
            User u = DbHandler.getUser(login);
            UsersSender usrSender = new UsersSender(this, clientSocket, u);
            usrSender.start();
            
        } else {
            System.out.print("Error de objeto");
        }

    }
}

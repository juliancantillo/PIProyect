/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iclient;

import java.io.Serializable;

/**
 *
 * @author julianacb
 */
public class ServerConfig implements Serializable{
    
    private String ip;
    
    public static final String MULTICAST_IP = "239.0.0.1";
    // puerto para escuchar transmisi�n m�ltiple de datagramas
    public static final int MULTICAST_PORT = 5555;
    // puerto para enviar datagramas de transmisi�n m�ltiple
    public static final int MULTICAST_SENDING_PORT = 5554;
    // puerto para conexiones de objetos Socket a ServidorDeitelMessenger
    public static final int SERVER_PORT = 12345;
    public static final int USERS_PORT = 12346;
    // Objeto String que indica desconexi�n
    public static final String DISCONNECT_CODE = "DESCONECTAR";
    // Objeto String que separa el nombre del usuario del cuerpo del mensaje
    public static final String MSG_SEPARATOR = ">>>";
    // tama�o del mensaje (en bytes)
    public static final int MSG_SIZE = 512;

    public ServerConfig(String ip) {
        this.ip = ip;
    }
    
    public String getIp() {
        return ip;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import java.net.Socket;

/**
 *
 * @author julianacb
 */
public interface MsgListener {
    
    public void inMsg(Object obj, Socket clientSocket);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import java.util.ArrayList;

/**
 *
 * @author julianacb
 */
public class SessionsAdmin extends Thread{
    
    private static ArrayList connectedUsers = new ArrayList();
    private ArrayList messageQueue = new ArrayList();
    
    public static synchronized void addUser(ClientSession clises){
        connectedUsers.add(clises);
    }
    
    public static synchronized void removeUser(ClientSession clises){
        int index = connectedUsers.indexOf(clises);
        if(index != -1){
            connectedUsers.remove(index);
        }
    }
    
}

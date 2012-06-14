/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userhandler;

import java.io.Serializable;

/**
 *
 * @author julianacb
 */
public class User implements Serializable{
    
    private String nickname;
    private String name;
    private String user_id;    
    private String password;
    private String email;

    /**
     * 
     * @param nickname
     * @param name
     * @param user_id
     * @param password
     * @param email 
     */
    public User(String user_id, String nickname, String name, String password, String email) {
        this.nickname = nickname;
        this.name = name;
        this.user_id = user_id;
        this.password = password;
        this.email = email;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }
    
}

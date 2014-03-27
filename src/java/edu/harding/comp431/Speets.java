/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harding.comp431;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author asanchez
 */
public class Speets implements Serializable{
    Speets(){
        
    }
    
    Speets(int speedid, String username, String message, String timestamp){
        this.speedid = speedid;
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    private int speedid;
    private String username;
    private String message;
    private String timestamp;

    public int getSpeedid() {
        return speedid;
    }

    public void setSpeedid(int speedid) {
        this.speedid = speedid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Milos
 */
public class Session {
    private static Session instance;
    private Map<String, Object> map;
    private Socket socket;
    
    private Session() {
        map = new HashMap<>();
    }
    
    public static Session getInstance(){
        if (instance == null) instance = new Session();
        return instance;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    
}

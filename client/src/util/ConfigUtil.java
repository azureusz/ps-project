/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import session.Session;

/**
 *
 * @author Milos
 */
public class ConfigUtil {
    private static ConfigUtil instance;
    private Properties props;
    
    private ConfigUtil() throws FileNotFoundException, IOException {
        props = new Properties();
        FileInputStream fis = new FileInputStream(Constants.CONFIG_PATH);
        props.load(fis);
    }
    
    public static ConfigUtil getInstance() throws FileNotFoundException, IOException{
        if (instance == null) instance = new ConfigUtil();
        return instance;
    }

    public int getPort() throws NumberFormatException{
        return Integer.parseInt(props.getProperty(Constants.PORT));
    }
    
    public String getHost(){
        return props.getProperty(Constants.HOST);
    }

    public void setPort(int portNum) {
        props.setProperty(Constants.PORT, String.valueOf(portNum));
    }

    public void setHost(String host) {
        props.setProperty(Constants.HOST, host);
    }

    public void flush() throws FileNotFoundException, IOException {
        props.store(new FileOutputStream(Constants.CONFIG_PATH), "");
    }
}

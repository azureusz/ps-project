/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import constants.Constants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Milos
 */
public class Config {
    Properties properties;

    public Config() throws Exception {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(Constants.CONFIG_PATH);
        properties.load(fis);
    }
    
    public String getUrl(){
        return properties.getProperty(Constants.URL);
    }
    
    public String getUsername(){
        return properties.getProperty(Constants.USERNAME);
    }
    
    public String getPassword(){
        return properties.getProperty(Constants.PASSWORD);
    }
    
    public int getPort() throws NumberFormatException{
        return Integer.parseInt(properties.getProperty(Constants.PORT));
    }
    
    public void setUrl(String url){
        properties.setProperty(Constants.URL, url);
    }
    
    public void setUsername(String username){
        properties.setProperty(Constants.USERNAME, username);
    }
    
    public void setPassword(String password){
        properties.setProperty(Constants.PASSWORD, password);
    }
    
    public void setPort(int port){
        properties.setProperty(Constants.PORT, String.valueOf(port));
    }
    
    public void saveConfiguration() throws Exception{
        FileOutputStream fos = new FileOutputStream(Constants.CONFIG_PATH);
        properties.store(fos, "");
        fos.close();
    }

    public boolean isValid() {
        try{
            getPort();
        }catch( NumberFormatException e){
            return false;
        }
        if(getUrl().isEmpty() || getUsername().isEmpty()) return false;
        return true;
    }
}

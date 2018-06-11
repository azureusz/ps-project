/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Milos
 */
public class DatabaseConnection {
    private final Connection connection;
    private static DatabaseConnection instance;
    
    private DatabaseConnection() throws Exception{

        Config dbr = new Config();
        connection = DriverManager.getConnection(dbr.getUrl(), dbr.getUsername(), dbr.getPassword());
        System.out.println("Db connection successful!");
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static DatabaseConnection getInstance() throws Exception{
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

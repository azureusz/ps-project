/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domain.general.IDomainEntity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Milos
 */
public class DatabaseRepository {

    public IDomainEntity save(IDomainEntity ide) throws Exception{
        Connection connection = DatabaseConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(ide.getTableName())
                .append("(")
                .append(ide.getColumnNamesForInsert())
                .append(")")
                .append(" VALUES ")
                .append("(")
                .append(ide.getColumnValuesForInsert())
                .append(")");
        
        String query = sb.toString();
        Statement s = connection.createStatement();
        s.executeUpdate(query);
        
        if(ide.isIdAutoincrement()){
            ResultSet rs = s.getGeneratedKeys();
            Long id = rs.getLong(1);
            ide.setAutoincrementId(id);
        }
        return findById(ide);
        

    }
    
    public void startTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
    }
    
    public void commitTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().commit();
    }
    
    public void rollbackTransaction() throws Exception{
        DatabaseConnection.getInstance().getConnection().rollback();
    }

    private IDomainEntity findById(IDomainEntity ide) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

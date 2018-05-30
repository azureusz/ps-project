/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domain.general.IDomainEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Milos
 */
public class DatabaseRepository {

    public IDomainEntity save(IDomainEntity ide) throws Exception {
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
        System.out.println("Save: " + query);
        if (ide.isIdAutoincrement()) {
            s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            Long id = rs.getLong(1);
            ide.setAutoincrementId(id);
        } else {
            s.executeUpdate(query);
        }
        
        return findById(ide);
    }
    
    public boolean update(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE " + ide.getTableName() + " SET " + ide.getColumnValuesForUpdate() + " WHERE " + ide.getWhereCondition();
        Statement st = connection.prepareStatement(query);
        int count = st.executeUpdate(query);
        return count > 0;
    }
    
    public boolean delete(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM " + ide.getTableName()+ " WHERE " + ide.getWhereCondition();
        Statement st = connection.prepareStatement(query);
        int count = st.executeUpdate(query);
        return count > 0;
    }

    public void startTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
    }

    public void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().commit();
    }

    public void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().rollback();
    }

    private IDomainEntity findById(IDomainEntity ide) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM " + ide.getTableName() + " WHERE " + ide.getWhereCondition();
        System.out.println("Find: " + query);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        return rs.next() ? ide.getNewRecord(rs) : null;
    }

}

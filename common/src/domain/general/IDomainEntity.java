/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.general;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Milos
 */
public interface IDomainEntity {
    
    public String getColumnNamesForInsert();
    
    public String getColumnValuesForInsert();

    public String getTableName();

    public String getWhereCondition();
    
    public IDomainEntity getNewRecord(ResultSet rs) throws SQLException;
    
    public boolean isIdAutoincrement();

    public void setAutoincrementId(Long id);
}

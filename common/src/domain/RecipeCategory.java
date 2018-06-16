/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.general.IDomainEntity;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Milos
 */
public class RecipeCategory implements IDomainEntity, Serializable{
    
    private Long id;
    private String name;

    public RecipeCategory() {
        id = 0l;
        name = "example category";
    }

    public RecipeCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getColumnNamesForInsert() {
        return "id, naziv";
    }

    @Override
    public String getColumnValuesForInsert() {
        return id + ", '" + name + "'";
    }

    @Override
    public String getTableName() {
        return "kategorija_recepta";
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    @Override
    public IDomainEntity getNewRecord(ResultSet rs) throws SQLException {
        return new RecipeCategory(rs.getLong("id"),rs.getString("naziv"));
    }

    @Override
    public boolean isIdAutoincrement() {
        return true;
    }

    @Override
    public void setAutoincrementId(Long id) {
        this.id = id;
    }

    @Override
    public String getColumnValuesForUpdate() {
        return "id = " + id + ", naziv = '" + name + "'";
    }
}

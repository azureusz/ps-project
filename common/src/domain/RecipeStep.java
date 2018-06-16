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
public class RecipeStep implements IDomainEntity, Serializable{

    private Long id;
    private Long recipeId;
    private String text;

    public RecipeStep() {
        id = 0l;
        recipeId = 0l;
        text = "sample text";
    }

    public RecipeStep(Long id, Long recipeId, String text) {
        this.id = id;
        this.recipeId = recipeId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String getColumnNamesForInsert() {
        return "id, recept_id, tekst";
    }

    @Override
    public String getColumnValuesForInsert() {
        return id + ", " + recipeId + ", '" + text + "'";
    }

    @Override
    public String getTableName() {
        return "stavka_recepta";
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id + " AND recept_id = " + recipeId;
    }

    @Override
    public IDomainEntity getNewRecord(ResultSet rs) throws SQLException {
        return new RecipeStep(rs.getLong("id"), rs.getLong("recept_id"), rs.getString("tekst"));
    }

    @Override
    public boolean isIdAutoincrement() {
        return false;
    }

    @Override
    public void setAutoincrementId(Long id) {
        this.id = id;
    }

    @Override
    public String getColumnValuesForUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return text;
    }
}

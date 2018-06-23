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
import java.util.Objects;

/**
 *
 * @author Milos
 */
public class IngredientOfRecipe implements IDomainEntity, Serializable{
    
    private Long recipeId;
    private Ingredient ingredient;
    private int amount;
    private MeasureUnit measureUnit;

    public IngredientOfRecipe() {
        recipeId = 0l;
        ingredient = new Ingredient();
        amount = 0;
        measureUnit = new MeasureUnit();
    }

    public IngredientOfRecipe(Long recipeId, Ingredient ingredient, int amount, MeasureUnit measureUnit) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.amount = amount;
        this.measureUnit = measureUnit;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    @Override
    public String getColumnNamesForInsert() {
        return "recept_id, sastojak_id, kolicina, merna_jedinica_id";
    }

    @Override
    public String getColumnValuesForInsert() {
        return recipeId + ", " + ingredient.getId() + ", " + amount + ", " + measureUnit.getId();
    }

    @Override
    public String getTableName() {
        return "sastojak_recepta";
    }

    @Override
    public String getWhereCondition() {
        return "recept_id = " + recipeId + " AND sastojak_id = " + ingredient.getId();
    }

    @Override
    public IDomainEntity getNewRecord(ResultSet rs) throws SQLException {
        return new IngredientOfRecipe(rs.getLong("recept_id"),
                new Ingredient(rs.getLong("sastojak_id"), ""), rs.getInt("kolicina"),
                new MeasureUnit(rs.getLong("merna_jedinica_id"), ""));
    }

    @Override
    public boolean isIdAutoincrement() {
        return false;
    }

    @Override
    public void setAutoincrementId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnValuesForUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return ingredient + ": " + amount + " " + measureUnit;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IngredientOfRecipe other = (IngredientOfRecipe) obj;
        if (!Objects.equals(this.ingredient, other.ingredient)) {
            return false;
        }
        if (!Objects.equals(this.measureUnit, other.measureUnit)) {
            return false;
        }
        return true;
    }
    
    
    
}

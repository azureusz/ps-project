/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.general.IDomainEntity;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milos
 */
public class Recipe implements IDomainEntity{

    private Long id;
    private LocalDate dateCreated;
    private String title;
    private int timeRequired;
    private RecipeCategory category;
    private List<IngredientOfRecipe> ingredients;
    private List<RecipeStep> steps;

    public Recipe() {
        id = 0l;
        dateCreated = LocalDate.now();
        title = "sample recipe title";
        timeRequired = 0;
        category = new RecipeCategory();
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public Recipe(Long id, LocalDate dateCreated, String title, int timeRequired, RecipeCategory category, List<IngredientOfRecipe> ingredients, List<RecipeStep> steps) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.title = title;
        this.timeRequired = timeRequired;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(Long id, LocalDate dateCreated, String title, int timeRequired, RecipeCategory category) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.title = title;
        this.timeRequired = timeRequired;
        this.category = category;
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }
    
    
    
    @Override
    public String getColumnNamesForInsert() {
        return "id, datum, naziv, potrebno_vreme, kategorija_id";
    }

    @Override
    public String getColumnValuesForInsert() {
        return id + ", '" + Date.valueOf(dateCreated) + "', '" + title + "', " + timeRequired + ", " + category.getId();
    }

    @Override
    public String getTableName() {
        return "recept";
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    @Override
    public IDomainEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Recipe(rs.getLong("id"),
                rs.getDate("datum").toLocalDate(), rs.getString("naziv"),
                rs.getInt("potrebno_vreme"),
                new RecipeCategory(rs.getLong("kategorija_id"), ""));
    }

    @Override
    public boolean isIdAutoincrement() {
        return true;
    }

    @Override
    public void setAutoincrementId(Long id) {
        this.id = id;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.general.IDomainEntity;
import java.io.Serializable;
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
public class Recipe implements IDomainEntity, Serializable{

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public List<IngredientOfRecipe> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientOfRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    @Override
    public String getColumnValuesForUpdate() {
        return "id = " + id + ", datum = '" + Date.valueOf(dateCreated) + "', naziv = '" + title + "', potrebno_vreme = " + timeRequired + ", kategorija_id = " + category.getId();
    
    }
    
    
    
}

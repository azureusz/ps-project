/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.recipe;

import domain.Recipe;
import domain.RecipeCategory;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milos
 */
public class RecipesTableModel extends AbstractTableModel{
    
    private List<Recipe> recipes;
    private String[] colNames = new String[]{"No.","Title","Time required","Date created","Category"};
    private String searchTitle = "";
    private int maxPrepareTime = Integer.MAX_VALUE;
    private RecipeCategory searchCategory = new RecipeCategory(0l, "--All categories--");

    public RecipesTableModel(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public int getRowCount() {
        return getVisibleRecipes().size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) return rowIndex + 1;
        Recipe recipe = getVisibleRecipes().get(rowIndex);
        
        switch(columnIndex){
            case 1:
                return recipe.getTitle();
            case 2:
                return recipe.getTimeRequired();
            case 3:
                return recipe.getDateCreated();
            case 4:
                return recipe.getCategory().getName();
            default:
                return "N/A";
        }
    }
    
    public List<Recipe> getVisibleRecipes(){
        if(searchTitle.isEmpty() && searchCategory.getName().equals("--All categories--") && maxPrepareTime == Integer.MAX_VALUE) return recipes;
        
        return recipes.stream()
                .filter((Recipe t) -> {
                    if(searchCategory.getName().equals("--All categories--") && t.getTitle().toLowerCase().contains(searchTitle.toLowerCase()) && t.getTimeRequired() <= maxPrepareTime)
                        return true;
                    if(t.getCategory().equals(searchCategory) && t.getTitle().toLowerCase().contains(searchTitle.toLowerCase()) && t.getTimeRequired() <= maxPrepareTime)
                        return true;
                    return false;
                }).collect(Collectors.toList());
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
        fireTableDataChanged();
    }

    public void setMaxPrepareTime(int maxPrepareTime) {
        this.maxPrepareTime = maxPrepareTime;
        fireTableDataChanged();
    }

    public void setSearchCategory(RecipeCategory searchCategory) {
        this.searchCategory = searchCategory;
        fireTableDataChanged();
    }

    void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        fireTableDataChanged();
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.ingredient;

import domain.Ingredient;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milos
 */
public class IngredientsTableModel extends AbstractTableModel{

    private List<Ingredient> ingredients;
    private String searchTerm = "";

    public IngredientsTableModel(List<Ingredient> categories) {
        this.ingredients = categories;
    }
    
    @Override
    public int getRowCount() {
        return getVisibleIngredients().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0){
            return "No.";
        }else{
            return "Name";
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) return (rowIndex + 1) + ".";
        return getVisibleIngredients().get(rowIndex);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        fireTableDataChanged();
    }
    
    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
        fireTableDataChanged();
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        fireTableDataChanged();
    }

    public List<Ingredient> getVisibleIngredients() {
        if(this.searchTerm.isEmpty()) return this.ingredients;
        
        return this.ingredients.stream()
                .filter((Ingredient t) -> {
                    return t.getName().toLowerCase().contains(this.searchTerm.toLowerCase());
                }).collect(Collectors.toList());
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.category;

import domain.RecipeCategory;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milos
 */
public class CategoriesTableModel extends AbstractTableModel{

    private List<RecipeCategory> categories;
    private String searchTerm = "";

    public CategoriesTableModel(List<RecipeCategory> categories) {
        this.categories = categories;
    }
    
    @Override
    public int getRowCount() {
        return getVisibleCategories().size();
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
        return getVisibleCategories().get(rowIndex);
    }

    public List<RecipeCategory> getCategories() {
        return categories;
    }

    void addCategory(RecipeCategory recipeCategory) {
        categories.add(recipeCategory);
        fireTableDataChanged();
    }
    
    void removeCategory(RecipeCategory category){
        categories.remove(category);
        fireTableDataChanged();
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        fireTableDataChanged();
    }

    public List<RecipeCategory> getVisibleCategories() {
        if(this.searchTerm.isEmpty()) return this.categories;
        
        return this.categories.stream()
                .filter((RecipeCategory t) -> {
                    return t.getName().toLowerCase().contains(this.searchTerm.toLowerCase());
                }).collect(Collectors.toList());
    }
    
    
    
}

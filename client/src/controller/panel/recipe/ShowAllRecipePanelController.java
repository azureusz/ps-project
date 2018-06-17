/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.recipe;

import controller.panel.category.CategoriesTableModel;
import controller.panel.general.AbstractPanelController;
import domain.IngredientOfRecipe;
import domain.Recipe;
import domain.RecipeCategory;
import domain.RecipeStep;
import form.FMain;
import form.panel.recipe.ShowAllRecipePanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author Milos
 */
public class ShowAllRecipePanelController extends AbstractPanelController {

    ShowAllRecipePanel panel;

    public ShowAllRecipePanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }

    @Override
    public void initPanel(JPanel panel) {
        this.panel = (ShowAllRecipePanel) panel;
        addActionListeners();
        initTable();
        populateCbCategories();
        super.initPanel(panel); //To change body of generated methods, choose Tools | Templates.
    }

    private void addActionListeners() {
        addTableClickListener();
        addCbCategoriesActionListener();
        addSearchTitleActionListener();
        addSearchMaxTimeActionListener();
        addRemoveRecipeActionListener();
    }

    private void initTable() {
        try {
            List<Recipe> recipes = (List<Recipe>) performRequest(new RequestObject(IOperation.LOAD_ALL_RECIPES, null)).getData();
            this.panel.getJtblRecipes().setModel(new RecipesTableModel(recipes));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
        }
    }

    private void populateCbCategories() {
        try {
            List<RecipeCategory> categories = (List<RecipeCategory>) performRequest(new RequestObject(IOperation.LOAD_ALL_RECIPE_CATEGORIES, null)).getData();
            panel.getJcbSearchCategory().addItem(new RecipeCategory(0l, "--All categories--"));
            for (RecipeCategory category : categories) {
                panel.getJcbSearchCategory().addItem(category);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
        }
    }

    private void addTableClickListener() {
        this.panel.getJtblRecipes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    RecipesTableModel rtm = (RecipesTableModel) panel.getJtblRecipes().getModel();
                    Recipe recipe = rtm.getVisibleRecipes().get(row);
                    panel.getJlblSelectedRecipe().setText(recipe.getTitle());
                    renderIngredients(recipe);
                    renderSteps(recipe);
                }
            }

        });
    }

    private void renderIngredients(Recipe recipe) {
        int i = 1;
        panel.getJtxtAreaIngredients().setText("");
        for (IngredientOfRecipe ingredient : recipe.getIngredients()) {
            panel.getJtxtAreaIngredients().append(i++ + ". " + ingredient.getIngredient() + ": " + ingredient.getAmount() + " " + ingredient.getMeasureUnit() + "\n");
        }
    }

    private void renderSteps(Recipe recipe) {
        int i = 1;
        panel.getJtxtAreaSteps().setText("");
        for (RecipeStep step : recipe.getSteps()) {
            panel.getJtxtAreaSteps().append(i++ + ". " + step + "\n");
        }
    }

    private void addCbCategoriesActionListener() {
        panel.getJcbSearchCategory().addActionListener((e) -> {
            ((RecipesTableModel) panel.getJtblRecipes().getModel()).setSearchCategory((RecipeCategory) panel.getJcbSearchCategory().getSelectedItem());
        });
    }

    private void addSearchTitleActionListener() {
        this.panel.getJtxtSearchTitle().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                if (!panel.getJtxtSearchTitle().getText().isEmpty()) {
                    ((RecipesTableModel) panel.getJtblRecipes().getModel()).setSearchTitle(panel.getJtxtSearchTitle().getText());
                } else {
                    ((RecipesTableModel) panel.getJtblRecipes().getModel()).setSearchTitle("");
                }
            }
        });
    }

    private void addSearchMaxTimeActionListener() {
        panel.getJcbMaxTime().addActionListener((e) -> {
            String selected = (String) panel.getJcbMaxTime().getSelectedItem();
            if(selected.equals("--None--")){
                ((RecipesTableModel) panel.getJtblRecipes().getModel()).setMaxPrepareTime(Integer.MAX_VALUE);
            }else{
                ((RecipesTableModel) panel.getJtblRecipes().getModel()).setMaxPrepareTime(Integer.parseInt(selected.split(" ")[0]));
            }
        });
    }

    private void addRemoveRecipeActionListener() {
        this.panel.getJbtnRemoveRecipe().addActionListener((e) -> {
            int row = this.panel.getJtblRecipes().getSelectedRow();
            if (row != -1) {
                RecipesTableModel tm = (RecipesTableModel) this.panel.getJtblRecipes().getModel();
                Recipe recipe = tm.getVisibleRecipes().get(row);
                int answer = JOptionPane.showConfirmDialog(panel, "Are you sure you want to remove \"" + recipe.getTitle() + "\"?");
                if (answer == JOptionPane.YES_OPTION) {
                    try {
                        ResponseObject ro = performRequest(new RequestObject(IOperation.DELETE_RECIPE, recipe));
                        if (ro.getCode() == IStatus.OK) {
                            tm.removeRecipe(recipe);
                            panel.getJtxtSearchTitle().setText("");
                            panel.getJcbMaxTime().setSelectedIndex(0);
                            panel.getJcbSearchCategory().setSelectedIndex(0);
                        }
                        JOptionPane.showMessageDialog(panel, ro.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a recipe to remove.");
            }
        });
    }

}

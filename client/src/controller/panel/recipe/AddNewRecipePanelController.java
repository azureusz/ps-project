/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.recipe;

import controller.panel.general.AbstractPanelController;
import domain.Ingredient;
import domain.IngredientOfRecipe;
import domain.MeasureUnit;
import domain.Recipe;
import domain.RecipeCategory;
import domain.RecipeStep;
import form.FMain;
import form.panel.recipe.AddNewRecipePanel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author Milos
 */
public class AddNewRecipePanelController extends AbstractPanelController {

    Recipe recipe;
    AddNewRecipePanel panel;

    public AddNewRecipePanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }

    @Override
    public void initPanel(JPanel panel) {
        this.recipe = new Recipe();
        this.panel = (AddNewRecipePanel) panel;
        addActionListeners();
        populateCbs();
        super.initPanel(panel); //To change body of generated methods, choose Tools | Templates.
    }

    private void addActionListeners() {
        addIngredientActionListeners();
        addStepsActionListeners();
        addSaveRecipeActionListener();
    }

    private void populateCbs() {
        populateCategories();
        populateIngredients();
        populateMeasureUnits();
    }

    private void addSaveRecipeActionListener() {
        this.panel.getJbtnSaveRecipe().addActionListener((e) -> {
            recipe.setTitle(panel.getJtxtRecipeTitle().getText());
            recipe.setDateCreated(LocalDate.now());
            recipe.setCategory((RecipeCategory) panel.getJcbRecipeCategory().getSelectedItem());
            try {
                int time = Integer.parseInt(panel.getJtxtPreparingTime().getText());
                recipe.setTimeRequired(time);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(panel, "Preparing time must be a number.");
                return;
            }
            Long i = 1l;
            for (RecipeStep step : recipe.getSteps()) {
                step.setId(i++);
            }
            if (validateRecipe()) {
                try {
                    out.writeObject(new RequestObject(IOperation.SAVE_RECIPE, recipe));
                    ResponseObject ro = (ResponseObject) in.readObject();
                    JOptionPane.showMessageDialog(panel, ro.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AddNewRecipePanelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addIngredientActionListeners() {
        this.panel.getJbtnAddIngredient().addActionListener((e) -> {
            int quantity;
            try {
                quantity = Integer.parseInt(panel.getJtxtQuantity().getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(panel, "Please input a valid quantity.");
                return;
            }
            Ingredient ingredient = (Ingredient) panel.getJcbIngredients().getSelectedItem();
            MeasureUnit unit = (MeasureUnit) panel.getJcbMeasureUnits().getSelectedItem();
            IngredientOfRecipe ior = new IngredientOfRecipe(0l, ingredient, quantity, unit);
            if (recipe.getIngredients().contains(ior)) {
                int option = JOptionPane.showConfirmDialog(panel,
                        "The ingredient is already added. Do you want to add " + quantity + unit + " more to " + ingredient + "?");
                if(option == JOptionPane.YES_OPTION){
                    for (IngredientOfRecipe ingredient1 : recipe.getIngredients()) {
                        if(ingredient1.equals(ior)){
                            ingredient1.setAmount(ingredient1.getAmount() + ior.getAmount());
                        }
                    }
                }else return;
                
            } else {
                recipe.getIngredients().add(ior);
            }
            rerenderIngredients();
        });

        this.panel.getJbtnRemoveLastIngredient().addActionListener((e) -> {
            if (recipe.getIngredients().size() != 0) {
                recipe.getIngredients().remove(recipe.getIngredients().size() - 1);
                rerenderIngredients();
            }
        });
    }

    private void addStepsActionListeners() {
        this.panel.getJbtnAddStep().addActionListener((e) -> {
            String text = panel.getJtxtAreaStep().getText();
            if (text == null || text.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Step text can't be empty.");
                return;
            }
            panel.getJtxtAreaStep().setText("");
            recipe.getSteps().add(new RecipeStep(0l, 0l, text));
            rerenderSteps();
        });

        this.panel.getJbtnRemoveLastStep().addActionListener((e) -> {
            if (recipe.getSteps().size() != 0) {
                recipe.getSteps().remove(recipe.getSteps().size() - 1);
                rerenderSteps();
            }
        });
    }

    private void rerenderIngredients() {
        this.panel.getJtxtAreaIngredients().setText("");
        int i = 1;
        for (IngredientOfRecipe ingredient : recipe.getIngredients()) {
            this.panel.getJtxtAreaIngredients().append(i++ + ". " + ingredient + "\n");
        }
    }

    private void populateCategories() {
        try {
            out.writeObject(new RequestObject(IOperation.LOAD_ALL_RECIPE_CATEGORIES, null));
            ResponseObject res = (ResponseObject) in.readObject();
            if (res.getCode() == IStatus.OK) {
                List<RecipeCategory> categories = (List<RecipeCategory>) res.getData();
                for (RecipeCategory category : categories) {
                    this.panel.getJcbRecipeCategory().addItem(category);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Could not load recipe categories from server.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddNewRecipePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateMeasureUnits() {
        try {
            out.writeObject(new RequestObject(IOperation.LOAD_ALL_MEASURE_UNITS, null));
            ResponseObject res = (ResponseObject) in.readObject();
            if (res.getCode() == IStatus.OK) {
                List<MeasureUnit> measureUnits = (List<MeasureUnit>) res.getData();
                for (MeasureUnit measureUnit : measureUnits) {
                    this.panel.getJcbMeasureUnits().addItem(measureUnit);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Could not load measure units from server.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddNewRecipePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateIngredients() {
        try {
            out.writeObject(new RequestObject(IOperation.LOAD_ALL_INGREDIENTS, null));
            ResponseObject res = (ResponseObject) in.readObject();
            if (res.getCode() == IStatus.OK) {
                List<Ingredient> ingredients = (List<Ingredient>) res.getData();
                for (Ingredient ingredient : ingredients) {
                    this.panel.getJcbIngredients().addItem(ingredient);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Could not load ingredients from server.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddNewRecipePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rerenderSteps() {
        this.panel.getJtxtAreaAllSteps().setText("");
        int i = 1;
        for (RecipeStep step : recipe.getSteps()) {
            this.panel.getJtxtAreaAllSteps().append(i++ + ". " + step + "\n");
        }
    }

    private boolean validateRecipe() {
        String errors = "";
        if (recipe.getTitle().isEmpty()) {
            errors += "Title can't be empty.";
        }
        if (recipe.getSteps().size() == 0) {
            errors += "Recipe must have at least one step.\n";
        }
        if (recipe.getIngredients().size() == 0) {
            errors += "Recipe must have at least one ingredient.\n";
        }
        if (recipe.getTimeRequired() == 0) {
            errors += "Time required for preparing can't be 0 minutes.";
        }
        if (errors.isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(panel, errors);
            return false;
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import domain.Ingredient;
import domain.IngredientOfRecipe;
import domain.MeasureUnit;
import domain.Recipe;
import domain.RecipeCategory;
import domain.RecipeStep;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.DeleteRecipeCategory;
import so.SaveRecipe;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class Start {

    public static void main(String[] args) {
        //JFrame form = new FServer();
        //form.setVisible(true);
        testSaveRecipe();
        //testDelete();
        
    }

    private static void testSaveRecipe() {
        try {

            RecipeCategory rc = new RecipeCategory(1l, "Kolaci");
            Recipe recipe = new Recipe(0l, LocalDate.now(), "New recipe", 10, rc);
            List<RecipeStep> steps = new LinkedList<>();
            steps.add(new RecipeStep(1l, 0l, "Do this"));
            steps.add(new RecipeStep(2l, 0l, "Do that"));
            recipe.setSteps(steps);
            List<IngredientOfRecipe> ing = new LinkedList<>();
            ing.add(new IngredientOfRecipe(0l, new Ingredient(1l, "Cokolada"), 100, new MeasureUnit(1l, "g")));
            ing.add(new IngredientOfRecipe(0l, new Ingredient(2l, "Jaja"), 5, new MeasureUnit(2l, "kom")));
            recipe.setIngredients(ing);
            AbstractGenericOperation sc = new SaveRecipe();
            sc.templateExecute(recipe);
            System.out.println(recipe);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void testDelete() {
        try {
            RecipeCategory rc = new RecipeCategory(1l, "Kolaci");
            AbstractGenericOperation drc = new DeleteRecipeCategory();
            drc.templateExecute(rc);
        } catch (Exception ex) {
            
            System.out.println("error:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

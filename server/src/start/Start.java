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
import domain.general.IDomainEntity;
import form.FServer;
import form.controller.FormController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import so.DeleteRecipe;
import so.LoadAllRecipes;
import so.SaveRecipe;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class Start {

    public static void main(String[] args) {
        FormController fc = new FormController(new FServer());
        fc.initForm();
        //testSaveRecipe();
        //testDelete();
        //testLoadAll();
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
            Recipe recipe = new Recipe(2l, LocalDate.now(), "New recipe", 10, new RecipeCategory());
            AbstractGenericOperation drc = new DeleteRecipe();
            drc.templateExecute(recipe);
        } catch (Exception ex) {
            
            System.out.println("error:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void testLoadAll() {
        try {
            List<IDomainEntity> recipes = new ArrayList<>();
            AbstractGenericOperation drc = new LoadAllRecipes();
            drc.templateExecute(new Recipe(),recipes);
        } catch (Exception ex) {
            
            System.out.println("error:" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Ingredient;
import domain.IngredientOfRecipe;
import domain.MeasureUnit;
import domain.Recipe;
import domain.RecipeCategory;
import domain.RecipeStep;
import domain.general.IDomainEntity;
import java.util.List;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class LoadAllRecipes extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void execute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception {
        db.findAll(ide, ides);
        
        for (IDomainEntity ide1 : ides) {
            Recipe recipe = (Recipe)ide1;
            recipe.setCategory((RecipeCategory) db.findById(recipe.getCategory()));
            loadSteps(recipe);
            loadIngredients(recipe);
        }
        
    }

    private void loadSteps(Recipe recipe) throws Exception {
        List<RecipeStep> steps = (List<RecipeStep>)(List<?>)db.findAllCustom(new RecipeStep(), "recept_id=" + recipe.getId());
        recipe.setSteps(steps);
    }

    private void loadIngredients(Recipe recipe) throws Exception {
        List<IngredientOfRecipe> ingredients = (List<IngredientOfRecipe>)(List<?>)db.findAllCustom(new IngredientOfRecipe(), "recept_id=" + recipe.getId());
        for (IngredientOfRecipe ingredient : ingredients) {
            ingredient.setIngredient((Ingredient) db.findById(ingredient.getIngredient()));
            ingredient.setMeasureUnit((MeasureUnit) db.findById(ingredient.getMeasureUnit()));
        }
        recipe.setIngredients(ingredients);
    }
    
}

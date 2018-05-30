/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.IngredientOfRecipe;
import domain.Recipe;
import domain.RecipeStep;
import domain.general.IDomainEntity;
import java.util.List;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class SaveRecipe extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
        Recipe recipe = (Recipe) ide;
        String errors = "";
        
        if(recipe.getTimeRequired() == 0) errors += "Required time can't be 0.\n";
        if(recipe.getTitle().isEmpty()) errors += "Recipe title can't be empty.";
        if(recipe.getSteps().size() == 0) errors += "Recipe must have at least one step.";
        if(!errors.isEmpty()) throw new Exception(errors);
        
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        Recipe recipe = (Recipe) ide;
        Recipe savedRecipe = (Recipe) db.save(recipe);
        
        for (IngredientOfRecipe ingredient : recipe.getIngredients()) {
            ingredient.setRecipeId(savedRecipe.getId());
            db.save(ingredient);
        }
        
        for (RecipeStep step : recipe.getSteps()) {
            step.setRecipeId(savedRecipe.getId());
            db.save(step);
        }
        
        recipe.setId(savedRecipe.getId());
    }

    @Override
    protected void execute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

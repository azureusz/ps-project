/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import domain.Ingredient;
import domain.MeasureUnit;
import domain.Recipe;
import domain.RecipeCategory;
import domain.general.IDomainEntity;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.DeleteIngredient;
import so.DeleteRecipe;
import so.DeleteRecipeCategory;
import so.LoadAllIngredients;
import so.LoadAllMeasureUnits;
import so.LoadAllRecipeCategories;
import so.LoadAllRecipes;
import so.SaveIngredient;
import so.SaveRecipe;
import so.SaveRecipeCategory;
import so.general.AbstractGenericOperation;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author Milos
 */
public class ThreadClient extends Thread {

    Socket socket;

    public ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            while (!isInterrupted()) {
                try {

                    RequestObject request = (RequestObject) input.readObject();
                    ResponseObject responseObject = new ResponseObject();
                    switch (request.getOperation()) {
                        case IOperation.SAVE_RECIPE:
                            Recipe recipe = (Recipe) request.getData();
                            try {
                                AbstractGenericOperation saveRecipe = new SaveRecipe();
                                saveRecipe.templateExecute(recipe);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipe saved succesfully!");
                                responseObject.setData(recipe);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.SAVE_INGREDIENT:
                            Ingredient ingredient = (Ingredient) request.getData();
                            try {
                                AbstractGenericOperation saveIngredient = new SaveIngredient();
                                saveIngredient.templateExecute(ingredient);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Ingredient saved succesfully!");
                                responseObject.setData(ingredient);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.SAVE_RECIPE_CATEGORY:
                            RecipeCategory category = (RecipeCategory) request.getData();
                            try {
                                AbstractGenericOperation saveCategory = new SaveRecipeCategory();
                                saveCategory.templateExecute(category);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipe category saved succesfully!");
                                responseObject.setData(category);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.LOAD_ALL_RECIPES:
                            try {
                                List<IDomainEntity> recipes = new ArrayList<>();
                                AbstractGenericOperation loadAll = new LoadAllRecipes();
                                loadAll.templateExecute(new Recipe(), recipes);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipes loaded succesfully!");
                                responseObject.setData(recipes);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.LOAD_ALL_INGREDIENTS:
                            try {
                                List<IDomainEntity> ingredients = new ArrayList<>();
                                AbstractGenericOperation loadAll = new LoadAllIngredients();
                                loadAll.templateExecute(new Ingredient(), ingredients);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Ingredients loaded succesfully!");
                                responseObject.setData(ingredients);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;
                            
                        case IOperation.LOAD_ALL_MEASURE_UNITS:
                            try {
                                List<IDomainEntity> measureUnits = new ArrayList<>();
                                AbstractGenericOperation loadAll = new LoadAllMeasureUnits();
                                loadAll.templateExecute(new MeasureUnit(), measureUnits);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Measure units loaded succesfully!");
                                responseObject.setData(measureUnits);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;
                            
                        case IOperation.LOAD_ALL_RECIPE_CATEGORIES:
                            try {
                                List<IDomainEntity> categories = new ArrayList<>();
                                AbstractGenericOperation loadAll = new LoadAllRecipeCategories();
                                loadAll.templateExecute(new RecipeCategory(), categories);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipe categories loaded succesfully!");
                                responseObject.setData(categories);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.DELETE_RECIPE:
                            Recipe delRecipe = (Recipe) request.getData();
                            try {
                                AbstractGenericOperation deleteRecipe = new DeleteRecipe();
                                deleteRecipe.templateExecute(delRecipe);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipe deleted succesfully!");
                                responseObject.setData(delRecipe);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.DELETE_INGREDIENT:
                            Ingredient delIngredient = (Ingredient) request.getData();
                            try {
                                AbstractGenericOperation deleteIngredient = new DeleteIngredient();
                                deleteIngredient.templateExecute(delIngredient);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Ingredient deleted succesfully!");
                                responseObject.setData(delIngredient);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                        case IOperation.DELETE_RECIPE_CATEGORY:
                            RecipeCategory delRecipeCategory = (RecipeCategory) request.getData();
                            try {
                                AbstractGenericOperation deleteRecipeCategory = new DeleteRecipeCategory();
                                deleteRecipeCategory.templateExecute(delRecipeCategory);
                                responseObject.setCode(IStatus.OK);
                                responseObject.setMessage("Recipe category deleted succesfully!");
                                responseObject.setData(delRecipeCategory);
                            } catch (Exception e) {
                                responseObject.setCode(IStatus.ERROR);
                                responseObject.setMessage(e.getMessage());
                            }

                            break;

                    }

                    output.writeObject(responseObject);
                    output.flush();
                } catch (Exception e) {
                    System.out.println("Client disconnected.");
                    break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

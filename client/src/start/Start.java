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
import form.FMain;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;

/**
 *
 * @author Milos
 */
public class Start {

    public static void main(String[] args) {
//        JFrame fmain = new FMain();
//        fmain.setVisible(true);  
        testSaveRecipe();
    }

    private static void testSaveRecipe() {
        try {
            Socket s = new Socket("localhost", 9009);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

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

            RequestObject req = new RequestObject(IOperation.SAVE_RECIPE, recipe);
            out.writeObject(req);
            out.flush();

            ResponseObject res = (ResponseObject) in.readObject();

            System.out.println(res.getCode() + res.getMessage());
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

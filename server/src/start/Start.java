/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import db.DatabaseRepository;
import domain.RecipeCategory;
import form.FServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import so.SaveRecipeCategory;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class Start {

    public static void main(String[] args) {
        try {
            //JFrame form = new FServer();
            //form.setVisible(true);
            RecipeCategory rc = new RecipeCategory(0l, "New category");
            AbstractGenericOperation sc = new SaveRecipeCategory();
            sc.templateExecute(rc);
            System.out.println(rc);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

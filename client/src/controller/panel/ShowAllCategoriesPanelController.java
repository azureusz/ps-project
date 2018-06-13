/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel;

import controller.panel.general.AbstractPanelController;
import domain.RecipeCategory;
import form.FMain;
import form.panel.category.ShowAllCategoriesPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;

/**
 *
 * @author Milos
 */
public class ShowAllCategoriesPanelController extends AbstractPanelController{
    
    ShowAllCategoriesPanel panel;

    public ShowAllCategoriesPanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }
   
    @Override
    public void initPanel(JPanel panel) {
        this.panel = (ShowAllCategoriesPanel) panel;
        setActionListeners();
        super.initPanel(panel); //To change body of generated methods, choose Tools | Templates.
    }

    private void setActionListeners() {
        this.panel.getJbtnAddCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = panel.getjTxtCategoryName().getText();
                    out.writeObject(new RequestObject(IOperation.SAVE_RECIPE_CATEGORY, new RecipeCategory(0l, name)));
                    out.flush();
                    ResponseObject ro = (ResponseObject) in.readObject();
                    JOptionPane.showMessageDialog(panel, ro.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    
    
}

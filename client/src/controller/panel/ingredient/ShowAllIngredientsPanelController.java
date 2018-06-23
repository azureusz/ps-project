/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.ingredient;

import controller.panel.general.AbstractPanelController;
import domain.Ingredient;
import form.FMain;
import form.panel.ingredient.ShowAllIngredientsPanel;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
public class ShowAllIngredientsPanelController extends AbstractPanelController {

    ShowAllIngredientsPanel panel;

    public ShowAllIngredientsPanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }

    @Override
    public void initPanel(JPanel panel) {
        this.panel = (ShowAllIngredientsPanel) panel;
        setActionListeners();
        initIngredientsTable();
        super.initPanel(panel); //To change body of generated methods, choose Tools | Templates.
    }

    private void initIngredientsTable() {
        try {
            ResponseObject res = performRequest(new RequestObject(IOperation.LOAD_ALL_INGREDIENTS, null));
            this.panel.getJtblIngredients().setModel(new IngredientsTableModel((List<Ingredient>) res.getData()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
        }
    }

    private void setActionListeners() {
        setAddIngredientActionListener();
        setRemoveIngredientActionListener();
        setSearchIngredientActionListener();
    }

    private void setAddIngredientActionListener() {
        this.panel.getJbtnAddIngredient().addActionListener((e) -> {
            try {
                String name = panel.getjTxtIngredientName().getText();
                out.writeObject(new RequestObject(IOperation.SAVE_INGREDIENT, new Ingredient(0l, name)));
                ResponseObject ro = (ResponseObject) in.readObject();
                if (ro.getCode() == IStatus.OK) {
                    ((IngredientsTableModel) this.panel.getJtblIngredients().getModel()).addIngredient((Ingredient) ro.getData());
                    panel.getjTxtIngredientName().setText("");
                }
                JOptionPane.showMessageDialog(panel, ro.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
            }
        });
    }

    private void setSearchIngredientActionListener() {
        this.panel.getJtxtSearchIngredients().getDocument().addDocumentListener(new DocumentListener() {
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
                if (!panel.getJtxtSearchIngredients().getText().isEmpty()) {
                    ((IngredientsTableModel) panel.getJtblIngredients().getModel()).setSearchTerm(panel.getJtxtSearchIngredients().getText());
                } else {
                    ((IngredientsTableModel) panel.getJtblIngredients().getModel()).setSearchTerm("");
                }
            }
        });
    }

    private void setRemoveIngredientActionListener() {
        this.panel.getJbtnRemoveIngredient().addActionListener((e) -> {
            int row = this.panel.getJtblIngredients().getSelectedRow();
            if (row != -1) {
                IngredientsTableModel tm = (IngredientsTableModel) this.panel.getJtblIngredients().getModel();
                Ingredient ingredient = tm.getVisibleIngredients().get(row);
                int answer = JOptionPane.showConfirmDialog(panel, "Are you sure you want to remove \"" + ingredient + "\"?");
                if (answer == JOptionPane.YES_OPTION) {
                    try {
                        ResponseObject ro = performRequest(new RequestObject(IOperation.DELETE_INGREDIENT, ingredient));
                        if (ro.getCode() == IStatus.OK) {
                            tm.removeIngredient(ingredient);
                            panel.getJtxtSearchIngredients().setText("");
                        } else {
                            JOptionPane.showMessageDialog(panel, "You can't delete a ingredient that is in some recipes.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a ingredient to remove.");
            }
        });
    }

}

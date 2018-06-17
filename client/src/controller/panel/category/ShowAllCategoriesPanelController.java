/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.category;

import controller.panel.general.AbstractPanelController;
import domain.RecipeCategory;
import form.FMain;
import form.panel.category.ShowAllCategoriesPanel;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
public class ShowAllCategoriesPanelController extends AbstractPanelController {

    ShowAllCategoriesPanel panel;

    public ShowAllCategoriesPanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }

    @Override
    public void initPanel(JPanel panel) {
        this.panel = (ShowAllCategoriesPanel) panel;
        setActionListeners();
        initCategoriesTable();
        super.initPanel(panel); //To change body of generated methods, choose Tools | Templates.
    }

    private void initCategoriesTable() {
        try {
            ResponseObject res = performRequest(new RequestObject(IOperation.LOAD_ALL_RECIPE_CATEGORIES, null));
            this.panel.getJtblCategories().setModel(new CategoriesTableModel((List<RecipeCategory>) res.getData()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
        }
    }

    private void setActionListeners() {
        setAddCategoryActionListener();
        setRemoveCategoryActionListener();
        setSearchCategoryActionListener();
    }

    private void setAddCategoryActionListener() {
        this.panel.getJbtnAddCategory().addActionListener((e) -> {
            try {
                String name = panel.getjTxtCategoryName().getText();
                out.writeObject(new RequestObject(IOperation.SAVE_RECIPE_CATEGORY, new RecipeCategory(0l, name)));
                ResponseObject ro = (ResponseObject) in.readObject();
                if (ro.getCode() == IStatus.OK) {
                    ((CategoriesTableModel) this.panel.getJtblCategories().getModel()).addCategory((RecipeCategory) ro.getData());
                    panel.getjTxtCategoryName().setText("");
                }
                JOptionPane.showMessageDialog(panel, ro.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
            }
        });
    }

    private void setSearchCategoryActionListener() {
        this.panel.getJtxtSearchCategories().getDocument().addDocumentListener(new DocumentListener() {
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
                if (!panel.getJtxtSearchCategories().getText().isEmpty()) {
                    ((CategoriesTableModel) panel.getJtblCategories().getModel()).setSearchTerm(panel.getJtxtSearchCategories().getText());
                } else {
                    ((CategoriesTableModel) panel.getJtblCategories().getModel()).setSearchTerm("");
                }
            }
        });
    }

    private void setRemoveCategoryActionListener() {
        this.panel.getJbtnRemoveCategory().addActionListener((e) -> {
            int row = this.panel.getJtblCategories().getSelectedRow();
            if (row != -1) {
                CategoriesTableModel tm = (CategoriesTableModel) this.panel.getJtblCategories().getModel();
                RecipeCategory category = tm.getVisibleCategories().get(row);
                int answer = JOptionPane.showConfirmDialog(panel, "Are you sure you want to remove \"" + category + "\"?");
                if (answer == JOptionPane.YES_OPTION) {
                    try {
                        ResponseObject ro = performRequest(new RequestObject(IOperation.DELETE_RECIPE_CATEGORY, category));
                        if (ro.getCode() == IStatus.OK) {
                            tm.removeCategory(category);
                            panel.getJtxtSearchCategories().setText("");
                        } else {
                            JOptionPane.showMessageDialog(panel, "You can't delete a category that contains some recipes.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "There was an error during server communication.");
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a category to remove.");
            }
        });
    }

}

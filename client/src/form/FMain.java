/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Milos
 */
public class FMain extends javax.swing.JFrame {

    /**
     * Creates new form FMain
     */
    public FMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemAddNew = new javax.swing.JMenuItem();
        jMenuItemShowAllRecipes = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemShowAllIngredients = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemShowAllCategories = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1056, Short.MAX_VALUE)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        jMenu1.setText("Recipes");

        jMenuItemAddNew.setText("Add new");
        jMenu1.add(jMenuItemAddNew);

        jMenuItemShowAllRecipes.setText("Show all...");
        jMenu1.add(jMenuItemShowAllRecipes);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ingredients");

        jMenuItemShowAllIngredients.setText("Show all...");
        jMenu2.add(jMenuItemShowAllIngredients);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Categories");

        jMenuItemShowAllCategories.setText("Show all...");
        jMenu3.add(jMenuItemShowAllCategories);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAddNew;
    private javax.swing.JMenuItem jMenuItemShowAllCategories;
    private javax.swing.JMenuItem jMenuItemShowAllIngredients;
    private javax.swing.JMenuItem jMenuItemShowAllRecipes;
    private javax.swing.JPanel jPanelMain;
    // End of variables declaration//GEN-END:variables

    public JMenuItem getjMenuItemAddNew() {
        return jMenuItemAddNew;
    }

    public JPanel getjPanelMain() {
        return jPanelMain;
    }

    public JMenuItem getjMenuItemShowAllIngredients() {
        return jMenuItemShowAllIngredients;
    }

    public JMenuItem getjMenuItemShowAllRecipes() {
        return jMenuItemShowAllRecipes;
    }

    public void setjPanelMain(JPanel jPanelMain) {
        this.jPanelMain = jPanelMain;
    }

    public JMenuItem getjMenuItemShowAllCategories() {
        return jMenuItemShowAllCategories;
    }

    
    
}

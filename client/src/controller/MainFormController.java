/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import util.ConfigUtil;
import controller.panel.recipe.AddNewRecipePanelController;
import controller.panel.HomePanelController;
import controller.panel.category.ShowAllCategoriesPanelController;
import controller.panel.recipe.ShowAllRecipePanelController;
import controller.panel.general.AbstractPanelController;
import form.FMain;
import form.panel.HomePanel;
import form.panel.category.ShowAllCategoriesPanel;
import form.panel.recipe.AddNewRecipePanel;
import form.panel.recipe.ShowAllRecipePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import util.Constants;

/**
 *
 * @author Milos
 */
public class MainFormController {

    FMain form;
    AbstractPanelController panelCtrl;
    ObjectOutputStream out;
    ObjectInputStream in;

    public MainFormController() {
        form = new FMain();
    }

    public void initForm() {
        try {
            connectToServer();
            out = new ObjectOutputStream(session.Session.getInstance().getSocket().getOutputStream());
            out.flush();
            in = new ObjectInputStream(session.Session.getInstance().getSocket().getInputStream());
            setActionListeners();
            panelCtrl = new HomePanelController(form, out, in);
            panelCtrl.initPanel(new HomePanel());
            form.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setActionListeners() {
        setRecipeMenuActionListeners();
        setCategoriesMenuActionListeners();

    }

    private void setRecipeMenuActionListeners() {
        form.getjMenuItemAddNew().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCtrl = new AddNewRecipePanelController(form, out, in);
                panelCtrl.initPanel(new AddNewRecipePanel());
            }
        });

        form.getjMenuItemShowAllRecipes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCtrl = new ShowAllRecipePanelController(form, out, in);
                panelCtrl.initPanel(new ShowAllRecipePanel());
            }
        });
    }

    private void setCategoriesMenuActionListeners() {
        form.getjMenuItemShowAllCategories().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCtrl = new ShowAllCategoriesPanelController(form, out, in);
                panelCtrl.initPanel(new ShowAllCategoriesPanel());
            }
        });
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(ConfigUtil.getInstance().getHost(), ConfigUtil.getInstance().getPort());
            session.Session.getInstance().setSocket(socket);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(form, "Server config file(" + Constants.CONFIG_PATH + ") not found.");
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Could not connect to server. Check your config parameters.");
            promptForCredentials();
        }
    }

    private void promptForCredentials() {
        JTextField hostname = new JTextField();
        JTextField port = new JTextField();
        Object[] message = {
            "Hostname:", hostname,
            "Port:", port
        };
        try {
            hostname.setText(ConfigUtil.getInstance().getHost());
            String portNum = ConfigUtil.getInstance().getPort() + "";
            port.setText(portNum);
        } catch (Exception ex) {
            port.setText("");
        }
        int option = JOptionPane.showConfirmDialog(null, message, "OK", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int portNum = Integer.parseInt(port.getText());
                String host = hostname.getText();
                ConfigUtil.getInstance().setPort(portNum);
                ConfigUtil.getInstance().setHost(host);
                ConfigUtil.getInstance().flush();
                initForm();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(form, "Port must be a number!");
                promptForCredentials();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(form, "Could not write to server config file.");
            }
        } else {
            System.exit(0);
        }
    }

}

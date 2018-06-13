/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel;

import controller.panel.general.AbstractPanelController;
import form.FMain;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Milos
 */
public class ShowAllRecipePanelController extends AbstractPanelController{

    public ShowAllRecipePanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        super(form, out, in);
    }
    
}

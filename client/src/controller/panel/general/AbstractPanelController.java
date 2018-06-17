/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.panel.general;

import form.FMain;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JPanel;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;

/**
 *
 * @author Milos
 */
public abstract class AbstractPanelController {
    protected FMain form;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    public AbstractPanelController(FMain form, ObjectOutputStream out, ObjectInputStream in) {
        this.form = form;
        this.out = out;
        this.in = in;
    }

    
    public void initPanel(JPanel panel){
        setPanel(panel);
        panel.setVisible(true);
        form.pack();
        form.revalidate();
    }

    public void setPanel(JPanel panel) {
        this.form.setContentPane(panel);
    }
    
    protected ResponseObject performRequest(RequestObject ro) throws IOException, ClassNotFoundException{
        out.writeObject(ro);
        return (ResponseObject) in.readObject();
    }
    
}

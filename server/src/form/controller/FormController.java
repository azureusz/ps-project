/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import form.FServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import thread.ThreadClock;
import thread.ThreadServer;

/**
 *
 * @author Milos
 */
public class FormController {

    private FServer form;
    private Thread clock;
    private ThreadServer server;

    public FormController(FServer form) {
        this.form = form;
        addActionListeners();
        startClock();
    }

    public void initForm() {
        form.setVisible(true);
    }

    public FServer getForm() {
        return form;
    }

    public void setForm(FServer form) {
        this.form = form;
    }

    private void startClock() {
        clock = new ThreadClock(form.getjLblClock());
        clock.start();
    }

    private void addActionListeners() {
        addStartStopServerActionListeners();
    }

    private void addStartStopServerActionListeners() {
        form.getjBtnStartServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null || !server.isAlive()) {
                    try {
                        server = new ThreadServer(9009);
                        server.start();
                        JOptionPane.showMessageDialog(form, "Server started.");
                    } catch (IOException ex) {
                        Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(form, "Server has already been started.");
                }
            }
        });

        form.getjBtnStopServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.stopServer();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(form, "Could not stop the server.");

                }
            }
        });

    }

}

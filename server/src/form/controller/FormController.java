/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import db.Config;
import form.FServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import thread.ThreadClock;
import thread.ThreadServer;
import constants.Constants;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        setConfigParams();
    }

    public void initForm() {
        form.setVisible(true);
        form.getJlblStatus().setText("Server is not running.");
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
        addEditAndSaveConfigActionListeners();
        addTestDbAndPortActionListeners();
    }

    private void addStartStopServerActionListeners() {
        form.getjBtnStartServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config config = null;
                try {
                    config = new Config();
                    if (!config.isValid()) {
                        JOptionPane.showMessageDialog(form, "Config is not valid. Try testing db connection and port availability before starting the server.");
                        return;
                    }

                    Connection conn = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                    conn.close();
                    conn = null;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, "Config is not valid. Try testing db connection and port availability before starting the server.");
                    return;
                }

                if (server == null || !server.isAlive()) {
                    try {
                        server = new ThreadServer(config.getPort());
                        server.start();
                        form.getJlblStatus().setText("Server is up and running.");
                        JOptionPane.showMessageDialog(form, "Server started.");
                        form.getJbtnTestDb().setEnabled(false);
                        form.getJbtnTestPort().setEnabled(false);
                        form.getjBtnStartServer().setEnabled(false);
                        form.getjBtnStopServer().setEnabled(true);
                        form.getJbtnEdit().setEnabled(false);
                        form.getJbtnSave().setEnabled(false);
                        setConfigParams();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(form, "Port " + config.getPort() + " is already in use.");
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
                    JOptionPane.showMessageDialog(form, "Server stopped.");
                    form.getJlblStatus().setText("Server is not running.");

                    form.getJbtnTestDb().setEnabled(true);
                    form.getJbtnTestPort().setEnabled(true);
                    form.getjBtnStartServer().setEnabled(true);
                    form.getjBtnStopServer().setEnabled(false);
                    form.getJbtnEdit().setEnabled(true);
                    form.getJbtnSave().setEnabled(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(form, "Could not stop the server.");

                }
            }
        });

    }

    private void setConfigParams() {
        try {
            Config config = new Config();
            form.getJtxtDbUrl().setText(config.getUrl());
            form.getJtxtDbUser().setText(config.getUsername());
            form.getJtxtDbPass().setText(config.getPassword());
            form.getJtxtServerPort().setText(String.valueOf(config.getPort()));

            form.getJtxtDbUrl().setEditable(false);
            form.getJtxtDbUser().setEditable(false);
            form.getJtxtDbPass().setEditable(false);
            form.getJtxtServerPort().setEditable(false);
        } catch (NumberFormatException e) {

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Could not find config file under: " + Constants.CONFIG_PATH);
            ex.printStackTrace();
            form.dispose();
            System.exit(0);
        }

    }

    private void addEditAndSaveConfigActionListeners() {
        form.getJbtnEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.getJtxtDbUrl().setEditable(true);
                form.getJtxtDbUser().setEditable(true);
                form.getJtxtDbPass().setEditable(true);
                form.getJtxtServerPort().setEditable(true);
            }
        });

        form.getJbtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Config config = new Config();
                    config.setUrl(form.getJtxtDbUrl().getText());
                    config.setUsername(form.getJtxtDbUser().getText());
                    config.setPassword(form.getJtxtDbPass().getText());
                    try {
                        int port = Integer.parseInt(form.getJtxtServerPort().getText());
                        if (port < 0 || port > 35000) {
                            throw new Exception("Port not vaild.");
                        }
                        config.setPort(port);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(form, "Port not valid.", "Failure", JOptionPane.ERROR_MESSAGE);
                        form.getJtxtServerPort().setText(String.valueOf(config.getPort()));
                    }
                    config.saveConfiguration();

                    form.getJtxtDbUrl().setEditable(false);
                    form.getJtxtDbUser().setEditable(false);
                    form.getJtxtDbPass().setEditable(false);
                    form.getJtxtServerPort().setEditable(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, ex.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void addTestDbAndPortActionListeners() {
        form.getJbtnTestDb().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection(form.getJtxtDbUrl().getText(), form.getJtxtDbUser().getText(), form.getJtxtDbPass().getText());
                    JOptionPane.showMessageDialog(form, "Connection successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(form, "Connection failed.", "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        form.getJbtnTestPort().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ServerSocket ss = new ServerSocket(Integer.parseInt(form.getJtxtServerPort().getText()));
                    JOptionPane.showMessageDialog(form, "Port is open.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    ss.close();
                    ss = null;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(form, "Port is not available.", "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}

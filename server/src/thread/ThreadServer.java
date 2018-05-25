/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milos
 */
public class ThreadServer extends Thread{

    ServerSocket serverSocket;
    List<Thread> clients;

    public ThreadServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }
    
    
    
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Thread client = new ThreadClient(socket);
                client.start();
                clients.add(client);
                //New client arrived
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}

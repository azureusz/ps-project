/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author Milos
 */
public class ThreadClient extends Thread {

    Socket socket;

    public ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                RequestObject request = (RequestObject) input.readObject();

                ResponseObject responseObject = new ResponseObject();
                switch (request.getOperation()) {
                    case IOperation.HI:
                        //CompanyEntity company = (CompanyEntity) request.getData();
                        try {
                            //AbstractGenericOperation saveCompany = new SaveCompany();
                            //saveCompany.templateExecute(company);

                            //responseObject.setCode(IStatus.OK);
                            //responseObject.setData(company);
                        } catch (Exception e) {
                            responseObject.setCode(IStatus.ERROR);
                            responseObject.setMessage(e.getMessage());
                        }

                        break;

                }

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(responseObject);
                output.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.response;

import java.io.Serializable;

/**
 *
 * @author Milos
 */
public class ResponseObject implements Serializable{
    private int code;
    private Object data;
    private String message;

    public ResponseObject() {
    }

    public ResponseObject(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}

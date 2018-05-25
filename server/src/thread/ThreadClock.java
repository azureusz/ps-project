/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.time.LocalTime;
import java.util.Calendar;
import javax.swing.JLabel;

/**
 *
 * @author Milos
 */
public class ThreadClock extends Thread{

    JLabel lblClock;
    
    public ThreadClock(JLabel lblClock) {
        this.lblClock = lblClock;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            LocalTime time = LocalTime.now();
            lblClock.setText(time.toString());
        }
    }
    
    
}

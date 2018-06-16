/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Milos
 */
public class ThreadClock extends Thread {

    JLabel lblClock;

    public ThreadClock(JLabel lblClock) {
        this.lblClock = lblClock;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                LocalTime time = LocalTime.now();
                lblClock.setText(time.format(DateTimeFormatter.ofPattern("H:mm:ss")));
                Thread.sleep(999l);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadClock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

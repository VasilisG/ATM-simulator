/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author Vasilis
 */
public class TimeLogger {
    
     public static String getTime(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String timestampString = timestamp.toString().split(" ")[1];
        String time = timestampString.substring(0, timestampString.indexOf('.'));
        return time;
    }   
}
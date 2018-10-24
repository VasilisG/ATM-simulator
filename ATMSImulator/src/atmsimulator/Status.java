/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

/**
 *
 * @author Vasilis
 */
public class Status {
        
    public static void exitFailure(String message){
        System.out.println(TimeLogger.getTime() + " : " + message);
        System.exit(1);
    }
    
    public static void showMessage(String message){
        System.out.println(TimeLogger.getTime() + " : " + message);
    }  
}
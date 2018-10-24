/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilis
 */
public class ClientProtocol {
    
    private static final Set<String>choices = new HashSet<String>(Arrays.asList(new String[] {"A", "K", "Y", "E"}));
    private static final String digits = "0123456789";
    private static final String SPACE = " ";
    private static final int LIMIT = 420;
    private BufferedReader userInputReader;
    
    
    public ClientProtocol(BufferedReader userInputReader){
        this.userInputReader = userInputReader;
    }
    
    public String request(){
        
        String choice;
        int amount;
        
        choice = getInput();
        
        switch(choice){
            case "A":
                amount = getWithDrawalAmount();
                return choice + SPACE + String.valueOf(amount);
            
            case "K":
                amount = getDepositAmount();
                return choice + SPACE + String.valueOf(amount);
                
            case "Y":
                return choice;
              
            case "E":
                return choice;
        }
        return null;
    }
    
    public boolean isPositive(int number){
        return number >= 0;
    }
    
    public boolean isValidWithDrawalAmount(int number, int limit){
        return isPositive(number) && number <= limit;
    }
    
    public boolean isValidDepositAmount(int number){
        return isPositive(number);
    }
    
    public boolean isValidChoice(String input){
        return choices.contains(input);
    }
    
    public boolean isNumber(String number){
        try
        {
            int n = Integer.parseInt(number);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }
    
    public String getInput(){
        
        String choice = null;
        
        while(true){
            System.out.println("Choice: ");
            try {
                choice = userInputReader.readLine();
            } catch (IOException ex) {
                Status.exitFailure("IO error occured during reading user input. Aborting...");
            }
            if(isValidChoice(choice)){
                break;
            }
            else{
                System.out.println("Invalid choice. Please try again.");
            }
        }
        return choice;
    }
    
    public int getWithDrawalAmount(){
        
        String amount = null;
        
        while(true){
            System.out.print("Enter withdrawal amount: ");
            try {
                amount = userInputReader.readLine();
            } catch (IOException ex) {
                Status.exitFailure("IO error occured during withdrawal amount input. Aborting...");
            }
            if(isNumber(amount)){
                int n = Integer.parseInt(amount);
                if(isPositive(n) && n <= LIMIT){
                    break;
                } 
            }
            else{
                System.out.println("Invalid withdrawal input. Please try again...");
            }
        }
        
        return Integer.parseInt(amount);
    }
    
    public int getDepositAmount(){
        
        String amount = null;
        
        while(true){
            System.out.print("Enter deposit amount: ");
            try {
                amount = userInputReader.readLine();
            } catch (IOException ex) {
                Status.exitFailure("IO error occured during deposit amount input. Aborting...");
            }
            if(isNumber(amount)){
                int n = Integer.parseInt(amount);
                if(isPositive(n)){
                    break;
                }
            }
            else{
                System.out.println("Invalid deposit input. Please try again...");
            }
        }
        return Integer.parseInt(amount);
    }
}
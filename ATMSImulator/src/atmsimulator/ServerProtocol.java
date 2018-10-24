/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilis
 */
public class ServerProtocol {
    
    private Connection connection;
    private int connectionCount;
    
    public ServerProtocol(Connection connection, int connectionCount){
        this.connection = connection;
        this.connectionCount = connectionCount;
    }
    
    public String reply(String request){
        
        char command = request.charAt(0);
        
        switch(command){
            case 'A':
                return makeTransaction(request, false);
            
            case 'K':
                return makeTransaction(request, true);
                
            case 'Y':
                return getBalance();
                
            case 'E':
                return "E";
                
            default:
                return null;
        }
    }
    
    public String makeTransaction(String request, boolean deposit){
        
        int storedAmount, transactionAmount;
        storedAmount = transactionAmount = 0;
        
        String[] commandParts = request.split(" ");
        
        Statement statement = null;
        ResultSet resultSet = null;
                
        transactionAmount = Integer.parseInt(commandParts[1]);

        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        try {
            resultSet = statement.executeQuery(SQLStatement.selectRecord("customers", connectionCount));
            
            if(resultSet.next()){
                storedAmount = Integer.parseInt(resultSet.getString("balance"));
            
                if(deposit) storedAmount += transactionAmount;
                else {
                    if(storedAmount >= transactionAmount) storedAmount -= transactionAmount;
                    else return String.valueOf(-1);
                }
            
                statement.executeUpdate(SQLStatement.updateRecord("customers", connectionCount, storedAmount));
            }
            else Status.showMessage("[Server protocol]: Could not update record in table.");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return String.valueOf(storedAmount);
    }
    
    public String getBalance(){
        
        Statement statement = null;
        ResultSet resultSet = null;
        String balanceString = null;
        
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        try {
            resultSet = statement.executeQuery(SQLStatement.selectRecord("Customers",connectionCount));
            
            if(resultSet.next()){
                balanceString = resultSet.getString("balance");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return balanceString;
    }  
}
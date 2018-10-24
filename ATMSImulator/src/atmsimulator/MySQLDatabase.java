/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Vasilis
 */
public class MySQLDatabase {
    
    private static final int PORT = 3306;
    private static final int NUM_OF_RECORDS = 20;
    private static final int AMOUNT = 2000;
           
    public static void initDatabase(){
        
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atmDB";
        String username = ""; // Fill in username.
        String password = ""; // Fill in password.
        String tableName = "customers";
                
        Connection connection = null;
        Statement statement = null;
            
        try {
            Class.forName(driver);
            Status.showMessage("Got MySQL driver.");
            
        } catch (ClassNotFoundException ex) {
            Status.exitFailure(("Could not find MySQL driver. Aborting..."));
        }
        
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER NOT NULL PRIMARY KEY, balance INTEGER NOT NULL)");
            Status.showMessage("Table created.");
   
            // Populating database
            for(int i=1; i<=NUM_OF_RECORDS; i++){
                    statement.execute(SQLStatement.insertRecord(tableName, i, AMOUNT));
            }
            Status.showMessage("Table populated with records.");
            
        } catch (SQLException ex) {
            Status.showMessage(ex.toString());
        }
    }
    
    public static void main(String[] args){
       initDatabase(); 
    }   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilis
 */
public class Server {
    
    private static final int PORT = 1234;
    
    public static void main(String[] args){
        
        int connectionCount = 0;
        
        ServerSocket connectionSocket = null;
        Connection connection = null;
        
        try {
            connectionSocket = new ServerSocket(PORT);
            Status.showMessage("[Server]: Server created.");
        } catch (IOException ex) {
            Status.exitFailure("[Server]: IO error occured during creation of server. Aborting...");
        }
        Status.showMessage("[Server]: Creating connection with database...");
        connection = createConnection();
        Status.showMessage("[Server]: Connection with database successfully created.");
        
        Status.showMessage("[Server]: Creating server protocol...");
        
        while(true){
            try {
                Status.showMessage("[Server]: Waiting for connection...");
                Socket dataSocket = connectionSocket.accept();
                connectionCount++;
                
                Status.showMessage("[Server]: Received connection request: " + connectionCount + " from " + dataSocket.getInetAddress());
                ClientThread clientThread = new ClientThread(dataSocket, connectionCount, connection);
                clientThread.start();
                
            } catch (IOException ex) {
                Status.exitFailure("[Server]: IO error occured while waiting for connection. Aborting...");
            }
        }
        
    }
    
    public static Connection createConnection(){
        
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atmDB";
        String username = "";
        String password = "";
        String tableName = "customers";
                
        Connection connection = null;
            
        try {
            Class.forName(driver);
            
        } catch (ClassNotFoundException ex) {
            Status.exitFailure("[Server]: Could not find MySQL driver. Aborting...");
        }
        
        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            Status.showMessage("[Server]: " + ex.toString());
        }
        return connection;
    }    
}
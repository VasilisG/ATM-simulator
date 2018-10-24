/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilis
 */
public class ClientThread extends Thread {
    
    private Socket dataSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader reader;
    private PrintWriter writer;
    
    private int connectionCount;
    private Connection connection;
    private ServerProtocol protocol;
    
    public ClientThread(Socket socket, int connectionCount, Connection connection){
        
        dataSocket = socket;
        
        try {
            inputStream = dataSocket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            
        } catch (IOException ex) {
            Status.exitFailure("[Client thread " + connectionCount + "]: IO error occured during creation of input stream. Aborting...");
        }
        try {
            outputStream = dataSocket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            
        } catch (IOException ex) {
            Status.exitFailure("[Client thread " + connectionCount + "]: IO error occured during creation of output stream. Aborting...");
        }
  
        this.connectionCount = connectionCount;
        this.connection = connection;
        
        //Creating server protocol.
        protocol = new ServerProtocol(connection, connectionCount); 
    }
    
    public InputStream getInputStream(){
        return inputStream;
    }
    
    public OutputStream getOutputStream(){
        return outputStream;
    }
    
    public BufferedReader getReader(){
        return reader;
    }
    
    public PrintWriter getWriter(){
        return writer;
    }
    
    public int getConnectionCount(){
        return connectionCount;
    }
    
    public void run(){
        
        while(true){
            try {
                Status.showMessage("[Client thread " + connectionCount + "]: Waiting for request from connection...");
                String request = reader.readLine();
                Status.showMessage("[Client thread " + connectionCount + "]: Got request from connection: " + request);
                
                String reply = protocol.reply(request);
                if(reply != null){
                
                    Status.showMessage("[Client thread " + connectionCount + "]: Sending reply to client: " + reply);
                    writer.println(reply);
                    
                    if(reply.equals("E")){
                        break;
                    }
                }
                else Status.showMessage("[Client thread " + connectionCount + "]: Reply is NULL...");
                
            } catch (IOException ex) {
                    Status.showMessage("[Client thread " + connectionCount + "]: IO error occured while waiting for user's request.");
                    break;
            } 
        }
        Status.showMessage("[Client thread " + connectionCount + "]: Ending connection...");
        try {
            dataSocket.close();
        } catch (IOException ex) {
            Status.exitFailure("[Client thread " + connectionCount + "]: IO error occured while closing socket.");
        }
        Status.showMessage("[Client thread " + connectionCount + "]: Connection ended.");
    }
    
}
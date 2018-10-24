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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vasilis
 */
public class Client {
    
    private static final int PORT = 1234;
    private static final String[] choices = {"A", "K", "Y", "E"};
    
    public static void main(String[] args){
        
        Socket dataSocket = null;
        InetAddress host = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader userInputReader = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        
        ClientProtocol protocol = null;
        String request = null;
        String reply = null;
        
        
        Status.showMessage("Attempting to connect to server...");
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Status.exitFailure("Error: Uknown host.");
        }
        
        try {
            dataSocket = new Socket(host, PORT);
            Status.showMessage("Connected to server.");
        } catch (IOException ex) {
            Status.exitFailure("IO error occured during connection.");
        }
        
        Status.showMessage("Creating input stream with server...");
        try {
            inputStream = dataSocket.getInputStream();
            Status.showMessage("Input stream created.");
        } catch (IOException ex) {
            Status.exitFailure("IO error occured during creation of input stream.");
        }
        
        Status.showMessage("Creating output stream with server...");
        try {
            outputStream = dataSocket.getOutputStream();
            Status.showMessage("Output stream created.");
        } catch (IOException ex) {
            Status.exitFailure("IO error occured during creation of output stream.");
        }
        
        Status.showMessage("Initializing user input interface...");
        userInputReader = new BufferedReader(new InputStreamReader(System.in));
        
        Status.showMessage("Initializing input reader for server replies...");
        reader = new BufferedReader(new InputStreamReader(inputStream));
        
        Status.showMessage("Initializing output writer for client requests...");
        writer = new PrintWriter(outputStream, true);
        
        protocol = new ClientProtocol(userInputReader);
        
        while(true){
            
            showInstructions();
            request = protocol.request();
            
            writer.println(request);
            
            Status.showMessage("Sent request to server: " + request);
            
            if(request.equals("E")){
                break;
            }
                
            try {
                reply = reader.readLine();
                if(reply.equals("E")){
                    break;
                }
                else if(reply.equals("-1")){
                    Status.showMessage("Could not make transaction. Withdrawal amount must be smaller than stored amount.");
                }
                else{
                    Status.showMessage("Got reply from server: " + reply);
                }
            } catch (IOException ex) {
                Status.exitFailure("IO error occured while waiting for server response. Aborting...");
            }    
        }
        
        try {
            reader.close();
        } catch (IOException ex) {
            Status.showMessage("IO error occured while closing buffered reader...");
        }
        
        writer.close();
        
        try {
            inputStream.close();
        } catch (IOException ex) {
            Status.showMessage("IO error occured while closing input stream...");
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Status.showMessage("IO error occured while closing output stream...");
        }
        try {
            dataSocket.close();
        } catch (IOException ex) {
            Status.showMessage("IO error occured while closing connection...");
        }
    }
    
    public static void showInstructions(){
        System.out.println();
        System.out.println("Select an action to proceed:");
        System.out.println("A: Withdrawal");
        System.out.println("K: Deposit");
        System.out.println("Y: Balance");
        System.out.println("E: Exit");
        System.out.println();
    }   
}
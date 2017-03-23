/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonhal
 */
public class ClientInput extends Thread {
    ObjectOutputStream out;
    Socket clientSocket;
    BufferedReader stdIn;
    Client client;
    
    public ClientInput(Socket clientSocket, Client client){
    this.client = client;
    this.clientSocket = clientSocket;
}
   
    
  public void run(){
        
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
          //  stdIn = new BufferedReader(new InputStreamReader(System.in));
            
           
            
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        
    }

void sendMessage(String message, String type){
    
    String collectedMessage;
        try {
            if(message.isEmpty()){
                System.out.println("Message to be sendt was empty");
            }
            out.writeUTF(type);
            out.flush();
            
            out.writeUTF(message);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
}
    
}

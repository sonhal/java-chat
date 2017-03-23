/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonhal
 */
public class ServerClientConnectionOutput extends Thread {
    ObjectOutputStream out;
    Socket clientSocket;
 
    ServerClientConnection serverClientConnection;
    
    public ServerClientConnectionOutput(Socket clientSocket, ServerClientConnection serverClientConnection){
    this.serverClientConnection = serverClientConnection;
    this.clientSocket = clientSocket;
}
   
    
  public void run(){
        
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
          
          
            
            
            
        } catch (IOException ex) {
           ex.printStackTrace();
        } 
        
    }
  
  public void closeThread(){
        try {
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerClientConnectionInput.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
   }
  
  
 public void sendMessage(String type, String message){
    System.out.println(type +" "+ message);
    String collectedMessage;
        try {
            if( type.isEmpty() && message.isEmpty()){
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

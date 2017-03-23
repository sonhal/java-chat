/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonhal
 */
public class ServerClientConnectionInput extends Thread {
    
    ObjectInputStream in;
    Socket clientSocket;
    
    ServerClientConnection serverClientConnection;
    
    public ServerClientConnectionInput(Socket clientSocket, ServerClientConnection serverClientConnection){
    this.serverClientConnection = serverClientConnection;
    this.clientSocket = clientSocket;
}
    
   public void closeThread(){
        try {
            in.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerClientConnectionInput.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
   }
   
    
  public void run(){
        
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
          
            
            while(true){
                if(in.available() == 0)
                {
                    Thread.sleep(10);
                }
                else{
                   String message = in.readUTF();
                   String type = in.readUTF();
                   
                   serverClientConnection.receiveMessage(type, message);
                   System.out.println("we got message");
                   
                }
            }
            
        } catch (IOException ex) {
           ex.printStackTrace();
        } catch (InterruptedException ex) {
           ex.printStackTrace();
        }
        
    }
    
    
    
}

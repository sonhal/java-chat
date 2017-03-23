/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author sonhal
 */
public class ClientOutput extends Thread {
    
    ObjectInputStream in;
    Socket clientSocket;
    Client client;
    
    
    public ClientOutput(Socket clientSocket, Client client){
    this.client = client;
    this.clientSocket = clientSocket;
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
                   String type = in.readUTF();
                   String message = in.readUTF();
                     
                   client.receiveMessage(type, message);
                   
                    }
            }
            
        } catch (IOException ex) {
           ex.printStackTrace();
        } catch (InterruptedException ex) {
           ex.printStackTrace();
        }
        
    }
    
    
}

    
    
    


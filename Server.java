/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author sonhal
 */
public class Server {
    
    
   ServerSocket socket;
   ArrayList<ServerClientConnection> connections = new ArrayList<ServerClientConnection>();
   boolean shouldRun = true;
   
   
 
      
   public Server(){
       
       try{
           
           socket = new ServerSocket(4444);
           System.out.println("Hi, I am EchoUCase TCP server");
           while(shouldRun){
           
           Socket clientSocket = socket.accept();
           ServerClientConnection sc = new ServerClientConnection(clientSocket, this);
           
           sc.start();
           
           
           }
           
       }catch(IOException e){
           e.printStackTrace();
       }
   }
    
    public static void main(String[] args) {
        
      Server server = new Server();
    }
}

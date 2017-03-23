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
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sonhal
 */
public class Client extends Thread{
    
    
    
   Socket clientSocket;
   BufferedReader stdIn;
  
   ClientInput clientInput;
   ClientOutput clientOutput;
   DataOutputStream out;
   DataInputStream in;
   UIcontroller controller;
   boolean loggedIn = false;
 
    
     public Client(UIcontroller controller) {
     
      
      
      this.controller = controller;

   
}
     
   @Override
   public void  run(){
        startClient();
     }
     
        
  void closeClient(){
   
   try{ 

    out.close();
    stdIn.close();
    clientSocket.close();
   }catch(IOException e){
       e.printStackTrace();
   } 
        
}
  
  
  public void receiveMessage(String type, String melding){
      
      switch(type){
          //Server wants you to log in. Send username and password
          case "requestLogin" : 
              controller.showLogin();
              /*
             UIcontroller s = new UIcontroller();
             s.letsStart(this);
              */
              System.out.println("We runnin login window");
            break;
            //user wants to chat with you. user info in message
          case "requestConnection" :
            break;
           //user wants to stop to chat with you.
          case "requestReleaseConnection" :
            break;
          //List of online, busy and offline users.
          case "serverInfo" :
              controller.getUsers(melding);
            break;
          //message from connected user. message contains message
          case "userMessage" :
              controller.getMessage(melding);
            break;
          case "loginFailed" :
              controller.loginFailed();
            break;
          case "loginSuc" :
              loggedIn = true;
              controller.chatbox();
            break;
          case "serverInfoStop" :
              System.out.println("we got stop");
              controller.printUsers();
              
            break;

      }
      
      
      
  }
  
  
  public void sendLogin(String user, String password){
      clientInput.sendMessage("loginRequest",user+","+password);
  }
  
  
  public void sendMessage(String type, String message){
     clientInput.sendMessage(type, message);
     System.out.print("Message fra klient"+ type + " " + message);
     
  }
  
  public void startClient(){
      
      String hostName = "127.0.0.1"; // Default host, localhost
      int port_number = 4444; // Default port to use
      
      try {
             
            clientSocket = new Socket(hostName,port_number);
           
            clientInput = new ClientInput(clientSocket, this);
            clientOutput = new ClientOutput(clientSocket, this);
            
            
            clientInput.start();
            clientOutput.start();
            
            
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("I (Client) [" + InetAddress.getLocalHost()  + ":" + clientSocket.getLocalPort() + "] > Connected ");
            
            while(true){
               if(loggedIn){
                Thread.sleep(100);
                clientInput.sendMessage("serverInfo","");
                System.out.println("vi sendte serverinfo");
                
               }
                Thread.sleep(5000);
             
            }
            
          
           
        }catch( IOException e ){
            e.printStackTrace();
            System.err.println("Unknown host " + hostName);
            closeClient();
       
    }  catch (InterruptedException ex) {
           Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
       }
        
  }
  
    
   

    
}

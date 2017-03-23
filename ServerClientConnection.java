/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonhal
 */
public class ServerClientConnection extends Thread{
    
    Socket clientSocket;
    //ObjectInputStream in;
   // ObjectOutputStream out;
    Server server;
    ArrayList<ServerClientConnection> clientList;
    String[] lUsers;
    String username;
    ServerClientConnectionInput inputThread; 
    ServerClientConnectionOutput outputThread;
    ServerClientConnection clientToChatWith;
    
    
    
    public ServerClientConnection(Socket clientSocket, Server server){
    this.server = server;
    this.clientSocket = clientSocket;
    this.clientList = server.connections;
    
}
   
    
  public void run(){
        
        try {
         //   in = new ObjectInputStream(clientSocket.getInputStream());
          //  out = new ObjectOutputStream(clientSocket.getOutputStream());
            inputThread = new  ServerClientConnectionInput(clientSocket, this);
            outputThread = new  ServerClientConnectionOutput(clientSocket, this);
            inputThread.start();
            outputThread.start();
            Thread.sleep(100);
            
            outputThread.sendMessage("requestLogin","bla");
           
            boolean flag = false;
            
         
          } catch (InterruptedException ex) {
            Logger.getLogger(ServerClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
  
  
  
 boolean login(String username, String password){
      
      String loginUP = null;
      boolean isOnline = true;
      
      
      
      if(username != null){
          this.username = username;
          loginUP = username + password;
           
      }
      
       return reader(loginUP);
  }
 
 static boolean reader(String user){
        String usr = user;
        
        String ln;
        boolean isEq = false;
        try(
                BufferedReader read = new BufferedReader(new FileReader("usernamesPasswords.txt"));
           )
        {
            
            while((ln = read.readLine()) != null){
        
               if(usr.equals(ln)){
                   
                   
                 isEq = true;
                 
                     }
               
        }
        }
        catch (IOException e){
        			e.printStackTrace();
        }
        
        
        return isEq;
        
        
    }
 
 
 
 public synchronized void getUsers(){
     
     clientList = server.connections;
     lUsers = new String[clientList.size()];
     for(int i = 0; i < lUsers.length ; i++){
         lUsers[i] = clientList.get(i).username;
       
     }
    
 }

 public synchronized void listUsers(){
     
     getUsers();
     System.out.println("we got users");
     for(int i = 0; i < lUsers.length; i++){
             if(this.username.equals(lUsers[i])){
                 continue;
             }
             
             outputThread.sendMessage("serverInfo",lUsers[i]);
             System.out.println("we sendt user " + lUsers[i]);
     }
     outputThread.sendMessage("serverInfoStop","ff");
 }
 
 public synchronized void removeUser(){
    
     
     for(int i = 0; i < clientList.size(); ){
         
        long thisThread = clientList.get(i).getId();
        if(thisThread == this.getId()){
         
          clientList.remove(i);
          
        }i++;
     }
     
 }
 
 synchronized boolean checkIfUserIsOnline(String brukernavn){
     System.out.println(brukernavn + "lets check if this user is online");
     getUsers();
     
     boolean online = false;
     for(int i = 0; i< clientList.size();){
         String usersOnline;
         usersOnline =  clientList.get(i).getUsername();
         System.out.println(usersOnline + " "+ brukernavn);
         if(usersOnline.equals(brukernavn)){
          System.out.println("han var pålogga allerede: " + usersOnline + "er lik " + brukernavn);
          online = true;
          break;
         }i++;
     }
     if(!online){
         System.out.println("han var ikke pålogga");
     }
     
     return online;
 }
 
 
 public String getUsername(){
     return username;
 }
 public void setUserConnection(ServerClientConnection s){
     this.clientToChatWith = s;
 }
 
 public boolean tryConnectToUser(String username){
        getUsers();
       if(checkIfUserIsOnline(username)){
             for(int i = 0; i < clientList.size(); i++){
             if(username.equals(clientList.get(i).username)){
                 setUserConnection(clientList.get(i));
                 clientList.get(i).setUserConnection(this);
                 return true;
             }
          
     }
       }
       return false;
 }
 public void sendMessage(String type, String message){
     outputThread.sendMessage(type, message);
 }
 
 public void closeUser(){
     clientToChatWith.sendMessage("userMessage", username+ " : " + " logged off \n");
     removeUser();
     inputThread.closeThread();
     outputThread.closeThread();
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 
 
   public void receiveMessage(String type, String melding){
       
      System.out.println(type +","+ melding);
      switch(type){
          //Server wants you to log in. Send username and password
          case "loginRequest" : 
              String[] info = melding.split(",");
              System.out.println("asking for login");
              boolean couldLogin = login(info[0],info[1]);
               if(couldLogin && !checkIfUserIsOnline(info[0])){
                   
                   outputThread.sendMessage("loginSuc", "ff");
                   server.connections.add(this);
               }
               else{
                   outputThread.sendMessage("loginFailed","ff");
               }
              System.out.println("We runnin login window");
             
            break;
            //user wants to chat with you. user info in message
          case "requestConnection" :
             
            boolean canC = tryConnectToUser(melding);
            if(canC){
                sendMessage("userMessage", "You are Chatting with - " + clientToChatWith.username);
                clientToChatWith.sendMessage("userMessage", "You are Chatting with - " + username);
            }
            else{
                
            }
            break;
           //user wants to stop to chat.
          case "requestReleaseConnection" :
              closeUser();
            break;
          //List of online, busy and offline users.
          case "serverInfo" :
               listUsers();
            break;
          //message from connected user. message contains message
          case "userMessage" :
              clientToChatWith.sendMessage("userMessage", username+ " : \n" + melding);
              sendMessage("userMessage", username+ " : \n" + melding);
            break;
          case "loginFailed" :
             
            break;

      }
     
     
 
}

}
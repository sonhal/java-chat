/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author sonhal
 */
public class UIcontroller extends Application{

   Client client;
   FXMLLoader loader;
   Parent root;
   Stage primaryStage;
   Stage chatStage;
   boolean showLogin = false;
   boolean loginFailed = false;
   boolean loginSuc = false;
   LoginController controller;
   ChatController chatController;
   


    @FXML
    public void clickUser(){
        
    }
    
    public void getUsers(String s){
     chatController.getUsers(s);
    }
    
    public void printUsers(){
      Platform.runLater(() -> {chatController.printUsers();});    
    
    }
   
    public void sendMessage(String type, String message){
        client.sendMessage(type,message); 
        System.out.println("type : " + type + " message " + message);
    }
   
   public void getMessage(String message){
         Platform.runLater(() -> {chatController.getMessage(message);});
   }
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        this.primaryStage = primaryStage;
        
        client = new Client(this);
        client.start();

    }
    
    public void showLogin(){
        Platform.runLater(() -> {loginUI();});
        showLogin = true;
      
        
    }
    
    public void loginFailed(){
        Platform.runLater(() -> {controller.failedLogin();});
    }
    
    public void chatbox(){
        
        Platform.runLater(() -> {openChatbox();});
        
         
    }
    
    public void closeClient(){
        client.sendMessage("requestReleaseConnection","ff");
    }
    
    
    
    public void openChatbox(){
    loader = new FXMLLoader( getClass().getResource("chat.fxml"));
        System.out.println("No kjører vi Chat");
       try {
           root = loader.load();
       } catch (IOException ex) {
           Logger.getLogger(UIcontroller.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
       chatController = loader.<ChatController>getController();
       chatController.setUIcontroller(this);
       //controller.setClient(client);
        
        primaryStage.setTitle("chat client");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.setResizable(false);
        primaryStage.show(); 
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            public void handle(WindowEvent we){
                System.out.println("client closing");
                closeClient();
            }
            
        });
}
    
    
    
    
    private void loginUI(){
        loader = new FXMLLoader( getClass().getResource("login.fxml"));
        System.out.println("No kjører vi GUI");
       try {
           root = loader.load();
       } catch (IOException ex) {
           Logger.getLogger(UIcontroller.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
       controller = loader.<LoginController>getController();
       
       controller.setClient(client);
        
        primaryStage.setTitle("chat klient");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setResizable(false);
        primaryStage.show(); 
        
    }
    
    
    
    
    
 
  public static void main(String[] args) {
        
        launch(args);
 
        
    }
  
    
}

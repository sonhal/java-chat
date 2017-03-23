/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sonhal
 */
public class ChatController implements Initializable {
ArrayList<String> items = new ArrayList<String>();
UIcontroller uiController;
String chatText = "";
    

public void setUIcontroller(UIcontroller uicontroller){
    this.uiController = uicontroller;
}


       @FXML
    public Button sendMessage;
    
    public TextField message;
    public TextArea messageField;
    public ListView listUsers;
    
       
   
    @FXML
    public void sendMessage(){
        String messageToClient;
        messageToClient = message.getText();
        uiController.sendMessage("userMessage", messageToClient);
        message.clear();
        System.out.println("vi sender - "+ messageToClient +" - til Client");
        
    }
    



    
    public void connectToUser(){
      ObservableList selected;
     selected = listUsers.getSelectionModel().getSelectedItems();
     if(selected.size() > 0)   {
         System.out.println(selected.get(0).toString() + "heherhehrhe");
         uiController.sendMessage("requestConnection",  selected.get(0).toString());
         
     }
     else{
         
     }
    }
    
    
    public void getMessage(String message){
        chatText += message +"\n";
        if(chatText != null)
        {
        messageField.setText(chatText);
        }
        
    }
    
    public void getUsers(String s){

      items.add(s);
      System.out.println("getting some user");
    }
    
    public void printUsers(){
        ObservableList<String> userL = FXCollections.observableArrayList("tom");
        if(items.size() < 1){
           userL = FXCollections.observableArrayList("No users online..."); 
        }
        else{
           userL = FXCollections.observableArrayList(items);
           System.out.println(items.get(0));
        }
    
    
    listUsers.setItems(userL);
    items.clear();
    
    }
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        
    }    
    
}

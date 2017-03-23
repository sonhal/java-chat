/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservermultithreadexperimental;


import javafx.fxml.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sonhal
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
      String usernametxt;
      String passwordtxt;
      Client client;
      
    @FXML
    public Button button;
    
    public TextField password;
    public TextField username;
    public Label failedLogin;
    @FXML
    public void logInHandler(){
        passwordtxt = password.getText();
        usernametxt = username.getText();
        client.sendLogin(usernametxt, passwordtxt);
        System.out.println("vi prøver å logge inn");
        
    }
    
    public void failedLogin(){
        failedLogin.textProperty().setValue("login failed!!!!");
    }
    
    public void setClient(Client client){
        this.client = client;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

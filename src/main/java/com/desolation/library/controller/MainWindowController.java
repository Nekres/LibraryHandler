package com.desolation.library.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainWindowController implements Initializable, EventHandler<ActionEvent>{
    @FXML Label warningField;
    @FXML TextField loginField;
    @FXML PasswordField passwordField;
    @FXML RadioButton userRB;
    @FXML RadioButton adminRB;
    @FXML Button registrationBtn;
    @FXML Button signInBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SQLUtils.openConnection("root", "finished");
        registrationBtn.setOnAction(this);
    }    
    private final void openRegistrationWindow() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registrationWindow.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == registrationBtn){
            try {
                openRegistrationWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

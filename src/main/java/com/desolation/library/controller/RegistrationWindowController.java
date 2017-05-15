/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Qosmio
 */
public class RegistrationWindowController implements Initializable {
    @FXML TextField loginField;
    @FXML PasswordField passField;
    @FXML PasswordField passAgainField;
    @FXML TextField nameField;
    @FXML TextField surnameField;
    @FXML TextField patField;
    @FXML TextField addressField;
    @FXML TextField phoneField;
    @FXML DatePicker datePicker;
    @FXML Button finishBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

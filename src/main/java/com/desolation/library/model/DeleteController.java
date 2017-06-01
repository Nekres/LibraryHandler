/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.model;

import com.desolation.library.controller.SQLUtils;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author desolation
 */
public class DeleteController implements Initializable {
    @FXML Button okButton;
    @FXML TextField loginField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String user = loginField.getText();
                try {
                    int id = -1;
                    ResultSet set = SQLUtils.executeQuery("SELECT user_id FROM account a WHERE a.login = \"" + user + "\"");
                    while(set.next()){
                        id = set.getInt(1);
                    }
                    SQLUtils.update("DELETE FROM user WHERE user_id = " + id);
                    SQLUtils.update("DELETE FROM account WHERE login = " + "\"" + user + "\"");
                    Stage window  = (Stage)okButton.getScene().getWindow();
                    window.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }    
    
}

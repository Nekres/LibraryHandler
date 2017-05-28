/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nekres
 */
public class BookInsertionControllerController implements Initializable {
    @FXML TextField bookNameField;
    @FXML TextField authorName;
    @FXML TextField authorSurname;
    @FXML Button addBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int id = -1;
                    ResultSet set = SQLUtils.executeQuery("SELECT author_id FROM author WHERE author_name = \""+ authorName.getText() + "\" AND author_surname = \""+ authorSurname.getText() + "\"");
                    while(set.next()){
                        id = set.getInt(1);
                    }
                    if(id != -1)
                        SQLUtils.update("INSERT INTO book(book_id,book_name,author_id,book_owner) VALUES (null,\"" + bookNameField.getText()+ "\"," +
                                id + "," + null + ")");
                    else{
                        int ids = -1;
                        SQLUtils.executeQuery("INSERT INTO author(author_id,author_name,author_surname) VALUES (null, \"" + authorName + "\",\"" + authorSurname.getText() + "\")");
                        ResultSet set2 = SQLUtils.executeQuery("SELECT author_id FROM author WHERE author_name=\"" + authorName.getText() + "\" AND author_surname = \"" + authorSurname.getText() + "\"");
                        while(set2.next()){
                            ids = set2.getInt(1);
                        }
                        if(ids != -1){
                            SQLUtils.executeQuery("INSERT INTO book(book_id,book_name,author_id,book_owner) VALUES (null,\"" + bookNameField.getText()+ "\"," +
                                ids + "," + null + ")");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.controller;

import com.desolation.library.model.CurrentUser;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author nekres
 */
public class WorkingWindowController implements Initializable {
    @FXML ListView bookList;
    public static int USER_ID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CurrentUser user = new CurrentUser(USER_ID);
            bookList.setItems(user.getBooks());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void init(final int id) throws SQLException{
        
    }
    private final void getAllBooks(){
        
    }
    private final void getUserBooks(){
        
    }
    
}

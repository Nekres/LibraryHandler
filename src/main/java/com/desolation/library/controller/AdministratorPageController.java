package com.desolation.library.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.desolation.library.model.TableData;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nekres
 */
public class AdministratorPageController implements Initializable {
    @FXML TableView historyTable;
    @FXML TableColumn nameColumn;
    @FXML TableColumn actionColumn;
    @FXML TableColumn bookColumn;
    @FXML TableColumn dateColumn;
    @FXML Button addBook;
    @FXML Button deleteUser;
    @FXML Button addUser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/registrationWindow.fxml"));
                    Stage window = new Stage();
                    window.setScene(new Scene(root));
                    window.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        addBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/BookInsertionController.fxml"));
                    Stage window = new Stage();
                    window.setTitle("Добавить книгу");
                    window.setScene(new Scene(root));
                    window.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        actionColumn.setCellValueFactory(new PropertyValueFactory("action"));
        bookColumn.setCellValueFactory(new PropertyValueFactory("book"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        try {
            ResultSet set = SQLUtils.executeQuery("SELECT u.name,a.action_type,b.book_name, a.action_date FROM user u INNER JOIN action a ON u.user_id = a.user_id INNER JOIN "
                    + "book b ON a.book_id = b.book_id");
            while(set.next()){
                TableData pojo = new TableData(set.getString(1), set.getString(2), set.getString(3), set.getString(4));
                historyTable.getItems().add(pojo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }    
    
}

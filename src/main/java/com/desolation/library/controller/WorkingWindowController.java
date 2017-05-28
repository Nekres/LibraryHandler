/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.controller;

import com.desolation.library.model.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author nekres
 */
public class WorkingWindowController implements Initializable,EventHandler<ActionEvent> {
    public static CurrentUser user;
    @FXML ListView<Book> bookList;
    @FXML TableView<Book> tableView;
    @FXML TableColumn authorColumn;
    @FXML TableColumn nameColumn;
    @FXML Button searchButton;
    @FXML TextField searchField;
    @FXML RadioButton radioAuthor;
    @FXML Button addButton;
    @FXML Button getBack;
    @FXML Label warningField;
    private ObservableList<Book> data = FXCollections.observableArrayList();
    public static int USER_ID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchButton.setOnAction(this);
        addButton.setOnAction(this);
        getBack.setOnAction(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
            user = new CurrentUser(USER_ID);
            bookList.setItems(user.getBooks());
            getAllBooks();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.setItems(data);
            }
        }).start();
        
    }
    public void init(final int id) throws SQLException{
        
    }
    private final void getAllBooks() throws SQLException{
        ResultSet set = SQLUtils.executeQuery("SELECT b.book_name, a.author_name, a.author_surname, b.book_id FROM book b INNER JOIN author a ON b.author_id = a.author_id WHERE b.book_owner IS NULL;");
            while(set.next()){
                Book book = new Book();
                for(int i = 0; i < 4;i++){
                    switch(i){
                        case 0:book.setName(new SimpleStringProperty(set.getString(i+1)));
                        break;
                        case 1:book.setAuthor(new SimpleStringProperty(set.getString(i+1)));
                        break;
                        case 2:book.setAuthor(new SimpleStringProperty(book.getAuthor() +" " + set.getString(i+1)));
                        break;
                        case 3:book.setBook_id(set.getInt(i+1));
                        break;
                    }
                    }
                data.add(book);
        }
            set.close();
    }
    private final void returnBook(){
        if(bookList.getSelectionModel().getSelectedItem() == null)
            return;
        Book b = bookList.getSelectionModel().getSelectedItem();
        bookList.getItems().remove(b);
        try {
            SQLUtils.update("UPDATE book SET book_owner = null WHERE book_id = " + b.getBook_id());
            String date = new java.sql.Date(new Date().getTime()).toLocalDate().format(DateTimeFormatter.ISO_DATE);
            System.out.println(date);
            SQLUtils.update("INSERT INTO action(user_id,book_id,action_type,action_date) VALUES(" + user.getUser_id() + "," + b.getBook_id() + "," +"\"return\"," + "\""+ date + "\")");
            tableView.getItems().add(b);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private final void searchBookByName(final String name){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for(int i = 0; i < data.size();i++){
            if(data.get(i).getName().contains(name))
                books.add(data.get(i));
        }
        tableView.setItems(books);
    }
    private final void searchBookByAuthor(final String author){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for(int i = 0; i < data.size();i++){
            if(data.get(i).getAuthor().contains(author))
                books.add(data.get(i));
        }
        tableView.setItems(books);
    }
    private final void search(){
        
    }
    private final void addBook(){
        Book book = tableView.getSelectionModel().getSelectedItem();
        int book_id = book.getBook_id();
        ObservableList<Book> list = user.getBooks();
        if(list.contains(book)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    warningField.setText("Экземпляр этой книги уже есть у вас.");
                   // warningField.setText("");
                }
            });
        } else{
        user.getBooks().add(book);
            try {
                String date = new java.sql.Date(new Date().getTime()).toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
                SQLUtils.update("UPDATE book SET book_owner = " + USER_ID + " WHERE book_id = " + book.getBook_id());
                SQLUtils.update("INSERT INTO action(user_id,book_id,action_type,action_date) VALUES(" + user.getUser_id() + "," + book.getBook_id() + "," +"\"get\"," + "\""+ date + "\")");
                data.remove(book);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == searchButton){
            if(radioAuthor.isSelected())
                searchBookByAuthor(searchField.getText());
            else
                searchBookByName(searchField.getText());
        }
        if(event.getSource() == addButton){
            addBook();
        }
        if(event.getSource() == getBack){
            returnBook();
        }
    }
    
    
}

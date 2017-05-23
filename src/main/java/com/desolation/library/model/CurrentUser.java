/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.model;

import com.desolation.library.controller.SQLUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;

/**
 *
 * @author nekres
 */
public class CurrentUser{
    private final int user_id;
    ObservableList<Book> books = FXCollections.observableArrayList();

    public CurrentUser(int user_id) throws SQLException {
        this.user_id = user_id;
        loadBooks();
    }
    
    private final void loadBooks() throws SQLException{
        ResultSet set = SQLUtils.executeQuery("SELECT book_name, book_id FROM book b WHERE b.book_owner = "+user_id);
        while(set.next()){
            Book book = new Book();
            book.setName(new SimpleStringProperty(set.getString(1)));
            book.setBook_id(set.getInt(2));
            books.add(book);
        }
        set.close();
    }

    public ObservableList<Book> getBooks() {
        return books;
    }
    
    
}

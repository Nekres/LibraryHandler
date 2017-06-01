
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

    public int getUser_id() {
        return user_id;
    }
    
    private final void loadBooks() throws SQLException{
        ResultSet set = SQLUtils.executeQuery("SELECT b.book_name, b.book_id, a.author_name, a.author_surname FROM book b INNER JOIN author a ON b.author_id = a.author_id WHERE b.book_owner = "+user_id);
        while(set.next()){
            Book book = new Book();
            book.setName(new SimpleStringProperty(set.getString(1)));
            book.setBook_id(set.getInt(2));
            book.setAuthor(new SimpleStringProperty(set.getString(3) + " " + set.getString(4)));
            books.add(book);
        }
        set.close();
    }

    public ObservableList<Book> getBooks() {
        return books;
    }
    
    
}

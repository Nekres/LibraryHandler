/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author nekres
 */
public class Book {
    private int book_id;
    private SimpleStringProperty name;
    private SimpleStringProperty author;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public void setAuthor(SimpleStringProperty author) {
        this.author = author;
    }
    

    public String getName() {
        return name.get();
    }

    public String getAuthor() {
        return author.get();
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book)
        return this.book_id == ((Book) obj).book_id;
        else return false;
    }
    
    
    
}

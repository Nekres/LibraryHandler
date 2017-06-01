package com.desolation.library.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author nekres
 */
public class TableData {
    final SimpleStringProperty name;
        final SimpleStringProperty action;
        final SimpleStringProperty book;
        final SimpleStringProperty date;

        public TableData(String name, String action, String book, String date) {
            this.name = new SimpleStringProperty(name);
            this.action = new SimpleStringProperty(action);
            this.book = new SimpleStringProperty(book);
            this.date = new SimpleStringProperty(date);
        }

        public String getName() {
            return name.getValue();
        }

        public String getAction() {
            return action.getValue();
        }

        public String getBook() {
            return book.getValue();
        }

        public String getDate() {
            return date.getValue();
        }
}

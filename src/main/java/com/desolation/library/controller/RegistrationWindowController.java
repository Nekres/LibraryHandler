/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desolation.library.controller;

import com.desolation.library.model.User;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

/**
 * FXML Controller class
 *
 * @author Qosmio
 */
public class RegistrationWindowController implements Initializable,EventHandler<ActionEvent> {
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
    @FXML Label loginErr;
    @FXML Label warningField;
    @FXML ProgressBar progressBar;
    @FXML Label greetingLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        finishBtn.setOnAction(this);
        loginField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    boolean exist = SQLUtils.isLoginExist(newValue) && !newValue.equals("");
                    if(exist)
                        loginErr.setText("Такой логин уже есть.");
                    else
                        loginErr.setText("");
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Connection with bd lost.");
                }
            }
            
        });
    }
    public final void registerNew(){
        if(nameField.getText().length() < 3){
            warningField.setText("Имя неправильное, или содержит запрещенные символы");
            return;
        }
        if(passField.getText().length() < 4 || !passAgainField.getText().equals(passField.getText())){
            warningField.setText("Пароли не совпадают или содержат запрещенные символы");
            return;
        }
        if(surnameField.getText().length() < 5){
            warningField.setText("Фамилия неправильная, или содержит запрещенные символы.");
            return;
        }
        if(patField.getText().length() < 5){
            warningField.setText("Отчество неправильное, или содержит запрещенные символы.");
            return;
        }
        if(addressField.getText().length() < 3){
            warningField.setText("Адрес неправильный, или содержит запрещенные символы");
        }
        if(phoneField.getText().length() < 6){
            warningField.setText("Неправильный номер телефона");
            return;
        }
        if(datePicker.getValue().getYear() > 2010){
            warningField.setText("Введите дату рождения.");
            return;
        }
        User u = new User(loginField.getText(), passField.getText(), nameField.getText(), surnameField.getText(),patField.getText(),addressField.getText()
                ,phoneField.getText(), datePicker.getValue().format(DateTimeFormatter.ISO_DATE));
        try {
 
          SQLUtils.addUser("root", "finished", u);
            for(int i = 0; i< 100;i++)
                progressBar.setProgress(i);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Завершено.");
                alert.setContentText("Аккаунт " + "some" + "успешно создан");
                alert.showAndWait().ifPresent(new Consumer<ButtonType>(){
                    @Override
                    public void accept(ButtonType t) {
                        if(t == ButtonType.OK){
                            Stage window = (Stage)progressBar.getScene().getWindow();
                            window.close();
                        }
                    }
                    
                });
                
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("smth wrong with connection");
        }
        Stage window = (Stage)loginField.getScene().getWindow();
        window.close();
    }
    

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == finishBtn){
            registerNew();
        }
    }

}

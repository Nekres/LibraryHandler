package com.desolation.library.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindowController implements Initializable, EventHandler<ActionEvent>{
    @FXML Label warningField;
    @FXML TextField loginField;
    @FXML PasswordField passwordField;
    @FXML RadioButton userRB;
    @FXML RadioButton adminRB;
    @FXML Button registrationBtn;
    @FXML Button signInBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SQLUtils.openConnection("root", "finished");
        registrationBtn.setOnAction(this);
        signInBtn.setOnAction(this);
        
    }    
    private final void openRegistrationWindow() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registrationWindow.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.show();
    }
    private final void checkOnUserExist(final String login, final String password) throws IOException{
        try {
            boolean isExist = SQLUtils.isLoginExist(login);
            if(!isExist)
                warningField.setText("Такого пользователя не существует.");
            int userId = SQLUtils.signIn(login, password);
            if(userId > 0){
                WorkingWindowController.USER_ID = userId;
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/WorkingWindowController.fxml"));
                Stage s = new Stage();
                s.setTitle("Добро пожаловать");
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Barrio");
                //s.setResizable(false);
                s.setScene(scene);
                s.initModality(Modality.WINDOW_MODAL);
                s.initOwner(warningField.getScene().getWindow());
                
                s.show();
            }
        } catch (SQLException ex) {
            warningField.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }
    private final void logAsRoot(final String login, final String password) throws IOException{
        boolean success = SQLUtils.openConnection(login, password);
        if(success){
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdministratorPage.fxml"));
            Stage s = new Stage();
            s.setTitle("Панель администратора");
            Scene scene = new Scene(root);
            s.setScene(scene);
            s.initModality(Modality.WINDOW_MODAL);
            s.initOwner(warningField.getScene().getWindow());
            s.show();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == registrationBtn){
            try {
                openRegistrationWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (event.getSource() == signInBtn) {
            try {
                if (userRB.isSelected()) {
                    checkOnUserExist(loginField.getText(), passwordField.getText());
                } else {
                    logAsRoot(loginField.getText(), passwordField.getText());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

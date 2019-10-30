package ru.readingroom.system.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.readingroom.system.App;
import ru.readingroom.system.Main;
import ru.readingroom.system.models.Constants;
import ru.readingroom.system.models.DataBase;

import java.io.IOException;

public class LoginController implements EventHandler<ActionEvent> {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label userTypeLabel;

    @FXML
    private AnchorPane selectLoginTypePane, loginPane;

    @FXML
    private Button enterAsAdminButton, enterAsLibrarianButton;

    @FXML
    private Button backButton, loginButton;

    @FXML
    private TextField loginInput, passwordInput;

    public int userType;

    @FXML
    void initialize() {
        enterAsAdminButton.setOnAction(this);
        enterAsLibrarianButton.setOnAction(this);
        backButton.setOnAction(this);
        loginButton.setOnAction(this);
    }

    public void showSelectLoginTypePane() {
        loginPane.setVisible(false);
        selectLoginTypePane.setVisible(true);
    }

    public void showLoginPane() {
        if (userType == Constants.USER_ADMIN)
            userTypeLabel.setText("Администратор");
        else
            userTypeLabel.setText("Библиотекарь");
        loginInput.setText("");
        passwordInput.setText("");
        selectLoginTypePane.setVisible(false);
        loginPane.setVisible(true);
    }

    @Override
    public void handle(ActionEvent event) {
        Object src = event.getSource();

        if (src.equals(enterAsAdminButton)) {
            userType = Constants.USER_ADMIN;
            showLoginPane();
        } else if (src.equals(enterAsLibrarianButton)) {
            userType = Constants.USER_LIBRARIAN;
            showLoginPane();
        } else if (src.equals(backButton)) {
            showSelectLoginTypePane();
        } else if (src.equals(loginButton)) {
            boolean result = DataBase.checkLogin(loginInput.getText(), passwordInput.getText(), userType);
            if (result) {
                startApp();
            }
            else Main.showMsg("Не удалось войти", "Неправильный логин или пароль", Alert.AlertType.ERROR, null);
        }
    }

    private void startApp() {
        if (userType == Constants.USER_ADMIN) {
            App.xmlSrc = "views/admin.fxml";
            App.title = "Система учета библиотеки | Администратор";
        } else {
            App.xmlSrc = "views/librarian.fxml";
            App.title = "Система учета библиотеки | Библиотекарь";
        }
        try {
            new App().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.stage.close();
    }
}

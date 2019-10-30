package ru.readingroom.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.readingroom.system.models.DataBase;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        try {
            DataBase.connect();
            Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
            stage.setTitle("Вход в систему");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void showMsg(String titleString, String messageString, Alert.AlertType status, String iconStr) {
        Alert alert = new Alert(status);
        alert.setTitle(titleString);
        alert.setHeaderText("");
        alert.setContentText(messageString);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package ru.readingroom.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static String xmlSrc, title;
    public static Stage stage;

    public void start(Stage primaryStage) throws  Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(xmlSrc));
        Scene scene = new Scene(root, 1187, 790);
        stage.setTitle(title);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        //stage.setMaximized(true);
    }

}
package com.example.advance_chat_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setTitle("Client!");
        stage.setScene(new Scene(root, 478, 396));
        /* stage.setScene(scene);*/
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
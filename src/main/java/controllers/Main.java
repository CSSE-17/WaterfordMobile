package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
        Scene loginScene = new Scene(root);

        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

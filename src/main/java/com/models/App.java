package com.models;

import com.controllers.mainViewController;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.net.URL;

public class App extends Application{

    private sudoku sudoku;

    public static void main(String[] args) {
        launch();
}
    
        @Override
        public void start(Stage primaryStage) throws Exception {

            this.sudoku = new sudoku();

            URL location = getClass().getResource("/fxml/mainView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Scene scene = new Scene(fxmlLoader.load());

            mainViewController mainViewController = fxmlLoader.getController();
            mainViewController.setSudoku(sudoku);

            scene.getStylesheets().add(App.class.getResource("/css/sudoku.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sudoku Solver 3000");
            primaryStage.show();

        }
}

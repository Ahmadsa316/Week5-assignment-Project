package org.example.week4_assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/org/example/week4_assignment/shopping_cart.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Shopping Cart - Aroush");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
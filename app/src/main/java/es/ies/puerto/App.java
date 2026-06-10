package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/es/ies/puerto/view/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 1000);
        stage.setTitle("CentroPlus Connect");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        // Cargamos la vista de bienvenida manualmente para evitar ciclo infinito
        es.ies.puerto.controller.MainController mainController = fxmlLoader.getController();
        mainController.loadWelcomeView();
    }

    public static void main(String[] args) {
        launch();
    }
}

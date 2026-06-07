package es.ies.puerto.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private StackPane contentArea;
    @FXML
    private Label statusLabel;

    @FXML
    public void showActividades() {
        loadView("/es/ies/puerto/view/actividades.fxml");
    }

    @FXML
    public void showReservas() {
        loadView("/es/ies/puerto/view/reservas.fxml");
    }

    @FXML
    public void showIncidencias() {
        loadView("/es/ies/puerto/view/incidencias.fxml");
    }

    @FXML
    public void showUsuarios() {
        loadView("/es/ies/puerto/view/usuarios.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            URL url = getClass().getResource(fxmlPath);
            if (url == null) {
                if (statusLabel != null) {
                    statusLabel.setText("Error: No se encuentra " + fxmlPath);
                }
                System.err.println("No se encuentra el archivo FXML en: " + fxmlPath);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();
            if (contentArea != null) {
                contentArea.getChildren().setAll(view);
            }
            if (statusLabel != null) {
                statusLabel.setText("Cargado: " + fxmlPath.substring(fxmlPath.lastIndexOf("/") + 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (statusLabel != null) {
                statusLabel.setText("Error al cargar vista: " + fxmlPath);
            }
        }
    }
}

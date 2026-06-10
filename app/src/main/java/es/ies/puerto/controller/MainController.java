package es.ies.puerto.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private StackPane contentArea;
    
    private Parent welcomeView;

    // Método para establecer el contenido directamente
    public void setContent(Parent view) {
        if (contentArea != null) {
            contentArea.getChildren().setAll(view);
        }
    }

    @FXML
    public void initialize() {
        loadWelcomeView();
    }

    // Para testear, hacemos este método package-private
    void loadWelcomeView() {
        try {
            URL url = getClass().getResource("/es/ies/puerto/view/welcome.fxml");
            if (url != null) {
                FXMLLoader loader = new FXMLLoader(url);
                loader.setController(this);
                welcomeView = loader.load();
                
                if (contentArea != null) {
                    contentArea.getChildren().setAll(welcomeView);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos para navegar a las listas principales
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

    @FXML
    public void showMain() {
        if (contentArea != null && welcomeView != null) {
            contentArea.getChildren().setAll(welcomeView);
        }
    }

    // Métodos para navegar a los formularios de añadir
    public void showAddActividad() {
        loadView("/es/ies/puerto/view/add_actividad.fxml");
    }

    public void showAddReserva() {
        loadView("/es/ies/puerto/view/add_reserva.fxml");
    }

    public void showAddIncidencia() {
        loadView("/es/ies/puerto/view/add_incidencia.fxml");
    }

    public void showAddUsuario() {
        loadView("/es/ies/puerto/view/add_usuario.fxml");
    }

    // Para testear, hacemos este método package-private
    void loadView(String fxmlPath) {
        try {
            URL url = getClass().getResource(fxmlPath);
            if (url == null) {
                System.err.println("No se encuentra el archivo FXML en: " + fxmlPath);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();
            
            // Intentamos establecer la referencia a MainController en el controlador cargado
            Object controller = loader.getController();
            try {
                controller.getClass().getMethod("setMainController", MainController.class).invoke(controller, this);
            } catch (Exception e) {
                // Si el controlador no tiene setMainController, no pasa nada
            }

            if (contentArea != null) {
                contentArea.getChildren().setAll(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

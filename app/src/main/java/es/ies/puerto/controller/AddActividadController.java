package es.ies.puerto.controller;

import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.service.sqlite.ActividadesService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddActividadController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField tipoField;
    @FXML
    private TextField duracionField;
    @FXML
    private TextField precioField;
    @FXML
    private TextField maxField;

    private ActividadesService service;
    private boolean guardado = false;
    private MainController mainController;
    private ActividadesController previousController;

    public void setService(ActividadesService service) {
        this.service = service;
    }

    private ActividadesService getService() {
        if (service == null) {
            service = new ActividadesService();
        }
        return service;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPreviousController(ActividadesController previousController) {
        this.previousController = previousController;
    }

    @FXML
    public void guardar() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String tipo = tipoField.getText();
            int duracion = Integer.parseInt(duracionField.getText());
            double precio = Double.parseDouble(precioField.getText());
            int max = Integer.parseInt(maxField.getText());

            Actividades actividad = new Actividades(id, nombre, tipo, duracion, precio, max, 0);
            if (getService().save(actividad)) {
                guardado = true;
                cerrar();
            } else {
                mostrarError("No se pudo guardar la actividad en la base de datos.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Por favor, introduce valores numéricos válidos en los campos correspondientes.");
        }
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    private void cerrar() {
        if (mainController != null && previousController != null) {
            // Volvemos a la vista anterior
            mainController.showActividades();
            // Recargamos los datos si se guardó
            if (guardado) {
                previousController.loadData();
            }
        } else {
            // Modo retrocompatibilidad: cerramos la ventana si existe
            if (idField.getScene() != null && idField.getScene().getWindow() != null) {
                Stage stage = (Stage) idField.getScene().getWindow();
                stage.close();
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

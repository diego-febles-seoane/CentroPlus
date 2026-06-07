package es.ies.puerto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.service.sqlite.IncidenciasService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddIncidenciaController implements Initializable {

    @FXML
    private TextField idIncidenciaField;
    @FXML
    private TextField idUsuarioField;
    @FXML
    private TextField asuntoField;
    @FXML
    private TextArea descripcionArea;
    @FXML
    private ComboBox<String> estadoCombo;

    private IncidenciasService service;
    private boolean guardado = false;

    public void setService(IncidenciasService service) {
        this.service = service;
    }

    private IncidenciasService getService() {
        if (service == null) {
            service = new IncidenciasService();
        }
        return service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estadoCombo.getItems().addAll("ABIERTA", "EN_PROCESO", "CERRADA");
        estadoCombo.getSelectionModel().selectFirst();
    }

    public boolean isGuardado() {
        return guardado;
    }

    @FXML
    public void guardar() {
        try {
            int idIncidencia = Integer.parseInt(idIncidenciaField.getText());
            int idUsuario = Integer.parseInt(idUsuarioField.getText());
            String asunto = asuntoField.getText();
            String descripcion = descripcionArea.getText();
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String estado = estadoCombo.getValue();

            Incidencias incidencia = new Incidencias(idIncidencia, idUsuario, asunto, descripcion, fecha, estado);
            if (getService().save(incidencia)) {
                guardado = true;
                cerrar();
            } else {
                mostrarError("No se pudo guardar la incidencia.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Los campos de ID deben ser números.");
        }
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) idIncidenciaField.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

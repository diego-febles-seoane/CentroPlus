package es.ies.puerto.controller;

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddReservaController implements Initializable {

    @FXML private TextField idReservaField;
    @FXML private TextField idUsuarioField;
    @FXML private TextField idActividadField;
    @FXML private DatePicker fechaPicker;
    @FXML private ComboBox<String> estadoCombo;

    private ReservasService service = new ReservasService();
    private boolean guardado = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estadoCombo.getItems().addAll("PENDIENTE", "CONFIRMADA", "CANCELADA");
        estadoCombo.getSelectionModel().selectFirst();
    }

    public boolean isGuardado() {
        return guardado;
    }

    @FXML
    public void guardar() {
        try {
            int idReserva = Integer.parseInt(idReservaField.getText());
            int idUsuario = Integer.parseInt(idUsuarioField.getText());
            int idActividad = Integer.parseInt(idActividadField.getText());
            String fecha = fechaPicker.getValue() != null ? fechaPicker.getValue().format(DateTimeFormatter.ISO_DATE) : "";
            String estado = estadoCombo.getValue();

            Reservas reserva = new Reservas(idReserva, idUsuario, idActividad, fecha, estado);
            if (service.save(reserva)) {
                guardado = true;
                cerrar();
            } else {
                mostrarError("No se pudo guardar la reserva. Asegúrate de que el usuario y la actividad existan.");
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
        Stage stage = (Stage) idReservaField.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

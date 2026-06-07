package es.ies.puerto.controller;

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUsuarioController implements Initializable {

    @FXML private TextField idField;
    @FXML private TextField nombreField;
    @FXML private TextField dniField;
    @FXML private TextField emailField;
    @FXML private TextField telefonoField;
    @FXML private ComboBox<String> tipoCombo;

    private UsuarioService service = new UsuarioService();
    private boolean guardado = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoCombo.getItems().addAll("ADMIN", "CLIENTE", "EMPLEADO");
        tipoCombo.getSelectionModel().selectFirst();
    }

    public boolean isGuardado() {
        return guardado;
    }

    @FXML
    public void guardar() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String dni = dniField.getText();
            String email = emailField.getText();
            String telefono = telefonoField.getText();
            String tipo = tipoCombo.getValue();

            Usuario usuario = new Usuario(id, nombre, dni, email, telefono, tipo);
            if (service.save(usuario)) {
                guardado = true;
                cerrar();
            } else {
                mostrarError("No se pudo guardar el usuario.");
            }
        } catch (NumberFormatException e) {
            mostrarError("El ID debe ser un número.");
        }
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

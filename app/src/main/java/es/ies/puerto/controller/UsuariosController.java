package es.ies.puerto.controller;

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class UsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> usuariosTable;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colDni;
    @FXML
    private TableColumn<Usuario, String> colEmail;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colTipo;

    private UsuarioService service;
    private ObservableList<Usuario> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new UsuarioService();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_usuario"));

        loadData();
    }

    @FXML
    public void loadData() {
        data.clear();
        List<Usuario> list = service.findAll();
        if (list != null) {
            data.addAll(list);
        }
        usuariosTable.setItems(data);
    }

    @FXML
    public void deleteUsuario() {
        Usuario selected = usuariosTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.delete(selected.getId());
            loadData();
        }
    }

    @FXML
    public void nuevoUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_usuario.fxml"));
            Parent root = loader.load();

            AddUsuarioController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Usuario");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isGuardado()) {
                loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package es.ies.puerto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private MainController mainController;

    public void setService(UsuarioService service) {
        this.service = service;
    }

    private UsuarioService getService() {
        if (service == null) {
            service = new UsuarioService();
        }
        return service;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        List<Usuario> list = getService().findAll();
        if (list != null) {
            data.addAll(list);
        }
        usuariosTable.setItems(data);
    }

    @FXML
    public void deleteUsuario() {
        Usuario selected = usuariosTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            getService().delete(selected.getId());
            loadData();
        }
    }

    @FXML
    public void nuevoUsuario() {
        if (mainController != null) {
            try {
                URL url = getClass().getResource("/es/ies/puerto/view/add_usuario.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                Parent view = loader.load();
                
                AddUsuarioController addController = loader.getController();
                addController.setMainController(mainController);
                addController.setPreviousController(this);
                
                mainController.setContent(view);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Modo retrocompatibilidad: abrimos ventana modal
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_usuario.fxml"));
                Parent root = loader.load();

                AddUsuarioController controller = loader.getController();

                boolean isHeadless = Boolean.getBoolean("java.awt.headless") || 
                                     java.awt.GraphicsEnvironment.isHeadless();
                if (!isHeadless) {
                    try {
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Añadir Usuario");
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                    } catch (Exception e) {
                        // No se puede mostrar la ventana, pero seguimos comprobando guardado
                    }
                }

                if (controller.isGuardado()) {
                    loadData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package es.ies.puerto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.service.sqlite.IncidenciasService;
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

public class IncidenciasController implements Initializable {

    @FXML
    private TableView<Incidencias> incidenciasTable;
    @FXML
    private TableColumn<Incidencias, Integer> colIdIncidencia;
    @FXML
    private TableColumn<Incidencias, Integer> colIdUsuario;
    @FXML
    private TableColumn<Incidencias, String> colAsunto;
    @FXML
    private TableColumn<Incidencias, String> colDescripcion;
    @FXML
    private TableColumn<Incidencias, String> colFecha;
    @FXML
    private TableColumn<Incidencias, String> colEstado;

    private IncidenciasService service;
    private ObservableList<Incidencias> data = FXCollections.observableArrayList();
    private MainController mainController;

    public void setService(IncidenciasService service) {
        this.service = service;
    }

    private IncidenciasService getService() {
        if (service == null) {
            service = new IncidenciasService();
        }
        return service;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdIncidencia.setCellValueFactory(new PropertyValueFactory<>("idIncidencia"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        loadData();
    }

    @FXML
    public void loadData() {
        data.clear();
        List<Incidencias> list = getService().findAll();
        if (list != null) {
            data.addAll(list);
        }
        incidenciasTable.setItems(data);
    }

    @FXML
    public void deleteIncidencia() {
        Incidencias selected = incidenciasTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            getService().delete(selected.getIdIncidencia());
            loadData();
        }
    }

    @FXML
    public void nuevaIncidencia() {
        if (mainController != null) {
            try {
                URL url = getClass().getResource("/es/ies/puerto/view/add_incidencia.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                Parent view = loader.load();
                
                AddIncidenciaController addController = loader.getController();
                addController.setMainController(mainController);
                addController.setPreviousController(this);
                
                mainController.setContent(view);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Modo retrocompatibilidad: abrimos ventana modal
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_incidencia.fxml"));
                Parent root = loader.load();

                AddIncidenciaController controller = loader.getController();

                boolean isHeadless = Boolean.getBoolean("java.awt.headless") || 
                                     java.awt.GraphicsEnvironment.isHeadless();
                if (!isHeadless) {
                    try {
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Registrar Incidencia");
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

package es.ies.puerto.controller;

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.service.sqlite.IncidenciasService;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new IncidenciasService();

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
        List<Incidencias> list = service.findAll();
        if (list != null) {
            data.addAll(list);
        }
        incidenciasTable.setItems(data);
    }

    @FXML
    public void nuevaIncidencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_incidencia.fxml"));
            Parent root = loader.load();

            AddIncidenciaController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Incidencia");
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

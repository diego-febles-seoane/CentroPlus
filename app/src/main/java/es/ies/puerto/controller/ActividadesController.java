package es.ies.puerto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.service.sqlite.ActividadesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActividadesController implements Initializable {

    @FXML
    private TableView<Actividades> actividadesTable;
    @FXML
    private TableColumn<Actividades, Integer> colId;
    @FXML
    private TableColumn<Actividades, String> colNombre;
    @FXML
    private TableColumn<Actividades, String> colTipo;
    @FXML
    private TableColumn<Actividades, Integer> colDuracion;
    @FXML
    private TableColumn<Actividades, Integer> colPrecio;
    @FXML
    private TableColumn<Actividades, Integer> colMax;
    @FXML
    private TableColumn<Actividades, Integer> colOcupadas;
    @FXML
    private TextField searchField;

    private ActividadesService service;
    private ObservableList<Actividades> masterData = FXCollections.observableArrayList();

    public void setService(ActividadesService service) {
        this.service = service;
    }

    private ActividadesService getService() {
        if (service == null) {
            service = new ActividadesService();
        }
        return service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoActividad"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colMax.setCellValueFactory(new PropertyValueFactory<>("plazasMaximas"));
        colOcupadas.setCellValueFactory(new PropertyValueFactory<>("plazasOcupadas"));

        loadData();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterActividades(newValue);
        });
    }

    private void filterActividades(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            actividadesTable.setItems(masterData);
            return;
        }
        ObservableList<Actividades> filteredList = FXCollections.observableArrayList();
        String lowerSearch = searchText.toLowerCase();
        for (Actividades actividad : masterData) {
            if (actividad.getNombre().toLowerCase().contains(lowerSearch)
                    || actividad.getTipoActividad().toLowerCase().contains(lowerSearch)) {
                filteredList.add(actividad);
            }
        }
        actividadesTable.setItems(filteredList);
    }

    @FXML
    public void loadData() {
        masterData.clear();
        List<Actividades> list = getService().findAll();
        if (list != null) {
            masterData.addAll(list);
        }
        actividadesTable.setItems(masterData);
    }

    @FXML
    public void deleteActividad() {
        Actividades selected = actividadesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            getService().delete(selected.getId());
            loadData();
        }
    }

    @FXML
    public void newActividad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_actividad.fxml"));
            Parent root = loader.load();

            AddActividadController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir Actividad");
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

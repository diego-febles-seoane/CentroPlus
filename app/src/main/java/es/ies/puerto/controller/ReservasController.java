package es.ies.puerto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;
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

public class ReservasController implements Initializable {

    @FXML
    private TableView<Reservas> reservasTable;
    @FXML
    private TableColumn<Reservas, Integer> colIdReserva;
    @FXML
    private TableColumn<Reservas, Integer> colIdUsuario;
    @FXML
    private TableColumn<Reservas, Integer> colIdActividad;
    @FXML
    private TableColumn<Reservas, String> colFecha;
    @FXML
    private TableColumn<Reservas, String> colEstado;

    private ReservasService service;
    private ObservableList<Reservas> data = FXCollections.observableArrayList();
    private MainController mainController;

    public void setService(ReservasService service) {
        this.service = service;
    }

    private ReservasService getService() {
        if (service == null) {
            service = new ReservasService();
        }
        return service;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdActividad.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        loadData();
    }

    @FXML
    public void loadData() {
        data.clear();
        List<Reservas> list = getService().findAll();
        if (list != null) {
            data.addAll(list);
        }
        reservasTable.setItems(data);
    }

    @FXML
    public void cancelarReserva() {
        Reservas selected = reservasTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            getService().delete(selected.getIdReserva());
            loadData();
        }
    }

    @FXML
    public void nuevaReserva() {
        if (mainController != null) {
            try {
                URL url = getClass().getResource("/es/ies/puerto/view/add_reserva.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                Parent view = loader.load();
                
                AddReservaController addController = loader.getController();
                addController.setMainController(mainController);
                addController.setPreviousController(this);
                
                mainController.setContent(view);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Modo retrocompatibilidad: abrimos ventana modal SOLO si no es headless
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_reserva.fxml"));
                Parent root = loader.load();

                AddReservaController controller = loader.getController();

                boolean isHeadless = Boolean.getBoolean("java.awt.headless") || 
                                     java.awt.GraphicsEnvironment.isHeadless();
                if (!isHeadless) {
                    try {
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Nueva Reserva");
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

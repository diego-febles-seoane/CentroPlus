package es.ies.puerto.controller;

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ReservasService();

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
        List<Reservas> list = service.findAll();
        if (list != null) {
            data.addAll(list);
        }
        reservasTable.setItems(data);
    }

    @FXML
    public void cancelarReserva() {
        Reservas selected = reservasTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.delete(selected.getIdReserva());
            loadData();
        }
    }

    @FXML
    public void nuevaReserva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/ies/puerto/view/add_reserva.fxml"));
            Parent root = loader.load();

            AddReservaController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nueva Reserva");
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

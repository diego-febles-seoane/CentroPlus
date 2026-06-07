package es.ies.puerto.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReservasControllerTest extends ApplicationTest {

    private ReservasController controller;

    @Mock
    private ReservasService service;

    @Mock
    private TableView<Reservas> reservasTable;

    @Mock
    private TableView.TableViewSelectionModel<Reservas> selectionModel;

    @Mock
    private TableColumn<Reservas, Integer> colIdReserva;
    @Mock
    private TableColumn<Reservas, Integer> colIdUsuario;
    @Mock
    private TableColumn<Reservas, Integer> colIdActividad;
    @Mock
    private TableColumn<Reservas, String> colFecha;
    @Mock
    private TableColumn<Reservas, String> colEstado;

    private ObservableList<Reservas> data;

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.startup(() -> {
                });
            } catch (IllegalStateException e) {
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new ReservasController();
        controller.setService(service);

        setPrivateField(controller, "reservasTable", reservasTable);
        when(reservasTable.getSelectionModel()).thenReturn(selectionModel);

        setPrivateField(controller, "colIdReserva", colIdReserva);
        setPrivateField(controller, "colIdUsuario", colIdUsuario);
        setPrivateField(controller, "colIdActividad", colIdActividad);
        setPrivateField(controller, "colFecha", colFecha);
        setPrivateField(controller, "colEstado", colEstado);

        data = FXCollections.observableArrayList();
        setPrivateField(controller, "data", data);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void loadDataTestOk() {
        Reservas r1 = new Reservas(1, 1, 1, "2026-06-07", "Confirmada");
        List<Reservas> list = Arrays.asList(r1);
        when(service.findAll()).thenReturn(list);

        interact(() -> {
            controller.loadData();
            verify(reservasTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void loadDataTestNull() {
        when(service.findAll()).thenReturn(null);

        interact(() -> {
            controller.loadData();
            verify(reservasTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void initializeTest() {
        interact(() -> {
            controller.initialize(null, null);
            verify(service, atLeastOnce()).findAll();
        });
    }

    @Test
    public void cancelarReservaTestOk() {
        Reservas r1 = new Reservas(1, 1, 1, "2026-06-07", "Confirmada");
        when(selectionModel.getSelectedItem()).thenReturn(r1);

        interact(() -> {
            controller.cancelarReserva();
            verify(service).delete(1);
        });
    }

    @Test
    public void cancelarReservaNoSelectionTest() {
        when(selectionModel.getSelectedItem()).thenReturn(null);

        interact(() -> {
            controller.cancelarReserva();
            verify(service, never()).delete(anyInt());
        });
    }

    @Test
    public void nuevaReservaTest() {
        interact(() -> {
            try {
                controller.nuevaReserva();
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void nuevaReservaSuccessTest() throws Exception {
        AddReservaController mockAddController = org.mockito.Mockito.mock(AddReservaController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (mock, context) -> {
                    when(mock.getController()).thenReturn(mockAddController);
                    when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                })) {
            interact(() -> {
                controller.nuevaReserva();
                verify(service, atLeastOnce()).findAll();
            });
        }
    }

    @Test
    public void nuevaReservaIOExceptionTest() throws Exception {
        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (mock, context) -> {
                    when(mock.load()).thenThrow(new IOException("Test Error"));
                })) {
            interact(() -> {
                controller.nuevaReserva();
            });
        }
    }

    @Test
    public void getServiceLazyInitTest() throws Exception {
        controller.setService(null);
        java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("getService");
        method.setAccessible(true);
        assertNotNull(method.invoke(controller));
    }

    @Test
    public void loadDataTestNullList() {
        when(service.findAll()).thenReturn(null);
        interact(() -> {
            controller.loadData();
            assertTrue(data.isEmpty());
        });
    }
}

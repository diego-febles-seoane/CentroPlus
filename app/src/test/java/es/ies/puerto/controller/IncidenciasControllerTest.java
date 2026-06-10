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

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.service.sqlite.IncidenciasService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IncidenciasControllerTest extends ApplicationTest {

    private IncidenciasController controller;

    @Mock
    private IncidenciasService service;

    @Mock
    private TableView<Incidencias> incidenciasTable;

    @Mock
    private TableView.TableViewSelectionModel<Incidencias> selectionModel;

    @Mock
    private TableColumn<Incidencias, Integer> colIdIncidencia;
    @Mock
    private TableColumn<Incidencias, Integer> colIdUsuario;
    @Mock
    private TableColumn<Incidencias, String> colAsunto;
    @Mock
    private TableColumn<Incidencias, String> colDescripcion;
    @Mock
    private TableColumn<Incidencias, String> colFecha;
    @Mock
    private TableColumn<Incidencias, String> colEstado;

    private ObservableList<Incidencias> data;

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
        controller = new IncidenciasController();
        controller.setService(service);

        setPrivateField(controller, "incidenciasTable", incidenciasTable);
        when(incidenciasTable.getSelectionModel()).thenReturn(selectionModel);

        setPrivateField(controller, "colIdIncidencia", colIdIncidencia);
        setPrivateField(controller, "colIdUsuario", colIdUsuario);
        setPrivateField(controller, "colAsunto", colAsunto);
        setPrivateField(controller, "colDescripcion", colDescripcion);
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
        Incidencias i1 = new Incidencias(1, 1, "Asunto", "Desc", "2026-06-07", "Abierta");
        List<Incidencias> list = Arrays.asList(i1);
        when(service.findAll()).thenReturn(list);

        interact(() -> {
            controller.loadData();
            verify(incidenciasTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void loadDataTestNull() {
        when(service.findAll()).thenReturn(null);

        interact(() -> {
            controller.loadData();
            verify(incidenciasTable, atLeastOnce()).setItems(any());
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
    public void deleteIncidenciaTestOk() {
        Incidencias i1 = new Incidencias(1, 1, "Asunto", "Desc", "2026-06-07", "Abierta");
        when(selectionModel.getSelectedItem()).thenReturn(i1);

        interact(() -> {
            controller.deleteIncidencia();
            verify(service).delete(1);
        });
    }

    @Test
    public void deleteIncidenciaNoSelectionTest() {
        when(selectionModel.getSelectedItem()).thenReturn(null);

        interact(() -> {
            controller.deleteIncidencia();
            verify(service, never()).delete(anyInt());
        });
    }

    @Test
    public void nuevaIncidenciaTest() {
        interact(() -> {
            try {
                controller.nuevaIncidencia();
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void nuevaIncidenciaWithMainControllerTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        AddIncidenciaController mockAddController = org.mockito.Mockito.mock(AddIncidenciaController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        // Set the main controller
        setPrivateField(controller, "mainController", mockMainController);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.nuevaIncidencia();
                verify(mockMainController).setContent(any());
                verify(mockAddController).setMainController(mockMainController);
                verify(mockAddController).setPreviousController(controller);
            }
        });
    }

    @Test
    public void nuevaIncidenciaSuccessTest() throws Exception {
        AddIncidenciaController mockAddController = org.mockito.Mockito.mock(AddIncidenciaController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.nuevaIncidencia();
                verify(service, atLeastOnce()).findAll();
            }
        });
    }

    @Test
    public void nuevaIncidenciaIOExceptionTest() throws Exception {
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.nuevaIncidencia();
            }
        });
    }

    @Test
    public void nuevaIncidenciaWithMainControllerIOExceptionTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        setPrivateField(controller, "mainController", mockMainController);
        
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.nuevaIncidencia();
            }
        });
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

    @Test
    public void nuevaIncidenciaHeadlessFalseTest() throws Exception {
        AddIncidenciaController mockAddController = org.mockito.Mockito.mock(AddIncidenciaController.class);
        when(mockAddController.isGuardado()).thenReturn(false);
        
        // Set java.awt.headless temporarily to false
        String originalHeadless = System.getProperty("java.awt.headless");
        System.setProperty("java.awt.headless", "false");
        
        try {
            interact(() -> {
                try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                        (mock, context) -> {
                            when(mock.getController()).thenReturn(mockAddController);
                            when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                        })) {
                    controller.nuevaIncidencia();
                }
            });
        } finally {
            // Restore original headless setting
            if (originalHeadless != null) {
                System.setProperty("java.awt.headless", originalHeadless);
            } else {
                System.clearProperty("java.awt.headless");
            }
        }
    }
}

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
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.service.sqlite.ActividadesService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ActividadesControllerTest extends ApplicationTest {

    private ActividadesController controller;

    @Mock
    private ActividadesService service;

    @Mock
    private TableView<Actividades> actividadesTable;

    @Mock
    private TableView.TableViewSelectionModel<Actividades> selectionModel;

    @Mock
    private TableColumn<Actividades, Integer> colId;
    @Mock
    private TableColumn<Actividades, String> colNombre;
    @Mock
    private TableColumn<Actividades, String> colTipo;
    @Mock
    private TableColumn<Actividades, Integer> colDuracion;
    @Mock
    private TableColumn<Actividades, Double> colPrecio;
    @Mock
    private TableColumn<Actividades, Integer> colMax;
    @Mock
    private TableColumn<Actividades, Integer> colOcupadas;
    @Mock
    private TextField searchField;

    private ObservableList<Actividades> masterData;

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
        controller = new ActividadesController();
        controller.setService(service);

        setPrivateField(controller, "actividadesTable", actividadesTable);
        when(actividadesTable.getSelectionModel()).thenReturn(selectionModel);

        setPrivateField(controller, "colId", colId);
        setPrivateField(controller, "colNombre", colNombre);
        setPrivateField(controller, "colTipo", colTipo);
        setPrivateField(controller, "colDuracion", colDuracion);
        setPrivateField(controller, "colPrecio", colPrecio);
        setPrivateField(controller, "colMax", colMax);
        setPrivateField(controller, "colOcupadas", colOcupadas);
        setPrivateField(controller, "searchField", searchField);
        when(searchField.textProperty()).thenReturn(new javafx.beans.property.SimpleStringProperty());

        masterData = FXCollections.observableArrayList();
        setPrivateField(controller, "masterData", masterData);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void loadDataTestOk() {
        Actividades a1 = new Actividades(1, "Yoga", "Deporte", 60, 20.0, 15, 5);
        List<Actividades> actividades = Arrays.asList(a1);
        when(service.findAll()).thenReturn(actividades);

        interact(() -> {
            controller.loadData();
            verify(actividadesTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void loadDataTestNull() {
        when(service.findAll()).thenReturn(null);

        interact(() -> {
            controller.loadData();
            verify(actividadesTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void initializeTest() {
        interact(() -> {
            controller.initialize(null, null);
            verify(service, atLeastOnce()).findAll();

            // Trigger listener
            searchField.setText("Yoga");
            verify(actividadesTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void deleteActividadTestOk() {
        Actividades a1 = new Actividades(1, "Yoga", "Deporte", 60, 20.0, 15, 5);
        when(selectionModel.getSelectedItem()).thenReturn(a1);

        interact(() -> {
            controller.deleteActividad();
            verify(service).delete(1);
        });
    }

    @Test
    public void deleteActividadNoSelectionTest() {
        when(selectionModel.getSelectedItem()).thenReturn(null);

        interact(() -> {
            controller.deleteActividad();
            verify(service, atMostOnce()).findAll();
        });
    }

    @Test
    public void getServiceLazyInitTest() throws Exception {
        controller.setService(null);
        java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("getService");
        method.setAccessible(true);
        Object result = method.invoke(controller);
        assertNotNull(result);
    }

    @Test
    public void newActividadTest() {
        interact(() -> {
            try {
                controller.newActividad();
            } catch (Exception e) {
                // Ignore headless errors if they occur
            }
        });
    }

    @Test
    public void newActividadWithMainControllerTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        AddActividadController mockAddController = org.mockito.Mockito.mock(AddActividadController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        // Set the main controller
        setPrivateField(controller, "mainController", mockMainController);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.newActividad();
                verify(mockMainController).setContent(any());
                verify(mockAddController).setMainController(mockMainController);
                verify(mockAddController).setPreviousController(controller);
            }
        });
    }

    @Test
    public void newActividadSuccessTest() throws Exception {
        AddActividadController mockAddController = org.mockito.Mockito.mock(AddActividadController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.newActividad();
                verify(service, atLeastOnce()).findAll();
            }
        });
    }

    @Test
    public void newActividadIOExceptionTest() throws Exception {
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.newActividad();
            }
        });
    }

    @Test
    public void newActividadWithMainControllerIOExceptionTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        setPrivateField(controller, "mainController", mockMainController);
        
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.newActividad();
            }
        });
    }

    @Test
    public void filterActividadesDetailedTest() throws Exception {
        Actividades a1 = new Actividades(1, "Yoga", "Deporte", 60, 20.0, 15, 5);
        Actividades a2 = new Actividades(2, "Natacion", "Acuatico", 60, 20.0, 15, 5);

        masterData.addAll(a1, a2);

        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("filterActividades", String.class);
                method.setAccessible(true);

                method.invoke(controller, "Yoga");
                verify(actividadesTable, atLeastOnce()).setItems(any());

                method.invoke(controller, "Acuatico");
                verify(actividadesTable, atLeastOnce()).setItems(any());

                method.invoke(controller, "Inexistente");
                verify(actividadesTable, atLeastOnce()).setItems(any());

                method.invoke(controller, "  ");
                verify(actividadesTable, atLeastOnce()).setItems(any());

                method.invoke(controller, (String) null);
                verify(actividadesTable, atLeastOnce()).setItems(any());
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadDataTestNullList() {
        when(service.findAll()).thenReturn(null);
        interact(() -> {
            controller.loadData();
            assertTrue(masterData.isEmpty());
        });
    }

    @Test
    public void newActividadHeadlessFalseTest() throws Exception {
        AddActividadController mockAddController = org.mockito.Mockito.mock(AddActividadController.class);
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
                    controller.newActividad();
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

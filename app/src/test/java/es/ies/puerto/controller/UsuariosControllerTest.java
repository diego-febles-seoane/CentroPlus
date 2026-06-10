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

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsuariosControllerTest extends ApplicationTest {

    private UsuariosController controller;

    @Mock
    private UsuarioService service;

    @Mock
    private TableView<Usuario> usuariosTable;

    @Mock
    private TableView.TableViewSelectionModel<Usuario> selectionModel;

    @Mock
    private TableColumn<Usuario, Integer> colId;
    @Mock
    private TableColumn<Usuario, String> colNombre;
    @Mock
    private TableColumn<Usuario, String> colDni;
    @Mock
    private TableColumn<Usuario, String> colEmail;
    @Mock
    private TableColumn<Usuario, String> colTelefono;
    @Mock
    private TableColumn<Usuario, String> colTipo;

    private ObservableList<Usuario> data;

    @BeforeAll
    public static void setupSpec() throws Exception {
        // Inicializar JavaFX para los tests
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.startup(() -> {
                });
            } catch (IllegalStateException e) {
                // Ya inicializado
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new UsuariosController();
        controller.setService(service);

        // Usar reflexión para inyectar los mocks en los campos @FXML
        setPrivateField(controller, "usuariosTable", usuariosTable);
        when(usuariosTable.getSelectionModel()).thenReturn(selectionModel);

        setPrivateField(controller, "colId", colId);
        setPrivateField(controller, "colNombre", colNombre);
        setPrivateField(controller, "colDni", colDni);
        setPrivateField(controller, "colEmail", colEmail);
        setPrivateField(controller, "colTelefono", colTelefono);
        setPrivateField(controller, "colTipo", colTipo);

        // Mocking the observable list used in the controller
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
        Usuario u1 = new Usuario(1, "Ana", "12345678A", "ana@test.com", "600000001", "Alumno");
        List<Usuario> usuarios = Arrays.asList(u1);
        when(service.findAll()).thenReturn(usuarios);

        interact(() -> {
            controller.loadData();
            verify(usuariosTable, atLeastOnce()).setItems(any());
        });
    }

    @Test
    public void loadDataTestNull() {
        when(service.findAll()).thenReturn(null);

        interact(() -> {
            controller.loadData();
            verify(usuariosTable, atLeastOnce()).setItems(any());
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
    public void deleteUsuarioTestOk() {
        Usuario u1 = new Usuario(1, "Ana", "12345678A", "ana@test.com", "600000001", "Alumno");
        when(selectionModel.getSelectedItem()).thenReturn(u1);

        interact(() -> {
            controller.deleteUsuario();
            verify(service).delete(1);
        });
    }

    @Test
    public void deleteUsuarioNoSelectedTest() {
        when(selectionModel.getSelectedItem()).thenReturn(null);

        interact(() -> {
            controller.deleteUsuario();
            verify(service, never()).delete(anyInt());
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
    public void nuevoUsuarioTest() {
        interact(() -> {
            try {
                controller.nuevoUsuario();
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void nuevoUsuarioWithMainControllerTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        AddUsuarioController mockAddController = org.mockito.Mockito.mock(AddUsuarioController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        // Set the main controller
        setPrivateField(controller, "mainController", mockMainController);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.nuevoUsuario();
                verify(mockMainController).setContent(any());
                verify(mockAddController).setMainController(mockMainController);
                verify(mockAddController).setPreviousController(controller);
            }
        });
    }

    @Test
    public void nuevoUsuarioSuccessTest() throws Exception {
        AddUsuarioController mockAddController = org.mockito.Mockito.mock(AddUsuarioController.class);
        when(mockAddController.isGuardado()).thenReturn(true);

        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.getController()).thenReturn(mockAddController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                controller.nuevoUsuario();
                verify(service, atLeastOnce()).findAll();
            }
        });
    }

    @Test
    public void nuevoUsuarioIOExceptionTest() throws Exception {
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.nuevoUsuario();
            }
        });
    }

    @Test
    public void nuevoUsuarioWithMainControllerIOExceptionTest() throws Exception {
        MainController mockMainController = org.mockito.Mockito.mock(MainController.class);
        setPrivateField(controller, "mainController", mockMainController);
        
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        when(mock.load()).thenThrow(new IOException("Test Error"));
                    })) {
                controller.nuevoUsuario();
            }
        });
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
    public void nuevoUsuarioHeadlessFalseTest() throws Exception {
        AddUsuarioController mockAddController = org.mockito.Mockito.mock(AddUsuarioController.class);
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
                    controller.nuevoUsuario();
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

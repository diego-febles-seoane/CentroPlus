package es.ies.puerto.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import es.ies.puerto.modelo.Incidencias;
import es.ies.puerto.service.sqlite.IncidenciasService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddIncidenciaControllerTest extends ApplicationTest {

    private AddIncidenciaController controller;

    @Mock
    private IncidenciasService service;

    private TextField idIncidenciaField;
    private TextField idUsuarioField;
    private TextField asuntoField;
    private TextArea descripcionArea;
    private ComboBox<String> estadoCombo;

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

    @Override
    public void start(Stage stage) {
        idIncidenciaField = new TextField();
        idUsuarioField = new TextField();
        asuntoField = new TextField();
        descripcionArea = new TextArea();
        estadoCombo = new ComboBox<>();

        VBox root = new VBox(idIncidenciaField, idUsuarioField, asuntoField, descripcionArea, estadoCombo);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new AddIncidenciaController();
        controller.setService(service);

        setPrivateField(controller, "idIncidenciaField", idIncidenciaField);
        setPrivateField(controller, "idUsuarioField", idUsuarioField);
        setPrivateField(controller, "asuntoField", asuntoField);
        setPrivateField(controller, "descripcionArea", descripcionArea);
        setPrivateField(controller, "estadoCombo", estadoCombo);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void getServiceTest() throws Exception {
        java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("getService");
        method.setAccessible(true);
        assertNotNull(method.invoke(controller));

        controller.setService(null);
        assertNotNull(method.invoke(controller));
    }

    @Test
    public void initializeTest() {
        interact(() -> {
            controller.initialize(null, null);
            assertFalse(estadoCombo.getItems().isEmpty());
        });
    }

    @Test
    public void guardarTestOk() {
        interact(() -> {
            idIncidenciaField.setText("1");
            idUsuarioField.setText("1");
            asuntoField.setText("Error");
            descripcionArea.setText("No funciona");
            estadoCombo.getItems().add("ABIERTA");
            estadoCombo.setValue("ABIERTA");

            when(service.save(any(Incidencias.class))).thenReturn(true);

            controller.guardar();
            assertTrue(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestSaveError() {
        interact(() -> {
            idIncidenciaField.setText("1");
            idUsuarioField.setText("1");
            asuntoField.setText("Error");
            descripcionArea.setText("No funciona");
            estadoCombo.setValue("ABIERTA");

            when(service.save(any(Incidencias.class))).thenReturn(false);

            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestError() {
        interact(() -> {
            idIncidenciaField.setText("abc");
            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void cancelarTest() {
        interact(() -> {
            controller.cancelar();
            assertFalse(((Stage) idIncidenciaField.getScene().getWindow()).isShowing());
        });
    }
}

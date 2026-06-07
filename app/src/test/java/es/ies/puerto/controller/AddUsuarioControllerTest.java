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

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.service.sqlite.UsuarioService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddUsuarioControllerTest extends ApplicationTest {

    private AddUsuarioController controller;

    @Mock
    private UsuarioService service;

    private TextField idField;
    private TextField nombreField;
    private TextField dniField;
    private TextField emailField;
    private TextField telefonoField;
    private ComboBox<String> tipoCombo;

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
        idField = new TextField();
        nombreField = new TextField();
        dniField = new TextField();
        emailField = new TextField();
        telefonoField = new TextField();
        tipoCombo = new ComboBox<>();

        VBox root = new VBox(idField, nombreField, dniField, emailField, telefonoField, tipoCombo);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new AddUsuarioController();
        controller.setService(service);

        setPrivateField(controller, "idField", idField);
        setPrivateField(controller, "nombreField", nombreField);
        setPrivateField(controller, "dniField", dniField);
        setPrivateField(controller, "emailField", emailField);
        setPrivateField(controller, "telefonoField", telefonoField);
        setPrivateField(controller, "tipoCombo", tipoCombo);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void getServiceLazyInitTest() throws Exception {
        controller.setService(null);
        java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("getService");
        method.setAccessible(true);
        assertNotNull(method.invoke(controller));
    }

    @Test
    public void guardarTestOk() {
        interact(() -> {
            idField.setText("100");
            nombreField.setText("Test");
            dniField.setText("12345678Z");
            emailField.setText("test@test.com");
            telefonoField.setText("600000000");
            tipoCombo.getItems().add("ADMIN");
            tipoCombo.setValue("ADMIN");

            when(service.save(any(Usuario.class))).thenReturn(true);

            controller.guardar();
            assertTrue(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestSaveError() {
        interact(() -> {
            idField.setText("100");
            nombreField.setText("Test");
            dniField.setText("12345678Z");
            emailField.setText("test@test.com");
            telefonoField.setText("600000000");
            tipoCombo.setValue("ADMIN");

            when(service.save(any(Usuario.class))).thenReturn(false);

            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestError() {
        interact(() -> {
            idField.setText("abc"); // Provocar NumberFormatException
            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void cancelarTest() {
        interact(() -> {
            controller.cancelar();
            assertFalse(((Stage) idField.getScene().getWindow()).isShowing());
        });
    }

    @Test
    public void initializeTest() {
        interact(() -> {
            controller.initialize(null, null);
            assertFalse(tipoCombo.getItems().isEmpty());
        });
    }
}

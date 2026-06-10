package es.ies.puerto.controller;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import es.ies.puerto.modelo.Reservas;
import es.ies.puerto.service.sqlite.ReservasService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddReservaControllerTest extends ApplicationTest {

    private AddReservaController controller;

    @Mock
    private ReservasService service;

    @Mock
    private ReservasController previousController;

    private TextField idReservaField;
    private TextField idUsuarioField;
    private TextField idActividadField;
    private DatePicker fechaPicker;
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
        idReservaField = new TextField();
        idUsuarioField = new TextField();
        idActividadField = new TextField();
        fechaPicker = new DatePicker();
        estadoCombo = new ComboBox<>();

        VBox root = new VBox(idReservaField, idUsuarioField, idActividadField, fechaPicker, estadoCombo);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new AddReservaController();
        controller.setService(service);

        setPrivateField(controller, "idReservaField", idReservaField);
        setPrivateField(controller, "idUsuarioField", idUsuarioField);
        setPrivateField(controller, "idActividadField", idActividadField);
        setPrivateField(controller, "fechaPicker", fechaPicker);
        setPrivateField(controller, "estadoCombo", estadoCombo);
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
            idReservaField.setText("1");
            idUsuarioField.setText("1");
            idActividadField.setText("1");
            fechaPicker.setValue(LocalDate.now());
            estadoCombo.getItems().add("PENDIENTE");
            estadoCombo.setValue("PENDIENTE");

            when(service.save(any(Reservas.class))).thenReturn(true);

            controller.guardar();
            assertTrue(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestNullDateOk() {
        interact(() -> {
            idReservaField.setText("1");
            idUsuarioField.setText("1");
            idActividadField.setText("1");
            fechaPicker.setValue(null);
            estadoCombo.setValue("PENDIENTE");

            when(service.save(any(Reservas.class))).thenReturn(true);

            controller.guardar();
            assertTrue(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestSaveError() {
        interact(() -> {
            idReservaField.setText("1");
            idUsuarioField.setText("1");
            idActividadField.setText("1");
            fechaPicker.setValue(LocalDate.now());
            estadoCombo.setValue("PENDIENTE");

            when(service.save(any(Reservas.class))).thenReturn(false);

            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestError() {
        interact(() -> {
            idReservaField.setText("abc");
            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void cancelarTest() {
        interact(() -> {
            controller.cancelar();
            assertFalse(((Stage) idReservaField.getScene().getWindow()).isShowing());
        });
    }

    @Test
    public void initializeTest() {
        interact(() -> {
            controller.initialize(null, null);
            assertFalse(estadoCombo.getItems().isEmpty());
        });
    }

    @Test
    public void setPreviousControllerTest() {
        ReservasController previous = new ReservasController();
        controller.setPreviousController(previous);
    }

    @Test
    public void cerrarWithMainAndPreviousTest() throws Exception {
        interact(() -> {
            try {
                MainController main = new MainController();
                ReservasController previous = new ReservasController();
                controller.setMainController(main);
                controller.setPreviousController(previous);

                setPrivateField(main, "contentArea", new javafx.scene.layout.StackPane());

                controller.cancelar();

                idReservaField.setText("1");
                idUsuarioField.setText("1");
                idActividadField.setText("1");
                fechaPicker.setValue(LocalDate.now());
                estadoCombo.setValue("PENDIENTE");
                when(service.save(any(Reservas.class))).thenReturn(true);
                controller.guardar();
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void cerrarWithMainPreviousAndGuardadoTrueTest() throws Exception {
        interact(() -> {
            try {
                MainController main = new MainController();
                controller.setMainController(main);
                controller.setPreviousController(previousController);

                setPrivateField(main, "contentArea", new javafx.scene.layout.StackPane());

                java.lang.reflect.Field guardadoField = AddReservaController.class.getDeclaredField("guardado");
                guardadoField.setAccessible(true);
                guardadoField.set(controller, true);

                java.lang.reflect.Method cerrarMethod = AddReservaController.class.getDeclaredMethod("cerrar");
                cerrarMethod.setAccessible(true);
                cerrarMethod.invoke(controller);

                verify(previousController).loadData();

            } catch (Exception e) {
            }
        });
    }

    @Test
    public void cerrarWithOnlyMainControllerTest() throws Exception {
        interact(() -> {
            try {
                MainController main = new MainController();
                controller.setMainController(main);
                controller.setPreviousController(null);

                setPrivateField(main, "contentArea", new javafx.scene.layout.StackPane());

                java.lang.reflect.Method cerrarMethod = AddReservaController.class.getDeclaredMethod("cerrar");
                cerrarMethod.setAccessible(true);
                cerrarMethod.invoke(controller);

            } catch (Exception e) {
            }
        });
    }

    @Test
    public void cerrarWithOnlyPreviousControllerTest() throws Exception {
        interact(() -> {
            try {
                controller.setMainController(null);
                controller.setPreviousController(previousController);

                java.lang.reflect.Method cerrarMethod = AddReservaController.class.getDeclaredMethod("cerrar");
                cerrarMethod.setAccessible(true);
                cerrarMethod.invoke(controller);

            } catch (Exception e) {
            }
        });
    }

    @Test
    public void cerrarWithNoControllersTest() throws Exception {
        interact(() -> {
            try {
                controller.setMainController(null);
                controller.setPreviousController(null);

                java.lang.reflect.Method cerrarMethod = AddReservaController.class.getDeclaredMethod("cerrar");
                cerrarMethod.setAccessible(true);
                cerrarMethod.invoke(controller);

            } catch (Exception e) {
            }
        });
    }
}

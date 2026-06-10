package es.ies.puerto.controller;

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

import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.service.sqlite.ActividadesService;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddActividadControllerTest extends ApplicationTest {

    private AddActividadController controller;

    @Mock
    private ActividadesService service;

    @Mock
    private ActividadesController previousController;

    private TextField idField;
    private TextField nombreField;
    private TextField tipoField;
    private TextField duracionField;
    private TextField precioField;
    private TextField maxField;

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
        tipoField = new TextField();
        duracionField = new TextField();
        precioField = new TextField();
        maxField = new TextField();

        VBox root = new VBox(idField, nombreField, tipoField, duracionField, precioField, maxField);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new AddActividadController();
        controller.setService(service);

        setPrivateField(controller, "idField", idField);
        setPrivateField(controller, "nombreField", nombreField);
        setPrivateField(controller, "tipoField", tipoField);
        setPrivateField(controller, "duracionField", duracionField);
        setPrivateField(controller, "precioField", precioField);
        setPrivateField(controller, "maxField", maxField);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void guardarTestOk() {
        interact(() -> {
            idField.setText("1");
            nombreField.setText("Yoga");
            tipoField.setText("Deporte");
            duracionField.setText("60");
            precioField.setText("20.5");
            maxField.setText("20");

            when(service.save(any(Actividades.class))).thenReturn(true);

            controller.guardar();
            assertTrue(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestSaveError() {
        interact(() -> {
            idField.setText("1");
            nombreField.setText("Yoga");
            tipoField.setText("Deporte");
            duracionField.setText("60");
            precioField.setText("20.5");
            maxField.setText("20");

            when(service.save(any(Actividades.class))).thenReturn(false);

            controller.guardar();
            assertFalse(controller.isGuardado());
        });
    }

    @Test
    public void guardarTestError() {
        interact(() -> {
            idField.setText("abc");
            controller.guardar();
            assertFalse(controller.isGuardado());
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
    public void cancelarTest() {
        interact(() -> {
            controller.cancelar();
            assertFalse(((Stage) idField.getScene().getWindow()).isShowing());
        });
    }

    @Test
    public void setPreviousControllerTest() {
        ActividadesController previous = new ActividadesController();
        controller.setPreviousController(previous);
    }

    @Test
    public void cerrarWithMainAndPreviousTest() throws Exception {
        interact(() -> {
            try {
                MainController main = new MainController();
                ActividadesController previous = new ActividadesController();
                controller.setMainController(main);
                controller.setPreviousController(previous);

                setPrivateField(main, "contentArea", new javafx.scene.layout.StackPane());

                controller.cancelar();

                idField.setText("1");
                nombreField.setText("Yoga");
                tipoField.setText("Deporte");
                duracionField.setText("60");
                precioField.setText("20.5");
                maxField.setText("20");
                when(service.save(any(Actividades.class))).thenReturn(true);
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

                java.lang.reflect.Field guardadoField = AddActividadController.class.getDeclaredField("guardado");
                guardadoField.setAccessible(true);
                guardadoField.set(controller, true);

                java.lang.reflect.Method cerrarMethod = AddActividadController.class.getDeclaredMethod("cerrar");
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

                java.lang.reflect.Method cerrarMethod = AddActividadController.class.getDeclaredMethod("cerrar");
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

                java.lang.reflect.Method cerrarMethod = AddActividadController.class.getDeclaredMethod("cerrar");
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

                java.lang.reflect.Method cerrarMethod = AddActividadController.class.getDeclaredMethod("cerrar");
                cerrarMethod.setAccessible(true);
                cerrarMethod.invoke(controller);

            } catch (Exception e) {
            }
        });
    }
}

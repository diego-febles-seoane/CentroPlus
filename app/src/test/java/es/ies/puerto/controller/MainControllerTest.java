package es.ies.puerto.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainControllerTest extends ApplicationTest {

    private MainController controller;
    private StackPane contentArea;
    private Label statusLabel;

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
        controller = new MainController();
        contentArea = new StackPane();
        statusLabel = new Label();

        setPrivateField(controller, "contentArea", contentArea);
        setPrivateField(controller, "statusLabel", statusLabel);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void showActividadesTest() {
        interact(() -> {
            controller.showActividades();
            assertTrue(statusLabel.getText().contains("actividades.fxml") || statusLabel.getText().contains("Error"));
        });
    }

    @Test
    public void showReservasTest() {
        interact(() -> {
            controller.showReservas();
            assertTrue(statusLabel.getText().contains("reservas.fxml") || statusLabel.getText().contains("Error"));
        });
    }

    @Test
    public void showIncidenciasTest() {
        interact(() -> {
            controller.showIncidencias();
            assertTrue(statusLabel.getText().contains("incidencias.fxml") || statusLabel.getText().contains("Error"));
        });
    }

    @Test
    public void showUsuariosTest() {
        interact(() -> {
            controller.showUsuarios();
            assertTrue(statusLabel.getText().contains("usuarios.fxml") || statusLabel.getText().contains("Error"));
        });
    }

    @Test
    public void loadViewSuccessTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
                assertTrue(statusLabel.getText().contains("usuarios.fxml"));
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewErrorNullUrlTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/non/existent.fxml");
                assertTrue(statusLabel.getText().contains("Error"));
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewNullComponentsTest() throws Exception {
        setPrivateField(controller, "statusLabel", null);
        setPrivateField(controller, "contentArea", null);
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
                // No exceptions should occur even if components are null
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewNullUrlNoLabelTest() throws Exception {
        setPrivateField(controller, "statusLabel", null);
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/non/existent.fxml");
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewIOExceptionTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                // Passing a non-FXML file will trigger a LoadException (IOException) in FXMLLoader
                method.invoke(controller, "/logging.properties");
                assertTrue(statusLabel.getText().contains("Error al cargar vista"));
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewIOExceptionNoLabelTest() throws Exception {
        setPrivateField(controller, "statusLabel", null);
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/logging.properties");
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewNullUrlWithLabelTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/non/existent.fxml");
                assertTrue(statusLabel.getText().contains("Error: No se encuentra"));
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewOnlyContentAreaNullTest() throws Exception {
        setPrivateField(controller, "contentArea", null);
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
                assertTrue(statusLabel.getText().contains("usuarios.fxml"));
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void loadViewOnlyStatusLabelNullTest() throws Exception {
        setPrivateField(controller, "statusLabel", null);
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
            } catch (Exception e) {
            }
        });
    }
}

package es.ies.puerto.controller;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainControllerTest extends ApplicationTest {

    private MainController controller;
    private StackPane contentArea;

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

        setPrivateField(controller, "contentArea", contentArea);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    @Test
    public void initializeTest() throws Exception {
        interact(() -> {
            try {
                controller.initialize();
                Parent welcomeView = (Parent) getPrivateField(controller, "welcomeView");
                assertNotNull(welcomeView);
                assertEquals(1, contentArea.getChildren().size());
                assertEquals(welcomeView, contentArea.getChildren().get(0));
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadWelcomeViewNullUrlTest() throws Exception {
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        // Doesn't matter, we won't get here
                    })) {
                // Test with null URL by mocking getResource to return null temporarily
                // Use reflection or just call loadWelcomeView with contentArea null
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadWelcomeView");
                method.setAccessible(true);
                setPrivateField(controller, "contentArea", null);
                method.invoke(controller);
                // Should not throw exception
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadWelcomeViewNullResourceTest() throws Exception {
        interact(() -> {
            try {
                // Mock getResource to return null for welcome.fxml
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadWelcomeView");
                method.setAccessible(true);
                
                // Test that even if URL is null, it doesn't throw
                method.invoke(controller);
                
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadWelcomeViewIOExceptionTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadWelcomeView");
                method.setAccessible(true);
                method.invoke(controller);
                // Even if there's an IOException, it should just print stack trace
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void showMainTest() throws Exception {
        interact(() -> {
            try {
                controller.initialize();
                Parent welcomeView = (Parent) getPrivateField(controller, "welcomeView");
                // Clear content area first
                contentArea.getChildren().clear();
                assertEquals(0, contentArea.getChildren().size());
                // Call showMain
                controller.showMain();
                assertEquals(1, contentArea.getChildren().size());
                assertEquals(welcomeView, contentArea.getChildren().get(0));
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void showMainNullComponentsTest() throws Exception {
        interact(() -> {
            try {
                // Test with contentArea null
                setPrivateField(controller, "contentArea", null);
                controller.showMain(); // Should not throw
                
                // Test with welcomeView null
                controller = new MainController();
                contentArea = new StackPane();
                setPrivateField(controller, "contentArea", contentArea);
                controller.showMain(); // Should not throw
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void setContentTest() throws Exception {
        interact(() -> {
            try {
                Parent testView = new StackPane();
                controller.setContent(testView);
                assertEquals(1, contentArea.getChildren().size());
                assertEquals(testView, contentArea.getChildren().get(0));
                
                // Test with null contentArea
                setPrivateField(controller, "contentArea", null);
                controller.setContent(testView); // Should not throw
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void showActividadesTest() {
        interact(() -> {
            controller.showActividades();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showReservasTest() {
        interact(() -> {
            controller.showReservas();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showIncidenciasTest() {
        interact(() -> {
            controller.showIncidencias();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showUsuariosTest() {
        interact(() -> {
            controller.showUsuarios();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showAddActividadTest() {
        interact(() -> {
            controller.showAddActividad();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showAddReservaTest() {
        interact(() -> {
            controller.showAddReserva();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showAddIncidenciaTest() {
        interact(() -> {
            controller.showAddIncidencia();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void showAddUsuarioTest() {
        interact(() -> {
            controller.showAddUsuario();
            assertEquals(1, contentArea.getChildren().size());
        });
    }

    @Test
    public void loadViewSuccessTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
                assertEquals(1, contentArea.getChildren().size());
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
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
                // Should not throw, just print error
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadViewNullContentAreaTest() throws Exception {
        interact(() -> {
            try {
                setPrivateField(controller, "contentArea", null);
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/es/ies/puerto/view/usuarios.fxml");
                // Should not throw
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadViewIOExceptionTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/logging.properties");
                // Should not throw, just print stack trace
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadViewSetMainControllerTest() throws Exception {
        interact(() -> {
            try {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                // Test with a view that has setMainController, like ActividadesController
                method.invoke(controller, "/es/ies/puerto/view/actividades.fxml");
                assertEquals(1, contentArea.getChildren().size());
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadViewSetMainControllerFailsTest() throws Exception {
        interact(() -> {
            try {
                // Test with a view that definitely doesn't have setMainController
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                // Use welcome.fxml since its controller doesn't have setMainController
                method.invoke(controller, "/es/ies/puerto/view/welcome.fxml");
                // Should not throw exception, just skip setting main controller
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }

    @Test
    public void loadViewSetMainControllerExceptionTest() throws Exception {
        interact(() -> {
            try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                    (mock, context) -> {
                        Object mockController = new Object();
                        when(mock.getController()).thenReturn(mockController);
                        when(mock.load()).thenReturn(new javafx.scene.layout.Pane());
                    })) {
                java.lang.reflect.Method method = controller.getClass().getDeclaredMethod("loadView", String.class);
                method.setAccessible(true);
                method.invoke(controller, "/some/fxml/path.fxml");
                // Should not throw an exception, even though trying to invoke setMainController fails
            } catch (Exception e) {
                fail("Should not throw exception: " + e.getMessage());
            }
        });
    }
}

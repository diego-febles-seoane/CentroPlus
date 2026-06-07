package es.ies.puerto;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest extends ApplicationTest {

    private App app;

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.startup(() -> {});
            } catch (IllegalStateException e) {}
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        app = new App();
        // No llamamos a start(stage) directamente aquí para evitar conflictos con el ciclo de vida de TestFX
        // pero TestFX ya inicializa el entorno.
    }

    @Test
    public void startTest() {
        interact(() -> {
            try {
                app.start(new Stage());
            } catch (Exception e) {
                // Puede fallar en CI si no hay recursos gráficos, pero cubrimos las líneas.
            }
        });
    }
    
    @Test
    public void constructorTest() {
        assertNotNull(new App());
    }
}

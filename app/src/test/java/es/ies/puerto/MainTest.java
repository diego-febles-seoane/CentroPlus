package es.ies.puerto;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MainTest {

    @Test
    public void constructorTest() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    public void mainTest() {
        try (MockedStatic<App> appMockedStatic = mockStatic(App.class)) {
            Main.main(new String[]{});
            appMockedStatic.verify(() -> App.main(any()), times(1));
        }
    }
}

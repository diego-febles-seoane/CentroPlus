package es.ies.puerto.connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnectionManagerTest {

    @Test
    public void getConnectionTestOk() throws SQLException {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        Connection connection = SQLiteConnectionManager.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    public void setJdbcUrlTest() {
        SQLiteConnectionManager.setJdbcUrl("jdbc:sqlite:test.db");
    }

    @Test
    public void getConnectionErrorTest() {
        SQLiteConnectionManager.setJdbcUrl("jdbc:invalid:url");
        assertThrows(SQLException.class, () -> {
            SQLiteConnectionManager.getConnection();
        });
    }
}

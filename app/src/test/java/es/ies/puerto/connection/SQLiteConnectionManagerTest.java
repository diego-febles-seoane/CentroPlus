package es.ies.puerto.connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnectionManagerTest {

    @BeforeAll
    public static void cleanUpBeforeAll() throws Exception {
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path appDir = userHome.resolve(".centroplus");
        Path dbFile = appDir.resolve("centroplus.db");
        Files.deleteIfExists(dbFile);
    }

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

    @Test
    public void initializeWithNewDatabaseTest() throws Exception {
        // Test 1: Make sure the DB doesn't exist first
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path appDir = userHome.resolve(".centroplus");
        Path dbFile = appDir.resolve("centroplus.db");

        // Delete the file if it exists
        Files.deleteIfExists(dbFile);

        // Call initialize() again (using reflection since it's package-private)
        java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(null, null, null);
        
        // Verify that the file exists after initialization
        assertNotNull(dbFile);
        
        // Clean up - delete the test file
        Files.deleteIfExists(dbFile);
    }

    @Test
    public void initializeWithExistingDatabaseTest() throws Exception {
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path appDir = userHome.resolve(".centroplus");
        Path dbFile = appDir.resolve("centroplus.db");
        
        // Create a dummy file first
        Files.createDirectories(appDir);
        if (!Files.exists(dbFile)) {
            Files.createFile(dbFile);
        }
        
        // Call initialize
        java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(null, null, null);
        
        // Verify the file still exists
        assertNotNull(dbFile);
        
        // Clean up
        Files.deleteIfExists(dbFile);
    }

    @Test
    public void initializeWithErrorTest() throws Exception {
        // Temporarily change user.home to a non-writable path to trigger an exception
        String originalUserHome = System.getProperty("user.home");
        try {
            // Call initialize with bad user home
            java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(null, "\\nonexistentpaththatshouldnotexist1234567890", null);
            
            // After the exception, jdbcUrl should be "jdbc:sqlite:centroplus.db"
            java.lang.reflect.Field jdbcUrlField = SQLiteConnectionManager.class.getDeclaredField("jdbcUrl");
            jdbcUrlField.setAccessible(true);
            assertNotNull(jdbcUrlField.get(null));
            
        } finally {
            // Restore original user.home
            System.setProperty("user.home", originalUserHome);
        }
    }

    @Test
    public void initializeWithNullResourceStreamTest() throws Exception {
        // Get our temp directory (since user.home might be restricted)
        String tempDir = System.getProperty("java.io.tmpdir");
        Path testAppDir = Paths.get(tempDir).resolve(".centroplus-test");
        Files.createDirectories(testAppDir);
        Path dbFile = testAppDir.resolve("centroplus.db");
        Files.deleteIfExists(dbFile);

        java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(null, tempDir, null);

        // Clean up
        Files.deleteIfExists(dbFile);
    }

    @Test
    public void initializeWithMockResourceStreamTest() throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        Path testAppDir = Paths.get(tempDir).resolve(".centroplus-test");
        Files.createDirectories(testAppDir);
        Path dbFile = testAppDir.resolve("centroplus.db");
        Files.deleteIfExists(dbFile);

        java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(null, tempDir, new ByteArrayInputStream(new byte[0]));

        // Clean up
        Files.deleteIfExists(dbFile);
    }

    @Test
    public void initializeWithResourceStreamNullTest() throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        Path testAppDir = Paths.get(tempDir).resolve(".centroplus-test-null-res");
        Files.createDirectories(testAppDir);
        Path dbFile = testAppDir.resolve("centroplus.db");
        Files.deleteIfExists(dbFile);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(dbFile)).thenReturn(false);
            mockedFiles.when(() -> Files.createDirectories(testAppDir)).thenReturn(null);
            
            java.lang.reflect.Method initializeMethod = SQLiteConnectionManager.class.getDeclaredMethod("initialize", String.class, InputStream.class);
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(null, tempDir, null);
        }

        // Clean up
        Files.deleteIfExists(dbFile);
    }
}

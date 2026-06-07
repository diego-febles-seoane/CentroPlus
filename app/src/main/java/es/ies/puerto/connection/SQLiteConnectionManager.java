package es.ies.puerto.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class SQLiteConnectionManager {
    private static String jdbcUrl;

    static {
        try {
            Path userHome = Paths.get(System.getProperty("user.home"));
            Path appDir = userHome.resolve(".centroplus");
            Files.createDirectories(appDir);
            Path dbFile = appDir.resolve("centroplus.db");
            
            if (!Files.exists(dbFile)) {
                InputStream resourceStream = SQLiteConnectionManager.class.getResourceAsStream("/es/ies/puerto/database/centroplus.db");
                if (resourceStream != null) {
                    Files.copy(resourceStream, dbFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            
            jdbcUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();
        } catch (Exception e) {
            System.err.println("Error inicializando la base de datos: " + e.getMessage());
            e.printStackTrace();
            jdbcUrl = "jdbc:sqlite:centroplus.db";
        }
    }

    public static Connection getConnection() throws SQLException { 
        return DriverManager.getConnection(jdbcUrl); 
    }

    public static void setJdbcUrl(String url) { 
        jdbcUrl = url; 
    }

    public static void setDatabasePath(String testDb) {
        jdbcUrl = "jdbc:sqlite:" + testDb;
    }
}

package es.ies.puerto.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.ies.puerto.connection.SQLiteConnectionManager;
import es.ies.puerto.modelo.Actividades;
import es.ies.puerto.service.sqlite.ActividadesService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActividadesServiceTest {

    private static ActividadesService actividadesService;

    @BeforeAll
    public static void setUp() {
        SQLiteConnectionManager.setDatabasePath("src/main/resources/es/ies/puerto/database/centroplus-test.db");
        actividadesService = new ActividadesService();
    }

    @Test
    @Order(1)
    @DisplayName("findAll devuelve una lista no nula")
    public void testFindAll() {
        List<Actividades> actividades = actividadesService.findAll();
        assertNotNull(actividades, "La lista de actividades no debe ser nula");
        System.out.println("Número de actividades encontradas: " + actividades.size());
    }

    @Test
    @Order(2)
    @DisplayName("save guarda una actividad y findById la recupera correctamente")
    public void testSaveAndFindById() {
        Actividades actividad = new Actividades(999, "Prueba Actividad", "Test", 60, 25.0, 20, 5);
        boolean guardado = actividadesService.save(actividad);
        assertTrue(guardado, "La actividad debe guardarse correctamente");

        Actividades encontrada = actividadesService.findById(999);
        assertNotNull(encontrada, "La actividad guardada debe poder recuperarse");
        assertEquals("Prueba Actividad", encontrada.getNombre(), "El nombre debe coincidir");
        assertEquals("Test", encontrada.getTipoActividad(), "El tipo debe coincidir");
        assertEquals(60, encontrada.getDuracion(), "La duración debe coincidir");
        assertEquals(20, encontrada.getPlazasMaximas(), "Las plazas máximas deben coincidir");
        assertEquals(5, encontrada.getPlazasOcupadas(), "Las plazas ocupadas deben coincidir");
    }

    @Test
    @Order(3)
    @DisplayName("update modifica los datos de una actividad existente")
    public void testUpdate() {
        Actividades actividad = actividadesService.findById(999);
        assertNotNull(actividad, "La actividad debe existir para poder actualizarla");

        actividad.setNombre("Actividad Actualizada");
        actividad.setTipoActividad("Actualizado");
        actividad.setDuracion(90);
        actividad.setPlazasMaximas(30);
        actividad.setPlazasOcupadas(10);
        boolean actualizado = actividadesService.update(actividad);
        assertTrue(actualizado, "La actualización debe realizarse correctamente");

        Actividades actualizada = actividadesService.findById(999);
        assertEquals("Actividad Actualizada", actualizada.getNombre(), "El nombre actualizado debe coincidir");
        assertEquals("Actualizado", actualizada.getTipoActividad(), "El tipo actualizado debe coincidir");
        assertEquals(90, actualizada.getDuracion(), "La duración actualizada debe coincidir");
        assertEquals(30, actualizada.getPlazasMaximas(), "Las plazas máximas actualizadas deben coincidir");
        assertEquals(10, actualizada.getPlazasOcupadas(), "Las plazas ocupadas actualizadas deben coincidir");
    }

    @Test
    @Order(4)
    @DisplayName("delete elimina una actividad y findById devuelve null")
    public void testDelete() {
        boolean eliminado = actividadesService.delete(999);
        assertTrue(eliminado, "La eliminación debe realizarse correctamente");

        Actividades eliminada = actividadesService.findById(999);
        assertNull(eliminada, "Tras eliminar, findById debe devolver null");
    }

    @Test
    @Order(5)
    @DisplayName("findById devuelve null para un id inexistente")
    public void testFindByIdNoExistente() {
        Actividades resultado = actividadesService.findById(-1);
        assertNull(resultado, "findById con id inexistente debe devolver null");
    }

    @Test
    @Order(6)
    @DisplayName("delete devuelve false para un id inexistente")
    public void testDeleteNoExistente() {
        boolean eliminado = actividadesService.delete(-1);
        assertFalse(eliminado, "delete con id inexistente debe devolver false");
    }
}

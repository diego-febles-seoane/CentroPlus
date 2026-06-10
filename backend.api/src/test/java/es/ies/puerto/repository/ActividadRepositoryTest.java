package es.ies.puerto.repository;

import es.ies.puerto.entity.Actividad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActividadRepositoryTest {

    @Autowired
    private ActividadRepository actividadRepository;

    @Test
    void testSaveActividad() {
        Actividad actividad = new Actividad(null, "Yoga", "DEPORTIVA", 60, 25.50, 15, 8);

        Actividad actividadGuardada = actividadRepository.save(actividad);

        assertNotNull(actividadGuardada);
        assertNotNull(actividadGuardada.getId());
        assertEquals("Yoga", actividadGuardada.getNombre());
    }

    @Test
    void testFindById() {
        Actividad actividad = new Actividad(null, "Programación Java", "ACADEMICA", 90, 40.00, 20, 12);
        actividadRepository.save(actividad);

        Optional<Actividad> actividadEncontrada = actividadRepository.findById(actividad.getId());

        assertTrue(actividadEncontrada.isPresent());
        assertEquals("Programación Java", actividadEncontrada.get().getNombre());
    }

    @Test
    void testFindAll() {
        actividadRepository.save(new Actividad(null, "Yoga", "DEPORTIVA", 60, 25.50, 15, 8));
        actividadRepository.save(new Actividad(null, "Programación Java", "ACADEMICA", 90, 40.00, 20, 12));

        List<Actividad> actividades = actividadRepository.findAll();

        assertNotNull(actividades);
        assertEquals(2, actividades.size());
    }

    @Test
    void testDeleteById() {
        Actividad actividad = new Actividad(null, "Spinning", "DEPORTIVA", 45, 18.00, 12, 12);
        Actividad actividadGuardada = actividadRepository.save(actividad);

        actividadRepository.deleteById(actividadGuardada.getId());

        Optional<Actividad> actividadEliminada = actividadRepository.findById(actividadGuardada.getId());
        assertFalse(actividadEliminada.isPresent());
    }

    @Test
    void testUpdateActividad() {
        Actividad actividad = new Actividad(null, "Yoga", "DEPORTIVA", 60, 25.50, 15, 8);
        Actividad actividadGuardada = actividadRepository.save(actividad);

        actividadGuardada.setNombre("Yoga Avanzado");
        actividadGuardada.setPrecio(30.00);
        Actividad actividadActualizada = actividadRepository.save(actividadGuardada);

        assertEquals("Yoga Avanzado", actividadActualizada.getNombre());
        assertEquals(30.00, actividadActualizada.getPrecio());
    }
}

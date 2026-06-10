package es.ies.puerto.repository;

import es.ies.puerto.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testSaveUsuario() {
        Usuario usuario = new Usuario(null, "Ana Pérez", "11111111A", "ana@email.com", "600111111", "ALUMNO");

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("Ana Pérez", usuarioGuardado.getNombre());
    }

    @Test
    void testFindById() {
        Usuario usuario = new Usuario(null, "Luis Ramos", "22222222B", "luis@email.com", "600222222", "SOCIO");
        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuario.getId());

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Luis Ramos", usuarioEncontrado.get().getNombre());
    }

    @Test
    void testFindAll() {
        usuarioRepository.save(new Usuario(null, "Ana Pérez", "11111111A", "ana@email.com", "600111111", "ALUMNO"));
        usuarioRepository.save(new Usuario(null, "Luis Ramos", "22222222B", "luis@email.com", "600222222", "SOCIO"));

        List<Usuario> usuarios = usuarioRepository.findAll();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test
    void testDeleteById() {
        Usuario usuario = new Usuario(null, "Marta Díaz", "33333333C", "marta@email.com", "600333333", "AMBOS");
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        usuarioRepository.deleteById(usuarioGuardado.getId());

        Optional<Usuario> usuarioEliminado = usuarioRepository.findById(usuarioGuardado.getId());
        assertFalse(usuarioEliminado.isPresent());
    }

    @Test
    void testUpdateUsuario() {
        Usuario usuario = new Usuario(null, "Ana Pérez", "11111111A", "ana@email.com", "600111111", "ALUMNO");
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        usuarioGuardado.setNombre("Ana Pérez García");
        Usuario usuarioActualizado = usuarioRepository.save(usuarioGuardado);

        assertEquals("Ana Pérez García", usuarioActualizado.getNombre());
    }
}

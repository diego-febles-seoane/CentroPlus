package es.ies.puerto.service;

import es.ies.puerto.entity.Usuario;
import es.ies.puerto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1, "Ana Pérez", "11111111A", "ana@email.com", "600111111", "ALUMNO");
    }

    @Test
    void testFindAll() {
        List<Usuario> usuarios = Arrays.asList(
                usuario,
                new Usuario(2, "Luis Ramos", "22222222B", "luis@email.com", "600222222", "SOCIO")
        );

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Ana Pérez", result.get().getNombre());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.save(usuario);

        assertNotNull(result);
        assertEquals("Ana Pérez", result.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioService.deleteById(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdate() {
        Usuario usuarioActualizado = new Usuario(1, "Ana Pérez García", "11111111A", "ana_actualizada@email.com", "600111111", "ALUMNO");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        Usuario result = usuarioService.update(1, usuarioActualizado);

        assertNotNull(result);
        assertEquals("Ana Pérez García", result.getNombre());
        assertEquals("ana_actualizada@email.com", result.getEmail());
    }
}

package es.ies.puerto.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import es.ies.puerto.modelo.Usuario;
import es.ies.puerto.repository.IUsuarioRepository;
import es.ies.puerto.service.sqlite.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario(999, "Test Usuario", "99999999T", "test@test.com", "600000001", "Socio");
    }

    @Test
    public void constructorDefaultTest() {
        UsuarioService service = new UsuarioService();
        assertNotNull(service);
    }

    @Test
    public void findAllTestOk() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void findByIdTestOk() {
        when(usuarioRepository.findById(999)).thenReturn(usuario);

        Usuario encontrado = usuarioService.findById(999);
        assertNotNull(encontrado);
        assertEquals("Test Usuario", encontrado.getNombre());
    }

    @Test
    public void findByIdTestNotFound() {
        when(usuarioRepository.findById(-1)).thenReturn(null);

        Usuario resultado = usuarioService.findById(-1);
        assertNull(resultado);
    }

    @Test
    public void saveTestOk() {
        when(usuarioRepository.save(usuario)).thenReturn(true);

        boolean guardado = usuarioService.save(usuario);
        assertTrue(guardado);
    }

    @Test
    public void saveTestNull() {
        boolean guardado = usuarioService.save(null);
        assertFalse(guardado);
    }

    @Test
    public void updateTestOk() {
        when(usuarioRepository.update(usuario)).thenReturn(true);

        boolean actualizado = usuarioService.update(usuario);
        assertTrue(actualizado);
    }

    @Test
    public void updateTestNull() {
        boolean actualizado = usuarioService.update(null);
        assertFalse(actualizado);
    }

    @Test
    public void deleteTestOk() {
        when(usuarioRepository.delete(999)).thenReturn(true);

        boolean eliminado = usuarioService.delete(999);
        assertTrue(eliminado);
    }

    @Test
    public void deleteTestNotFound() {
        when(usuarioRepository.delete(-1)).thenReturn(false);

        boolean eliminado = usuarioService.delete(-1);
        assertFalse(eliminado);
    }
}

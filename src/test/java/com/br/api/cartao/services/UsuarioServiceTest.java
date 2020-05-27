package com.br.api.cartao.services;

import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.repositories.UsuarioRepository;
import com.br.api.cartao.security.DetalhesUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class UsuarioServiceTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    public void InicializarTestes() {
        usuario = new Usuario(1, "Joao", "joao@joao", "123");
    }

    @Test
    public void deveSalvar() {
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioRetornado = usuarioService.salvarUsuario(usuario);

        Assertions.assertEquals(usuario.getId(), usuarioRetornado.getId());
        Assertions.assertEquals(usuario.getEmail(), usuarioRetornado.getEmail());
        Assertions.assertEquals(usuario.getNome(), usuarioRetornado.getNome());
        Assertions.assertEquals(usuario.getSenha(), usuarioRetornado.getSenha());

    }

    @Test
    public void deveSalvarDarErroPorEmailJaExistente() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario);

        Assertions.assertThrows(RuntimeException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

    }

    @Test
    public void deveDarErroPorUsuarioInexistente() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.loadUserByUsername("teste@teste");
        });

    }

    @Test
    public void deveConsultarUsuarioNormalmente() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario);

        UserDetails userDetails = usuarioService.loadUserByUsername("teste@teste");
        DetalhesUsuario detalhesUsuario = (DetalhesUsuario) userDetails;

        Assertions.assertEquals(1, detalhesUsuario.getId());
        Assertions.assertEquals("joao@joao", detalhesUsuario.getEmail());
        Assertions.assertEquals("123", detalhesUsuario.getSenha());


    }
}

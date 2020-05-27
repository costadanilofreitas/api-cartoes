package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.security.JWTUtil;
import com.br.api.cartao.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;

    ObjectMapper objectMapper = new ObjectMapper();
    Usuario usuario;

    @BeforeEach
    public void Inicialize() {
        usuario = new Usuario(1, "Joao", "joao@joao", "123");
    }

    @Test
    public void salvarUsuario() throws Exception {

        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("Joao")));
    }

    @Test
    public void salvarUsuarioDeDarErro() throws Exception {

        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(new RuntimeException());
        String json = objectMapper.writeValueAsString(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

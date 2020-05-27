package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.security.DetalhesUsuario;
import com.br.api.cartao.security.JWTUtil;
import com.br.api.cartao.services.ClienteService;
import com.br.api.cartao.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
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

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    ObjectMapper mapper = new ObjectMapper();

    Cliente cliente;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;


    @BeforeEach
    public void iniciar() throws ParseException {
        Usuario usuario = new Usuario(1, "Joao", "joao@joao", "123");
        DetalhesUsuario detalhesUsuario = new DetalhesUsuario(usuario.getId(), usuario.getEmail(), usuario.getSenha());
        Mockito.when(usuarioService.loadUserByUsername(Mockito.anyString())).thenReturn(detalhesUsuario);
        Mockito.when(jwtUtil.getUsername(Mockito.anyString())).thenReturn("joao@joao");
        Mockito.when(jwtUtil.tokenValido(Mockito.anyString())).thenReturn(true);


        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(1991, Calendar.OCTOBER + 1, 25);

        cliente = new Cliente(1, "João Aparecido", "470.129.120-06", calendar.getTime(), "joao@email.com.br");

    }

    @Test
    public void deveSalvarClienteComTodasAsInformacoes() throws Exception {
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("João Aparecido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.equalTo("470.129.120-06")));
    }

    @Test
    public void deveDarBadRequestParaCPFInvalido() throws Exception {
        cliente.setCpf("123.456.789-10");
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deveDarBadRequestParaEmailInvalido() throws Exception {
        cliente.setEmail("teste");
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deveDarBadRequestParaNomeInvalido() throws Exception {
        cliente.setNome("João");
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveDarBadRequestCasoOsCamposSejamNulos() throws Exception {
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenReturn(new Cliente());

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deveDarBadRequestCasoGereAlgumErroParaSalvar() throws Exception {
        Mockito.when(clienteService.salvarCliente(Mockito.any(Cliente.class))).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void deveDarBadRequestCasoPasseUmJsonVazio() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveBuscarPorId() throws Exception {
        Mockito.when(clienteService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.of(cliente));
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("João Aparecido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.equalTo("470.129.120-06")));
    }

    @Test
    public void deveDarBadQuestCasoTenhaExceptionAoBuscarId() throws Exception {
        Mockito.when(clienteService.buscarPorId(Mockito.anyInt())).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveBuscarTodosComUmIntegranteNaLista() throws Exception {
        Iterable<Cliente> lista = Arrays.asList(cliente);
        Mockito.when(clienteService.buscarTodosClientes()).thenReturn(lista);
        String json = mapper.writeValueAsString(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome", CoreMatchers.equalTo("João Aparecido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf", CoreMatchers.equalTo("470.129.120-06")));
    }

    @Test
    public void deveBuscarTodosComListaVazia() throws Exception {
        Iterable<Cliente> lista = Arrays.asList();
        Mockito.when(clienteService.buscarTodosClientes()).thenReturn(lista);
        String json = mapper.writeValueAsString(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void deveDarBadQuestCasoTenhaExceptionAoBuscarTodos() throws Exception {
        Iterable<Cliente> lista = Arrays.asList(cliente);
        Mockito.when(clienteService.buscarTodosClientes()).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveDeletarPorId() throws Exception {
        Mockito.when(clienteService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.of(cliente));
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("João Aparecido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.equalTo("470.129.120-06")));

        Mockito.verify(clienteService, Mockito.times(1)).deletarCliente(cliente);
    }

    @Test
    public void deveDarNoContentCasoNaoConsigaBuscarPorId() throws Exception {
        Mockito.when(clienteService.buscarPorId(Mockito.anyInt())).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void deveAtualizarClienteComTodasAsInformacoes() throws Exception {
        Mockito.when(clienteService.atualizarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("João Aparecido")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.equalTo("470.129.120-06")));
    }

    @Test
    public void deveDarBadRequestCasoTenhaErroAoAtualizar() throws Exception {
        Mockito.when(clienteService.atualizarCliente(Mockito.any(Cliente.class))).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(cliente);

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}

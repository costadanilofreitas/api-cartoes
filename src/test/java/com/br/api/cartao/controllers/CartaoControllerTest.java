package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.repositories.CartaoRepository;
import com.br.api.cartao.security.DetalhesUsuario;
import com.br.api.cartao.security.JWTUtil;
import com.br.api.cartao.services.CartaoService;
import com.br.api.cartao.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hamcrest.CoreMatchers;
import org.hibernate.ObjectNotFoundException;
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

import java.util.*;

@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CartaoRepository cartaoRepository;

    @MockBean
    CartaoService cartaoService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;

    Cartao cartao;
    Cliente cliente;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void Inicialize() {
        Usuario usuario = new Usuario(1, "Joao", "joao@joao", "123");
        DetalhesUsuario detalhesUsuario = new DetalhesUsuario(usuario.getId(), usuario.getEmail(), usuario.getSenha());
        Mockito.when(usuarioService.loadUserByUsername(Mockito.anyString())).thenReturn(detalhesUsuario);
        Mockito.when(jwtUtil.getUsername(Mockito.anyString())).thenReturn("joao@joao");
        Mockito.when(jwtUtil.tokenValido(Mockito.anyString())).thenReturn(true);

        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(1971, Calendar.MAY, 25);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setCpf("470.129.120-06");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("teste@teste.com");
        cliente.setNome("SALVADOR DALI");

        cartao = new Cartao();
        cartao.setCliente(cliente);
        cartao.setCvv(111);
        cartao.setLimiteAtual(1000);
        cartao.setLimiteTotal(1000);

        Calendar validade = new GregorianCalendar();
        validade = new GregorianCalendar(2030, Calendar.JANUARY, 2);

        cartao.setValidade(validade.getTime());

    }

    @Test
    public void salvarCartaoTest() throws Exception {

        cartao.setNumeroCartao(00000001);
        Iterable<Cliente> clienteIterable = Arrays.asList(cliente);

        Mockito.when(cartaoService.buscarTodosClientes(Mockito.anyList())).thenReturn(clienteIterable);
        Mockito.when(cartaoService.salvarCartao(Mockito.any(Cartao.class))).thenReturn(cartao);

        String json = objectMapper.writeValueAsString(cartao);

        mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao", CoreMatchers.equalTo(1)));
    }

    @Test
    public void salvarCartaoInvalidoTest() throws Exception {

        cartao.setNumeroCartao(1);
        Iterable<Cliente> clienteIterable = Arrays.asList(cliente);

        Mockito.when(cartaoService.buscarTodosClientes(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(cartaoService.salvarCartao(Mockito.any(Cartao.class))).thenReturn(cartao);

        String json = objectMapper.writeValueAsString(cartao);

        mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void buscarCartaoTest() throws Exception {
        Iterable<Cartao> cartaoIterable = Arrays.asList(cartao, cartao);
        Mockito.when(cartaoService.buscarTodosCartoes()).thenReturn(cartaoIterable);

        String json = objectMapper.writeValueAsString(cartaoIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void buscarCartaoIDTest() throws Exception {
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(cartaoOptional);

        String json = objectMapper.writeValueAsString(cartaoOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void buscarCartaoIDInexistenteTest() throws Exception {
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(cartaoOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void atualizarCartaoTest() throws Exception {

        cartao.setNumeroCartao(00000001);
        Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class))).thenReturn(cartao);

        String json = objectMapper.writeValueAsString(cartao);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao", CoreMatchers.equalTo(1)));

    }

    @Test
    public void atualizarCartaoTestErro() throws Exception {

        Cartao cartao2 = new Cartao();
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class)))
                .thenThrow(new ObjectNotFoundException(Cartao.class,
                        "O Cartão não foi encontrado"));

        String json = objectMapper.writeValueAsString(cartao2);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void atualizarCartaoTestUrlErro() throws Exception {

        Cartao cartao2 = new Cartao();
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class)))
                .thenThrow(new ObjectNotFoundException(Cartao.class,
                        "O Cartão não foi encontrado"));

        String json = objectMapper.writeValueAsString(cartao2);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartoes/a")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void atualizarCartaoTestVazioErro() throws Exception {

        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class)))
                .thenThrow(new ObjectNotFoundException(Cartao.class,
                        "O Cartão não foi encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/cartoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void excluirCartaoTest() throws Exception {
        cartao.setNumeroCartao(3);
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(cartaoOptional);

        String json = objectMapper.writeValueAsString(cartaoOptional.get());

        mockMvc.perform(MockMvcRequestBuilders.delete("/cartoes/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(cartaoService, Mockito.times(1))
                .deletarCartao(cartaoOptional.get());
    }

    @Test
    public void excluirCartaoInvalidoTest() throws Exception {
        cartao.setNumeroCartao(3);
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(cartaoOptional.get());

        mockMvc.perform(MockMvcRequestBuilders.delete("/cartoes/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

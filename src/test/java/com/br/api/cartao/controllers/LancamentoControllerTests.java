package com.br.api.cartao.controllers;

import com.br.api.cartao.enums.Categoria;
import com.br.api.cartao.enums.TipoDeLancamento;
import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Lancamento;
import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.security.DetalhesUsuario;
import com.br.api.cartao.security.JWTUtil;
import com.br.api.cartao.services.CartaoService;
import com.br.api.cartao.services.LancamentoService;
import com.br.api.cartao.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.regexp.joni.Option;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@WebMvcTest(LancamentoController.class)
public class LancamentoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LancamentoService lancamentoService;

    @MockBean
    CartaoService cartaoService;

    ObjectMapper mapper = new ObjectMapper();

    Lancamento lancamento;
    Cartao cartao;
    Cliente cliente;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JWTUtil jwtUtil;

    @BeforeEach
    public void iniciar(){
        Usuario usuario = new Usuario(1, "Joao", "joao@joao", "123");
        DetalhesUsuario detalhesUsuario = new DetalhesUsuario(usuario.getId(), usuario.getEmail(), usuario.getSenha());
        Mockito.when(usuarioService.loadUserByUsername(Mockito.anyString())).thenReturn(detalhesUsuario);
        Mockito.when(jwtUtil.getUsername(Mockito.anyString())).thenReturn("joao@joao");
        Mockito.when(jwtUtil.tokenValido(Mockito.anyString())).thenReturn(true);

        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(2020, Calendar.APRIL, 12);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setCpf("13048729801");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("email@email.com");
        cliente.setNome("Nome Cliente");


        cartao = new Cartao();
        cartao.setNumeroCartao(1l);
        cartao.setCliente(cliente);
        cartao.setCvv(673);
        cartao.setLimiteAtual(0);
        cartao.setLimiteTotal(1000);
        cartao.setValidade(calendar.getTime());

        lancamento = new Lancamento();
        lancamento.setId(1);
        lancamento.setData(calendar.getTime());
        lancamento.setTipoDeLancamento(TipoDeLancamento.DEBITO);
        lancamento.setValor(100);
        lancamento.setCategoria(Categoria.ALIMENTACAO);
        lancamento.setCartao(cartao);

    }

    @Test
    public void testarSalvarLancamento() throws Exception{

        lancamento.setId(1);
        Mockito.when(lancamentoService.criarLancamento(Mockito.any(Lancamento.class))).thenReturn(lancamento);

        String json = mapper.writeValueAsString(lancamento);

        mockMvc.perform(MockMvcRequestBuilders.post("/lancamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    public void testeSalvarLancamentoInvalido() throws Exception{
        lancamento.setId(1);
        lancamento.setValor(0);
        Mockito.when(lancamentoService.criarLancamento(Mockito.any(Lancamento.class)))
                .thenReturn(lancamento);

        String json = mapper.writeValueAsString(lancamento);

        mockMvc.perform(MockMvcRequestBuilders.post("/lancamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());  }

    @Test
    public void testarBuscarTodosLancamentos() throws Exception{
        Iterable<Lancamento> lancamentoIterable = Arrays.asList(lancamento, lancamento);
        Mockito.when(lancamentoService.buscarTodosLancamentos()).thenReturn(lancamentoIterable);

        String json = mapper.writeValueAsString(lancamentoIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/lancamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testarBuscarLancamentoPorID() throws Exception{
        Optional<Lancamento> lancamentoOptional = Optional.of(lancamento);
        Mockito.when(lancamentoService.buscarPorId(Mockito.anyInt())).thenReturn(lancamentoOptional);

        String json = mapper.writeValueAsString(lancamentoOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/lancamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testarBuscarLancamentoInexistentePorID() throws Exception{
        Optional<Lancamento> lancamentoOptional = Optional.of(lancamento);
        Mockito.when(lancamentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.empty());

        String json = mapper.writeValueAsString(lancamentoOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/lancamentos/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testarAtualizarLancamento() throws Exception{

        lancamento.setId(1);
        Mockito.when(lancamentoService.atualizarLancamento(Mockito.any(Lancamento.class))).thenReturn(lancamento);

        String json = mapper.writeValueAsString(lancamento);

        mockMvc.perform(MockMvcRequestBuilders.put("/lancamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    public void testarAtualizarLancamentoInexistente() throws Exception{

        //Mockito.when(lancamentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(lancamentoService.atualizarLancamento(Mockito.any(Lancamento.class)))
                .thenThrow(new ObjectNotFoundException(Lancamento.class
                                ,"Não foi encontrado o lançamento selecionado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/lancamentos/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testarExcluirLancamento() throws Exception{
        lancamento.setId(1);
        Optional<Lancamento> lancamentoOptional = Optional.of(lancamento);
        Mockito.when(lancamentoService.buscarPorId(Mockito.anyInt())).thenReturn(lancamentoOptional);

        String json = mapper.writeValueAsString(lancamentoOptional.get());

        mockMvc.perform(MockMvcRequestBuilders.delete("/lancamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(lancamentoService, Mockito.times(1))
                .apagarLancamento(lancamentoOptional.get());

    }

    @Test
    public void testarExcluirLancamentoInexistente() throws Exception{
        lancamento.setId(1);
        Optional<Lancamento> lancamentoOptional = Optional.of(lancamento);
        Mockito.when(lancamentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.empty());

        String json = mapper.writeValueAsString(lancamentoOptional.get());

        mockMvc.perform(MockMvcRequestBuilders.delete("/lancamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + "DevePassar")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.repositories.CartaoRepository;
import com.br.api.cartao.services.CartaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CartaoRepository cartaoRepository;

    @MockBean
    CartaoService cartaoService;

    Cartao cartao;
    Cliente cliente;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void Inicialize() {

        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(1971, Calendar.MAY, 25);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setCpf("12345678909");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("teste@teste.com");
        cliente.setNome("SALVADOR DALI");

        cartao = new Cartao();
        cartao.setCliente(cliente);
        cartao.setCvv(111);
        cartao.setLimiteAtual(1000);
        cartao.setLimiteTotal(1000);

    }

    @Test
    public void salvarCartaoTest() throws Exception {

        cartao.setNumeroCartao(00000001);
        Mockito.when(cartaoService.salvarCartao(Mockito.any(Cartao.class))).thenReturn(cartao);

        String json = objectMapper.writeValueAsString(cartao);

        mockMvc.perform(MockMvcRequestBuilders.post("/cartao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao", CoreMatchers.equalTo(1)));
    }

    @Test
    public void buscarCartaoTest() throws Exception {
        Iterable<Cartao> cartaoIterable = Arrays.asList(cartao, cartao);
        Mockito.when(cartaoService.buscarTodosCartoes()).thenReturn(cartaoIterable);

        String json = objectMapper.writeValueAsString(cartaoIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void buscarCartaoIDTest() throws Exception {
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(cartaoOptional);

        String json = objectMapper.writeValueAsString(cartaoOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartao/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void atualizarCartaoTest() throws Exception {

        cartao.setNumeroCartao(00000001);
        Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class))).thenReturn(cartao);

        String json = objectMapper.writeValueAsString(cartao);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartao/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao", CoreMatchers.equalTo(1)));

    }

    @Test
    public void atualizarCartaoTestErro() throws Exception {

        Cartao cartao2 = new Cartao();
        //   Mockito.when(cartaoService.atualizarCartao(Mockito.any(Cartao.class))).thenReturn(cartao2);

        String json = objectMapper.writeValueAsString(cartao2);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartao/a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //     .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao", CoreMatchers.equalTo(4)));

    }


    /*@Test
    public void excluirCartaoTest() throws Exception {
        cartao.setNumeroCartao(3);
        Optional<Cartao> cartaoOptional = Optional.of(cartao);
        Mockito.when(cartaoService.buscarPorId(Mockito.anyLong())).thenReturn(cartaoOptional);
        Mockito.verify(cartaoService, Mockito.times(1))
                .deletarCartao(cartaoOptional.get());
        String json = objectMapper.writeValueAsString(cartaoOptional.get());

        mockMvc.perform(MockMvcRequestBuilders.delete("/cartao/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }*/

}

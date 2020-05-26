package com.br.api.cartao.services;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Cartao;

import com.br.api.cartao.repositories.CartaoRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@SpringBootTest
public class CartaoServiceTests {
    @MockBean
    CartaoRepository cartaoRepository;

    @Autowired
    CartaoService cartaoService;

    Cartao cartao;
    Cliente cliente;

    @BeforeEach
    public void InicializarTestes () {
        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(2019, Calendar.MARCH, 15);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setCpf("13048729801");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("email@email.com");
        cliente.setNome("Validacoes Iniciais");

        cartao = new Cartao();
        cartao.setCliente(cliente);
        cartao.setCvv(567);
        cartao.setLimiteAtual(0);
        cartao.setLimiteTotal(1000);

    }

    @Test
    public void testarBuscarPorIdSucesso(){
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));
        int id = 1;
        Optional lancamentoOptional = cartaoService.buscarPorId(id);
        Assertions.assertEquals(cartao, lancamentoOptional.get());
    }

    @Test
    public void testarBuscarPorIdInexistente(){
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        int id = 1;
        Optional lancamentoOptional = cartaoService.buscarPorId(id);
        Assertions.assertFalse(lancamentoOptional.isPresent());
    }

    @Test
    public void testarSalvarCartaoSucesso(){
        Mockito.when(cartaoRepository.save(Mockito.any(Cartao.class))).thenReturn(cartao);
        Cartao cartaoObjeto = cartaoService.salvarCartao(cartao);
        Assertions.assertEquals(cartao, cartaoObjeto);
    }

    @Test
    public void testarBuscarTodosCartoesSucesso(){
        Iterable<Cartao> lancamentosIterable = Arrays.asList(cartao);
        Mockito.when(cartaoRepository.findAll()).thenReturn(lancamentosIterable);
        Iterable<Cartao> iterableResultado = cartaoService.buscarTodosCartoes();
        Assertions.assertEquals(lancamentosIterable, iterableResultado);
    }

    @Test
    public void testarBuscarTodosLancamentosVazio(){
        Iterable<Cartao> cartaoIterable = Arrays.asList(new Cartao());
        Mockito.when(cartaoRepository.findAll()).thenReturn(cartaoIterable);
        Iterable<Cartao> iterableResultado = cartaoService.buscarTodosCartoes();
        Assertions.assertEquals(cartaoIterable, iterableResultado);
    }

    @Test
    public void testarAtualizarCartaoSucesso() throws javassist.tools.rmi.ObjectNotFoundException {
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));
        Mockito.when(cartaoRepository.save(Mockito.any(Cartao.class))).thenReturn(cartao);
        Cartao cartaoObjeto = cartaoService.atualizarCartao(cartao);
        Assertions.assertEquals(cartao, cartaoObjeto);
    }

    @Test
    public void testarAtualizarLancamentoInexistente(){
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(cartaoRepository.save(Mockito.any(Cartao.class))).thenReturn(null);
        Assertions.assertThrows(ObjectNotFoundException.class, ()->{cartaoService.atualizarCartao(cartao);});
    }


    @Test
    public void testarDeletarCartaoSucesso(){
        cartaoService.deletarCartao(cartao);
        Mockito.verify(cartaoRepository, Mockito.times(1))
                .delete(Mockito.any(Cartao.class));
    }

}

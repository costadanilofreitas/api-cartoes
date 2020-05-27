package com.br.api.cartao.services;

import com.br.api.cartao.enums.Categoria;
import com.br.api.cartao.enums.TipoDeLancamento;
import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.models.Lancamento;
import com.br.api.cartao.repositories.LancamentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
public class LancamentoServiceTests {
    @MockBean
    LancamentoRepository lancamentoRepository;

    @Autowired
    LancamentoService lancamentoService;

    Lancamento lancamento;
    Cartao cartao;
    Cliente cliente;

    @BeforeEach
    public void inicializarTestes() {
        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(2024, Calendar.JANUARY, 1);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setCpf("25606206854");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("angela@gmail.com");
        cliente.setNome("Teste");

        cartao = new Cartao();
        cartao.setCliente(cliente);
        cartao.setCvv(111);
        cartao.setLimiteAtual(0);
        cartao.setLimiteTotal(10);

        cartao.setValidade(calendar.getTime());
        lancamento = new Lancamento();
        lancamento.setId(1);
        lancamento.setData(calendar.getTime());
        lancamento.setTipoDeLancamento(TipoDeLancamento.CREDITO);
        lancamento.setValor(100);
        lancamento.setCategoria(Categoria.BONUS);
        lancamento.setCartao(cartao);
    }

    @Test
    public void testarBuscarPorIdSucesso(){
        Mockito.when(lancamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(lancamento));
        int id = 1;
        Optional lancamentoOptional = lancamentoService.buscarPorId(id);
        Assertions.assertEquals(lancamento, lancamentoOptional.get());
        Assertions.assertEquals(lancamento.getTipoDeLancamento(),
                ((Lancamento) lancamentoOptional.get()).getTipoDeLancamento());
        Assertions.assertEquals(lancamento.getData(),
                ((Lancamento) lancamentoOptional.get()).getData());
        Assertions.assertEquals(lancamento.getValor(),
                ((Lancamento) lancamentoOptional.get()).getValor());
        Assertions.assertEquals(lancamento.getCategoria(),
                ((Lancamento) lancamentoOptional.get()).getCategoria());
        Assertions.assertEquals(lancamento.getCartao(),
                ((Lancamento) lancamentoOptional.get()).getCartao());
    }

    @Test
    public void testarBuscarPorIdInexistente(){
        Mockito.when(lancamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        int id = 1;
        Assertions.assertThrows(ObjectNotFoundException.class,() ->{lancamentoService.buscarPorId(id);});
    }

    @Test
    public void testarCriarLancamentoSucesso(){
        Mockito.when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(lancamento);
        Lancamento lancamentoObjeto = lancamentoService.criarLancamento(lancamento);
        Assertions.assertEquals(lancamento, lancamentoObjeto);
    }

    @Test
    public void testarCriarLancamentoZero(){
        Lancamento lancamentoSave = new Lancamento(1, TipoDeLancamento.CREDITO, 0.01
                , Calendar.getInstance().getTime(), Categoria.LAZER, cartao);
        Mockito.when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(lancamentoSave);
        Lancamento lancamentoObjeto = lancamentoService.criarLancamento(lancamentoSave);
        Assertions.assertEquals(lancamentoSave, lancamentoObjeto);
    }

    @Test
    public void testarBuscarTodosLancamentosSucesso(){
        Iterable<Lancamento> lancamentosIterable = Arrays.asList(lancamento);
        Mockito.when(lancamentoRepository.findAll()).thenReturn(lancamentosIterable);
        Iterable<Lancamento> iterableResultado = lancamentoService.buscarTodosLancamentos();
        Assertions.assertEquals(lancamentosIterable, iterableResultado);
    }

    @Test
    public void testarBuscarTodosLancamentosVazio(){
        Iterable<Lancamento> lancamentosIterable = Arrays.asList(new Lancamento());
        Mockito.when(lancamentoRepository.findAll()).thenReturn(lancamentosIterable);
        Iterable<Lancamento> iterableResultado = lancamentoService.buscarTodosLancamentos();
        Assertions.assertEquals(lancamentosIterable, iterableResultado);
    }

    @Test
    public void testarAtualizarLancamentoSucesso(){
        Mockito.when(lancamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(lancamento));
        Mockito.when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(lancamento);
        Lancamento lancamentoObjeto = lancamentoService.atualizarLancamento(lancamento);
        Assertions.assertEquals(lancamento, lancamentoObjeto);
    }

    @Test
    public void testarAtualizarLancamentoInexistente(){
        Mockito.when(lancamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(null);
        Assertions.assertThrows(ObjectNotFoundException.class, ()->{lancamentoService.atualizarLancamento(lancamento);});
    }

    @Test
    public void testarAtualizarLancamentoValorInvalido(){
        Lancamento lancamentoAtualizado = lancamento;
        lancamentoAtualizado.setValor(new Double(0));
        Mockito.when(lancamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(lancamento));
        Mockito.when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(lancamentoAtualizado);
        Lancamento lancamentoObjeto = lancamentoService.atualizarLancamento(lancamentoAtualizado);
        Assertions.assertEquals(lancamentoObjeto, lancamentoAtualizado);
    }

    @Test
    public void testarApagarLancamentoSucesso(){
        lancamentoService.apagarLancamento(lancamento);
        Mockito.verify(lancamentoRepository, Mockito.times(1))
                .delete(Mockito.any(Lancamento.class));
    }
}

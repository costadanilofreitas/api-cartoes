package com.br.api.cartao.services;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.repositories.ClienteRepository;
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
public class ClienteServiceTests {

    @MockBean
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    Cliente cliente;

    @BeforeEach
    public void inicializar(){
        Calendar calendar = new GregorianCalendar();
        calendar = new GregorianCalendar(1988, Calendar.DECEMBER, 7);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Carolina Silva Maia");
        cliente.setCpf("381.428.528-02");
        cliente.setDataNascimento(calendar.getTime());
        cliente.setEmail("carolinamaia187@gmail.com");
    }

    @Test
    public void testarSalvarCliente(){
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        Cliente clienteObjeto = clienteService.salvarCliente(cliente);

        Assertions.assertEquals(cliente, clienteObjeto);
        Assertions.assertEquals(cliente.getEmail(), clienteObjeto.getEmail());
    }

    @Test
    public void testarBuscarPorIdSucesso(){
        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));
        int id = 1;
        Optional clienteOptional = clienteService.buscarPorId(id);
        Assertions.assertEquals(cliente, clienteOptional.get());
    }

    @Test
    public void testarBuscarPorIdInexistente(){
        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        int id = 1;
        Optional clienteOptional = clienteService.buscarPorId(id);
        Assertions.assertFalse(clienteOptional.isPresent());
    }

    @Test
    public void testarBuscarTodosClientesSucesso(){
        Iterable<Cliente> clienteIterable = Arrays.asList(cliente);

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteIterable);
        Iterable<Cliente> iterableResultado = clienteService.buscarTodosClientes();
        Assertions.assertEquals(clienteIterable, iterableResultado);
    }

    @Test
    public void testarBuscarTodosClientesVazio(){
        Iterable<Cliente> clienteIterable = Arrays.asList(new Cliente());

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteIterable);
        Iterable<Cliente> iterableResultado = clienteService.buscarTodosClientes();
        Assertions.assertEquals(clienteIterable, iterableResultado);
    }

    @Test
    public void testarAtualizarClienteSucesso(){
        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        Cliente clienteObjeto = clienteService.atualizarCliente(cliente);
        Assertions.assertEquals(cliente, clienteObjeto);
    }

    @Test
    public void testarAtualizarClienteInexistente(){
        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(null);

        Assertions.assertThrows(ObjectNotFoundException.class,() ->{clienteService.atualizarCliente(cliente);});
    }

    @Test
    public void testarDeletarCliente(){
        clienteService.deletarCliente(cliente);
        Mockito.verify(clienteRepository, Mockito.times(1)).delete(Mockito.any(Cliente.class));
    }


}

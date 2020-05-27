package com.br.api.cartao.services;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.repositories.ClienteRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId (int id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return clienteOptional;
        }
        throw new ObjectNotFoundException(Cliente.class, "O cliente não foi encontrado");
    }

    public Iterable<Cliente> buscarTodosClientes() {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

    public Cliente atualizarCliente(Cliente cliente) throws ObjectNotFoundException {
        Optional<Cliente> clienteOptional = buscarPorId(cliente.getId());
        if (clienteOptional.isPresent()) {
            Cliente clienteObjeto = clienteRepository.save(cliente);
            return clienteObjeto;
        }
        throw new ObjectNotFoundException(Cliente.class, "O cliente não foi encontrado");
    }

    public void deletarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }


}

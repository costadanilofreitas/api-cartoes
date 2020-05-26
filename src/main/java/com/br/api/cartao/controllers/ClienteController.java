package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> buscarTodos() {
        try {
            return ResponseEntity.status(200).body(clienteService.buscarTodosClientes());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<Cliente> optional = clienteService.buscarPorId(id);
            return ResponseEntity.status(200).body(optional.get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid Cliente cliente) {
        try {
            Cliente retorno = clienteService.salvarCliente(cliente);
            return ResponseEntity.status(201).body(retorno);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deletar(@PathVariable Integer id) {
        try {
            Optional<Cliente> optional = clienteService.buscarPorId(id);
            clienteService.deletarCliente(optional.get());
            return ResponseEntity.status(200).body(optional.get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            return ResponseEntity.status(200).body(clienteService.atualizarCliente(cliente));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

}

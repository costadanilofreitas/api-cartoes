package com.br.api.cartao.controllers;


import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cartao")
public class CartaoController {
    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> criarCartao(@RequestBody @Valid Cartao cartao) {

        try {

            List<Integer> clienteID = new ArrayList<>();
            clienteID.add(cartao.getCliente().getId());
            Iterable<Cliente> clienteIterable = cartaoService.buscarTodosClientes(clienteID);
            cartao.setCliente(  clienteIterable.iterator().next());  // Somente um cliente

            Cartao resultado = cartaoService.salvarCartao(cartao);
            return ResponseEntity.status(201).body(resultado);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<Iterable> buscarTodosCartoes(){
        Iterable<Cartao>  cartaoIterable = cartaoService.buscarTodosCartoes();
        return ResponseEntity.status(200).body(cartaoIterable);

    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Cartao> buscarCartaoID(@PathVariable Long numeroCartao){
        Optional<Cartao> cartaoOptional = cartaoService.buscarPorId(numeroCartao);
        if (cartaoOptional.isPresent()){
            return ResponseEntity.status(200).body(cartaoOptional.get());
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado cartão com esse número "+numeroCartao);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizarCartao(@PathVariable Long id ,@RequestBody @Valid Cartao cartao) {
        cartao.setNumeroCartao(id);
        try {
            Cartao cartaoAtualizado = cartaoService.atualizarCartao(cartao);
            return ResponseEntity.status(200).body(cartaoAtualizado);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Cartao excluirCartao(@PathVariable Integer id){
        Optional<Cartao> cartaoOptional = cartaoService.buscarPorId(id);
        if(cartaoOptional.isPresent()){
            cartaoService.deletarCartao(cartaoOptional.get());
            return cartaoOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }


}

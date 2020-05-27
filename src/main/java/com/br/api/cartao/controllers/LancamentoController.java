package com.br.api.cartao.controllers;


import com.br.api.cartao.models.Lancamento;
import com.br.api.cartao.services.CartaoService;
import com.br.api.cartao.services.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    public Iterable<Lancamento> buscarTodosLancamentos(){
        return lancamentoService.buscarTodosLancamentos();
    }

    @GetMapping("/{id}")
    public Lancamento buscarPorId(@PathVariable Integer id){
        Optional<Lancamento> lancamentoOptional = lancamentoService.buscarPorId(id);
        if (lancamentoOptional.isPresent()){
            return lancamentoOptional.get();
        }else {
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST));
        }
    }

    @PostMapping
    public ResponseEntity<Lancamento> criarLancamento(@RequestBody @Valid Lancamento lancamento){
            Lancamento lancamentoObjeto = lancamentoService.criarLancamento(lancamento);
            return ResponseEntity.status(201).body(lancamentoObjeto);
    }

    @PutMapping("/{id}")
    public Lancamento atualizarLancamento(@PathVariable Integer id, @RequestBody Lancamento lancamento){
        lancamento.setId(id);
        try {
            Lancamento lancamentoObjeto = lancamentoService.atualizarLancamento(lancamento);
            return lancamentoObjeto;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Lancamento apagarLancamento(@PathVariable Integer id){
        Optional<Lancamento> lancamentoOptional = lancamentoService.buscarPorId(id);
        if (lancamentoOptional.isPresent()){
            lancamentoService.apagarLancamento(lancamentoOptional.get());
            return lancamentoOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}

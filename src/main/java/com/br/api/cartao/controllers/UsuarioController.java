package com.br.api.cartao.controllers;

import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registarUsuario(@RequestBody @Valid Usuario usuario){
        Usuario userObj;
        try{
            userObj = usuarioService.salvarUsuario(usuario);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(201).body(userObj);
    }


}

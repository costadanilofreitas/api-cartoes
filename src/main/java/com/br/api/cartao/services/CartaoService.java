package com.br.api.cartao.services;


import com.br.api.cartao.models.Cliente;
import com.br.api.cartao.repositories.ClienteRepository;

import com.br.api.cartao.models.Cartao;
import com.br.api.cartao.repositories.CartaoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Iterable<Cliente> buscarTodosClientes(List<Integer> clientesId){
        Iterable<Cliente> clienteIterable = clienteRepository.findAllById(clientesId);
        return clienteIterable;
    }


    public Cartao salvarCartao(Cartao cartao){
        //Optional<Cliente> clienteOptional = clienteRepository.findById();
        //if (clienteOptional.isPresent()){
            Cartao cartaoObjeto = cartaoRepository.save(cartao);
            return cartaoObjeto;
        //}
        //throw new org.hibernate.ObjectNotFoundException(Cartao.class, "Código de Cliente Inexistente!");
    }

    public Optional<Cartao> buscarPorId(long id){
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        return cartaoOptional;
    };

    public Iterable<Cartao> buscarTodosCartoes(){
        Iterable<Cartao> cartoes = cartaoRepository.findAll();
        return cartoes;
    }

    public Cartao atualizarCartao(Cartao cartao) throws ObjectNotFoundException{
        Optional<Cartao> cartaoOptional = buscarPorId(cartao.getNumeroCartao());
        if (cartaoOptional.isPresent()){
            Cartao cartaoData = cartaoOptional.get();
            if (cartao.getLimiteAtual() == 0.0){
                cartao.setLimiteAtual(cartaoData.getLimiteAtual());
            }
            if (cartao.getValidade() == null){
                cartao.setValidade(cartaoData.getValidade());
            }
            if (cartao.getLimiteTotal() == 0.0){
                cartao.setLimiteTotal(cartaoData.getLimiteTotal());
            }
            //if (cartao.getIdCliente() == 0){
            //    cartao.setIdCliente(cartaoData.getIdCliente());
            Cartao cartaoObjeto = cartaoRepository.save(cartao);
            return cartaoObjeto;
            //}else{
             //   Optional<Cliente> clienteOptional = clienteRepository.findById(cartao.getIdCliente());
             //   if (clienteOptional.isPresent()){
             //       Cartao cartaoObjeto = cartaoRepository.save(cartao);
             //       return cartaoObjeto;
             //}
             //   throw new org.hibernate.ObjectNotFoundException(Cartao.class, "Novo Código de Cliente Inexistente!");
            //}
        }
        throw new org.hibernate.ObjectNotFoundException(Cartao.class, "O Cartão não foi encontrado");
    }


    public void deletarCartao(Cartao cartao){
        cartaoRepository.delete(cartao);
    }
}

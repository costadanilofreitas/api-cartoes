package com.br.api.cartao.services;

import com.br.api.cartao.models.Lancamento;
import com.br.api.cartao.repositories.LancamentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {
    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Optional<Lancamento> buscarPorId(int id){
        Optional<Lancamento> lancamentoOptional = lancamentoRepository.findById(id);
        return lancamentoOptional;
    }

    public Lancamento criarLancamento(Lancamento lancamento){
        Lancamento lancamentoObjeto = lancamentoRepository.save(lancamento);
        return lancamentoObjeto;
    }

    public Iterable<Lancamento> buscarTodosLancamentos(){
        Iterable<Lancamento> lancamento = lancamentoRepository.findAll();
        return lancamento;
    }

    public Lancamento atualizarLancamento(Lancamento lancamento){
        Optional<Lancamento> lancamentoOptional = buscarPorId(lancamento.getId());
        if (lancamentoOptional.isPresent()){
            Lancamento lancamentoData = lancamentoOptional.get();
            Lancamento lancamentoObjeto = lancamentoRepository.save(lancamento);
            return lancamentoObjeto;
        }
        else {
            throw new ObjectNotFoundException(Lancamento.class,"Não foi encontrado o lançamento selecionado");
        }
    }

    public void apagarLancamento(Lancamento lancamento){
        lancamentoRepository.delete(lancamento);
    }

}

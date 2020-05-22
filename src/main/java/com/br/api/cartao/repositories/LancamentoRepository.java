package com.br.api.cartao.repositories;

import com.br.api.cartao.models.Lancamento;
import org.springframework.data.repository.CrudRepository;

public interface LancamentoRepository extends CrudRepository<Lancamento, Integer> {

}

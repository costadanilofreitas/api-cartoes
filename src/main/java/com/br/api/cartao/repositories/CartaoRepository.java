package com.br.api.cartao.repositories;

import com.br.api.cartao.models.Cartao;
import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Cartao, Long> {

}
package com.br.api.cartao.repositories;

import com.br.api.cartao.models.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
}

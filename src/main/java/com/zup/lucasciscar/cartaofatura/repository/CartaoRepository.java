package com.zup.lucasciscar.cartaofatura.repository;

import com.zup.lucasciscar.cartaofatura.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {

    Optional<Cartao> findByNumero(String numero);
}

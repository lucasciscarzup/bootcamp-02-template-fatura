package com.zup.lucasciscar.cartaofatura.repository;

import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, UUID> {

    Optional<Fatura> findTopByCartaoAndStatus(Cartao cartao, Fatura.Status status);

    Optional<Fatura> findTopByStatus(Fatura.Status status);
}

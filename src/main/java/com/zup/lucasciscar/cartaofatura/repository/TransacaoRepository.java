package com.zup.lucasciscar.cartaofatura.repository;

import com.zup.lucasciscar.cartaofatura.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}

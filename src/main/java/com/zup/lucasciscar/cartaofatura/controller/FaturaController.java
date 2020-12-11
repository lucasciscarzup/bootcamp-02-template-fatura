package com.zup.lucasciscar.cartaofatura.controller;

import com.zup.lucasciscar.cartaofatura.dto.FaturaResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Transacao;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class FaturaController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping("/cartoes/{idCartao}/fatura")
    public ResponseEntity<?> mostrarFatura(@PathVariable("idCartao")UUID idCartao) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findByCartaoOrderByEfetivadaEmDesc(cartao);
        FaturaResponse faturaResponse = new FaturaResponse(transacoes);

        return ResponseEntity.ok(faturaResponse);
    }
}

package com.zup.lucasciscar.cartaofatura.controller;

import com.zup.lucasciscar.cartaofatura.dto.response.FaturaResponse;
import com.zup.lucasciscar.cartaofatura.dto.response.SaldoResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Transacao;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.FaturaRepository;
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
    @Autowired
    private FaturaRepository faturaRepository;

    @GetMapping("/cartoes/{idCartao}/faturas")
    public ResponseEntity<?> mostrarFatura(@PathVariable("idCartao") UUID idCartao) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        Optional<Fatura> faturaOpt = faturaRepository.findTopByCartaoAndStatus(cartao, Fatura.Status.ABERTA);
        Fatura fatura = faturaOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma Fatura em aberto"));

        List<Transacao> transacoes = transacaoRepository.findByFaturaOrderByEfetivadaEmDesc(fatura);
        FaturaResponse faturaResponse = new FaturaResponse(transacoes);

        return ResponseEntity.ok(faturaResponse);
    }

    @GetMapping("/cartoes/{idCartao}/saldos")
    public ResponseEntity<?> mostrarSaldo(@PathVariable("idCartao") UUID idCartao) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        Optional<Fatura> faturaOpt = faturaRepository.findTopByCartaoAndStatus(cartao, Fatura.Status.ABERTA);
        Fatura fatura = faturaOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma Fatura em aberto"));

        List<Transacao> transacoes = transacaoRepository.findByFaturaOrderByEfetivadaEmDesc(fatura);
        SaldoResponse saldoResponse = new SaldoResponse(cartao, transacoes);
        return ResponseEntity.ok(saldoResponse);
    }
}

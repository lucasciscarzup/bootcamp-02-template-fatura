package com.zup.lucasciscar.cartaofatura.controller;

import com.zup.lucasciscar.cartaofatura.dto.FaturaResponse;
import com.zup.lucasciscar.cartaofatura.dto.SaldoResponse;
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
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private FaturaRepository faturaRepository;

    @GetMapping("/cartoes/{idCartao}/fatura")
    public ResponseEntity<?> mostrarFatura(@PathVariable("idCartao") UUID idCartao) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        Optional<Fatura> faturaOpt = faturaRepository.findTopByCartaoAndFechadaFalse(cartao);
        Fatura fatura = faturaOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma Fatura em aberto"));

        List<Transacao> transacoes = transacaoRepository.findByFaturaOrderByEfetivadaEmDesc(fatura);
        FaturaResponse faturaResponse = new FaturaResponse(fatura, transacoes);

        return ResponseEntity.ok(faturaResponse);
    }

    @GetMapping("/cartoes/{idCartao}/saldo")
    public ResponseEntity<?> mostrarSaldo(@PathVariable("idCartao") UUID idCartao) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        Optional<Fatura> faturaOpt = faturaRepository.findTopByCartaoAndFechadaFalse(cartao);
        Fatura fatura = faturaOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma Fatura em aberto"));

        SaldoResponse saldoResponse = new SaldoResponse(cartao.getLimite().subtract(fatura.getTotal()));
        return ResponseEntity.ok(saldoResponse);
    }
}

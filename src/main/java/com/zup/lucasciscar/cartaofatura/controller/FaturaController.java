package com.zup.lucasciscar.cartaofatura.controller;

import com.zup.lucasciscar.cartaofatura.dto.request.ParcelamentoRequest;
import com.zup.lucasciscar.cartaofatura.dto.response.FaturaResponse;
import com.zup.lucasciscar.cartaofatura.dto.response.SaldoResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Parcelamento;
import com.zup.lucasciscar.cartaofatura.model.Transacao;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.FaturaRepository;
import com.zup.lucasciscar.cartaofatura.repository.ParcelamentoRepository;
import com.zup.lucasciscar.cartaofatura.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
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
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;

    @GetMapping("/cartoes/{idCartao}/faturas")
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

    @GetMapping("/cartoes/{idCartao}/saldos")
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

    @PostMapping("/cartoes/{idCartao}/faturas/{id}")
    @Transactional
    public ResponseEntity<?> parcelarFatura(@PathVariable("idCartao") UUID idCartao, @PathVariable("id") UUID idFatura,
                                            @RequestBody @Valid ParcelamentoRequest parcelamentoRequest, UriComponentsBuilder builder) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findById(idCartao);
        Cartao cartao = cartaoOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        Optional<Fatura> faturaOpt = faturaRepository.findById(idFatura);
        Fatura fatura = faturaOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Fatura não encontrada"));

        Parcelamento parcelamento = parcelamentoRequest.toModel(fatura);
        parcelamentoRepository.save(parcelamento);

        fatura.fecharFatura();
        faturaRepository.save(fatura);

        URI location = builder.path("/cartoes/{idCartao}/faturas/{idFatura}/parcelamento").build(idCartao, idFatura,
                parcelamento.getId());
        return ResponseEntity.created(location).build();
    }
}

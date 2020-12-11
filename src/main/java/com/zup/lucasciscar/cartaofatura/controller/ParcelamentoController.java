package com.zup.lucasciscar.cartaofatura.controller;

import com.zup.lucasciscar.cartaofatura.client.CartaoClient;
import com.zup.lucasciscar.cartaofatura.dto.request.ParcelamentoClientRequest;
import com.zup.lucasciscar.cartaofatura.dto.request.ParcelamentoRequest;
import com.zup.lucasciscar.cartaofatura.dto.response.ParcelamentoClientResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Parcelamento;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.FaturaRepository;
import com.zup.lucasciscar.cartaofatura.repository.ParcelamentoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ParcelamentoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private FaturaRepository faturaRepository;
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;
    @Autowired
    private CartaoClient cartaoClient;

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

        ParcelamentoClientRequest parcelamentoClientRequest = new ParcelamentoClientRequest(parcelamento);
        try {
            ParcelamentoClientResponse parcelamentoClientResponse = cartaoClient.criarParcelaCartao(cartao.getNumero(), parcelamentoClientRequest);
            if(parcelamentoClientResponse.getResultado().equals(ParcelamentoClientResponse.Resultado.APROVADO))
                parcelamento.setStatus(Parcelamento.Status.APROVADO);
            else
                parcelamento.setStatus(Parcelamento.Status.NEGADO);
        } catch(FeignException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Servidor indisponível");
        } finally {
            parcelamentoRepository.save(parcelamento);
        }

        URI location = builder.path("/cartoes/{idCartao}/faturas/{idFatura}/parcelamento").build(idCartao, idFatura,
                parcelamento.getId());
        return ResponseEntity.created(location).build();
    }
}

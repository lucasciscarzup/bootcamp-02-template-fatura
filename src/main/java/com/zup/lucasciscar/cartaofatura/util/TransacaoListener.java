package com.zup.lucasciscar.cartaofatura.util;

import com.zup.lucasciscar.cartaofatura.client.CartaoClient;
import com.zup.lucasciscar.cartaofatura.dto.response.CartaoClientResponse;
import com.zup.lucasciscar.cartaofatura.dto.response.TransacaoEventResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Transacao;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.FaturaRepository;
import com.zup.lucasciscar.cartaofatura.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TransacaoListener {

    @Autowired
    private CartaoClient cartaoClient;
    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private FaturaRepository faturaRepository;

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    @Transactional
    public void listen(TransacaoEventResponse transacaoEventResponse) {
        String numCartao = transacaoEventResponse.getCartao().getNumero();
        Optional<Cartao> cartaoOpt = cartaoRepository.findByNumero(numCartao);

        Cartao cartao;
        if(cartaoOpt.isPresent())
            cartao = cartaoOpt.get();
        else {
            CartaoClientResponse cartaoClientResponse = cartaoClient.buscarLimiteCartao(numCartao);
            cartao = new Cartao(numCartao, cartaoClientResponse.getLimite());
            cartaoRepository.save(cartao);
        }

        Transacao transacao = transacaoEventResponse.toModel();

        Optional<Fatura> faturaOpt = faturaRepository.findTopByFechadaFalse();
        Fatura fatura;
        if(faturaOpt.isPresent()) {
            fatura = faturaOpt.get();
            fatura.adicionarTotal(transacao.getValor());
        } else {
            fatura = new Fatura(transacao.getValor(), false, cartao);
        }
        faturaRepository.save(fatura);

        transacao.setFatura(fatura);
        transacaoRepository.save(transacao);
    }
}

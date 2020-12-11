package com.zup.lucasciscar.cartaofatura.util;

import com.zup.lucasciscar.cartaofatura.client.CartaoClient;
import com.zup.lucasciscar.cartaofatura.dto.CartaoClientResponse;
import com.zup.lucasciscar.cartaofatura.dto.TransacaoEventResponse;
import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Transacao;
import com.zup.lucasciscar.cartaofatura.repository.CartaoRepository;
import com.zup.lucasciscar.cartaofatura.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TransacaoListener {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;

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

        Transacao transacao = transacaoEventResponse.toModel(cartao);
        transacaoRepository.save(transacao);
    }
}

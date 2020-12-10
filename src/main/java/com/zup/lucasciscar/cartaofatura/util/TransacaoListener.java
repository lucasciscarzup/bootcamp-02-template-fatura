package com.zup.lucasciscar.cartaofatura.util;

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

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    @Transactional
    public void listen(TransacaoEventResponse transacaoEventResponse) {
        Optional<Cartao> cartaoOpt = cartaoRepository.findByNumero(transacaoEventResponse.getCartao().getId());

        Cartao cartao;
        if(cartaoOpt.isPresent())
            cartao = cartaoOpt.get();
        else {
            cartao = transacaoEventResponse.getCartao().toModel();
            cartaoRepository.save(cartao);
        }

        Transacao transacao = transacaoEventResponse.toModel(cartao);
        transacaoRepository.save(transacao);
    }
}

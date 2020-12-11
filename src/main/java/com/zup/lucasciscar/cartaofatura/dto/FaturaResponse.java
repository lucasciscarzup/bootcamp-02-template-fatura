package com.zup.lucasciscar.cartaofatura.dto;

import com.zup.lucasciscar.cartaofatura.model.Transacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FaturaResponse {

    private BigDecimal total;
    private List<TransacaoResponse> transacoes;

    public FaturaResponse(List<Transacao> transacoes) {
        this.total = transacoes.stream().map(transacao -> transacao.getValor())
                .reduce(BigDecimal.ZERO, (atual, prox) -> atual.add(prox));
        this.transacoes = transacoes.stream().map(transacao -> new TransacaoResponse(transacao)).collect(Collectors.toList());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<TransacaoResponse> getTransacoes() {
        return transacoes;
    }
}

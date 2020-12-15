package com.zup.lucasciscar.cartaofatura.dto.response;

import com.zup.lucasciscar.cartaofatura.model.Cartao;
import com.zup.lucasciscar.cartaofatura.model.Transacao;

import java.math.BigDecimal;
import java.util.List;

public class SaldoResponse {

    private BigDecimal saldoDisponivel;

    public SaldoResponse(Cartao cartao, List<Transacao> transacoes) {
        this.saldoDisponivel = cartao.getLimite().subtract(
                transacoes.stream().map(transacao -> transacao.getValor()).reduce(BigDecimal.ZERO, (atual, prox) -> atual.add(prox))
        );
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }
}

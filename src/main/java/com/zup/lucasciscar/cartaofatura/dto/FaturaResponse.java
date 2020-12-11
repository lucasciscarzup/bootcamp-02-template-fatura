package com.zup.lucasciscar.cartaofatura.dto;

import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Transacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FaturaResponse {

    private BigDecimal total;
    private List<TransacaoResponse> transacoes;

    public FaturaResponse(Fatura fatura, List<Transacao> transacoes) {
        this.total = fatura.getTotal();
        this.transacoes = transacoes.stream().map(transacao -> new TransacaoResponse(transacao)).collect(Collectors.toList());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<TransacaoResponse> getTransacoes() {
        return transacoes;
    }
}

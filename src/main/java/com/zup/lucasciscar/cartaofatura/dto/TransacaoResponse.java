package com.zup.lucasciscar.cartaofatura.dto;

import com.zup.lucasciscar.cartaofatura.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponse {

    private BigDecimal valor;
    private EstabelecimentoResponse estabelecimento;
    private LocalDateTime efetivadaEm;

    public TransacaoResponse(Transacao transacao) {
        this.valor = transacao.getValor();
        this.estabelecimento = new EstabelecimentoResponse(transacao.getEstabelecimento());
        this.efetivadaEm = transacao.getEfetivadaEm();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public EstabelecimentoResponse getEstabelecimento() {
        return estabelecimento;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}

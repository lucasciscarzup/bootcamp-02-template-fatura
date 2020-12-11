package com.zup.lucasciscar.cartaofatura.dto.request;

import com.zup.lucasciscar.cartaofatura.model.Parcelamento;

import java.math.BigDecimal;

public class ParcelamentoClientRequest {

    private String identificadorDaFatura;
    private int quantidade;
    private BigDecimal valor;

    public ParcelamentoClientRequest(Parcelamento parcelamento) {
        this.identificadorDaFatura = parcelamento.getFatura().getId().toString();
        this.quantidade = parcelamento.getParcelas();
        this.valor = parcelamento.getValor();
    }

    public String getIdentificadorDaFatura() {
        return identificadorDaFatura;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}

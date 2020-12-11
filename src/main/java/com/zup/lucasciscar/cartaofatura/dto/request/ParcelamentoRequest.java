package com.zup.lucasciscar.cartaofatura.dto.request;

import com.zup.lucasciscar.cartaofatura.model.Fatura;
import com.zup.lucasciscar.cartaofatura.model.Parcelamento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ParcelamentoRequest {

    @NotNull
    @Positive
    private int parcelas;
    @NotNull
    @Positive
    private BigDecimal valor;

    public ParcelamentoRequest(@NotNull @Positive int parcelas, @NotNull @Positive BigDecimal valor) {
        this.parcelas = parcelas;
        this.valor = valor;
    }

    public Parcelamento toModel(Fatura fatura) {
        return new Parcelamento(parcelas, valor, fatura);
    }
}

package com.zup.lucasciscar.cartaofatura.dto.response;

import java.math.BigDecimal;

public class SaldoResponse {

    private BigDecimal saldoDisponivel;

    public SaldoResponse(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }
}

package com.zup.lucasciscar.cartaofatura.dto.response;

import java.math.BigDecimal;

public class CartaoClientResponse {

    private BigDecimal limite;

    public CartaoClientResponse() {}

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}

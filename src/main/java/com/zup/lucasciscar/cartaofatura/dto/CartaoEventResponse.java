package com.zup.lucasciscar.cartaofatura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoEventResponse {

    @JsonProperty("id")
    private String numero;

    public CartaoEventResponse() {}

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }
}
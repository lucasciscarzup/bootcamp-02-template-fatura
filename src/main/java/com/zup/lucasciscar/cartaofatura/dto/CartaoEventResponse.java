package com.zup.lucasciscar.cartaofatura.dto;

import com.zup.lucasciscar.cartaofatura.model.Cartao;

public class CartaoEventResponse {

    private String id;
    private String email;

    public CartaoEventResponse() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cartao toModel() {
        return new Cartao(id, email);
    }

    public String getId() {
        return id;
    }
}
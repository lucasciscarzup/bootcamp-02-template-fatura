package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank
    private String numero;
    @NotNull
    @Positive
    private BigDecimal limite;

    @Deprecated
    public Cartao() {};

    public Cartao(@NotBlank String numero, @NotNull @Positive BigDecimal limite) {
        this.numero = numero;
        this.limite = limite;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}
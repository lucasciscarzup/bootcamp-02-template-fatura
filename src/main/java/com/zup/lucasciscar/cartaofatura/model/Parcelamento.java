package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "parcelamentos")
public class Parcelamento {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @Positive
    private int parcelas;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @Valid
    @OneToOne
    private Fatura fatura;

    @Deprecated
    public Parcelamento() {}

    public Parcelamento(@NotNull @Positive int parcelas, @NotNull @Positive BigDecimal valor,
                        @NotNull @Valid Fatura fatura) {
        this.parcelas = parcelas;
        this.valor = valor;
        this.fatura = fatura;
    }

    public UUID getId() {
        return id;
    }
}

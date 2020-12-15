package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "faturas")
public class Fatura {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;

    public enum Status {
        ABERTA, FECHADA;
    }

    @Deprecated
    public Fatura() {}

    public Fatura(@NotNull @Valid Cartao cartao) {
        this.status = Status.ABERTA;
        this.cartao = cartao;
    }

    public UUID getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

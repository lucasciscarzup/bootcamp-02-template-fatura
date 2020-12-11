package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "faturas")
public class Fatura {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private BigDecimal total;
    @NotNull
    private boolean fechada;
    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;
    @PastOrPresent
    private LocalDateTime fechadaEm;

    @Deprecated
    public Fatura() {}

    public Fatura(@NotNull BigDecimal total, @NotNull boolean fechada, @NotNull @Valid Cartao cartao) {
        this.total = total;
        this.fechada = fechada;
        this.cartao = cartao;
    }

    public void adicionarTotal(BigDecimal valor) {
        total = total.add(valor);
    }

    public BigDecimal getTotal() {
        return total;
    }
}

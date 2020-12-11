package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @Embedded
    private Estabelecimento estabelecimento;
    @NotNull
    @Past
    private LocalDateTime efetivadaEm;
    @Valid
    @ManyToOne
    private Fatura fatura;

    @Deprecated
    public Transacao() {}

    public Transacao(@NotNull @Positive BigDecimal valor, @NotNull Estabelecimento estabelecimento,
                     @NotNull @Past LocalDateTime efetivadaEm) {
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.efetivadaEm = efetivadaEm;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}

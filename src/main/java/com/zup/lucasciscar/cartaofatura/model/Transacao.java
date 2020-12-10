package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String idLegado;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @Embedded
    private Estabelecimento estabelecimento;
    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;
    @NotNull
    @Past
    private LocalDateTime efetivadaEm;

    @Deprecated
    public Transacao() {}

    public Transacao(@NotBlank String idLegado, @NotNull @Positive BigDecimal valor,
                     @NotNull Estabelecimento estabelecimento, @NotNull @Valid Cartao cartao,
                     @NotNull @Past LocalDateTime efetivadaEm) {
        this.idLegado = idLegado;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.cartao = cartao;
        this.efetivadaEm = efetivadaEm;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}

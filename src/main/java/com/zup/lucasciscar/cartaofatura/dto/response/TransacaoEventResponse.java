package com.zup.lucasciscar.cartaofatura.dto.response;

import com.zup.lucasciscar.cartaofatura.model.Estabelecimento;
import com.zup.lucasciscar.cartaofatura.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoEventResponse {

    private BigDecimal valor;
    private EstabelecimentoEventResponse estabelecimento;
    private CartaoEventResponse cartao;
    private LocalDateTime efetivadaEm;

    public TransacaoEventResponse() {}

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setEstabelecimento(EstabelecimentoEventResponse estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public void setCartao(CartaoEventResponse cartao) {
        this.cartao = cartao;
    }

    public void setEfetivadaEm(LocalDateTime efetivadaEm) {
        this.efetivadaEm = efetivadaEm;
    }

    public CartaoEventResponse getCartao() {
        return cartao;
    }

    public Transacao toModel() {
        Estabelecimento estabelecimento = this.estabelecimento.toModel();

        return new Transacao(valor, estabelecimento, efetivadaEm);
    }
}

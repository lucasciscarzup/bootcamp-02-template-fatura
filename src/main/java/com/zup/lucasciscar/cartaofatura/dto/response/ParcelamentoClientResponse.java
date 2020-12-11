package com.zup.lucasciscar.cartaofatura.dto.response;

public class ParcelamentoClientResponse {

    private Resultado resultado;

    public enum Resultado {
        APROVADO, NEGADO;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public Resultado getResultado() {
        return resultado;
    }
}

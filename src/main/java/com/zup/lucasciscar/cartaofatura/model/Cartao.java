package com.zup.lucasciscar.cartaofatura.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank
    private String numero;
    @NotBlank
    @Email
    private String email;

    @Deprecated
    public Cartao() {};

    public Cartao(@NotBlank String numero, @NotBlank @Email String email) {
        this.numero = numero;
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public String getEmail() {
        return email;
    }
}
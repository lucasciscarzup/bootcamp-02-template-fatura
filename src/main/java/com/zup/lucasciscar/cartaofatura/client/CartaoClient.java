package com.zup.lucasciscar.cartaofatura.client;

import com.zup.lucasciscar.cartaofatura.dto.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cartao", url = "${cartao-client.url}")
public interface CartaoClient {

    @GetMapping("/cartoes/{numCartao}")
    CartaoClientResponse buscarLimiteCartao(@PathVariable("numCartao") String numCartao);
}

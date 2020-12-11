package com.zup.lucasciscar.cartaofatura.client;

import com.zup.lucasciscar.cartaofatura.dto.request.ParcelamentoClientRequest;
import com.zup.lucasciscar.cartaofatura.dto.response.CartaoClientResponse;
import com.zup.lucasciscar.cartaofatura.dto.response.ParcelamentoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cartao", url = "${cartao-client.url}")
public interface CartaoClient {

    @GetMapping("/cartoes/{numCartao}")
    CartaoClientResponse buscarLimiteCartao(@PathVariable("numCartao") String numCartao);

    @PostMapping("/cartoes/{numCartao}/parcelas")
    ParcelamentoClientResponse criarParcelaCartao(@PathVariable("numCartao") String numCartao,
                                                  @RequestBody ParcelamentoClientRequest parcelamento);
}

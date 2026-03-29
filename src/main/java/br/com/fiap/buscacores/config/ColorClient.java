package br.com.fiap.buscacores.config;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.bind.annotation.RequestParam;

@HttpExchange("https://www.thecolorapi.com")
public interface ColorClient {

    @GetExchange("/scheme")
    ColorApiResponse getColorScheme(
            @RequestParam("hex") String hex,
            @RequestParam("mode") String mode,
            @RequestParam("count") int count
    );
}
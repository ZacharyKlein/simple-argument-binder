package com.example;

import com.example.binding.ShoppingCart;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/cart")
public class CartController {

    @Get("/")
    HttpResponse<String> loadCart(@ShoppingCart Long sessionId) {
        return HttpResponse.ok("Session:" + sessionId);
    }
}

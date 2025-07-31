package com.hakancivelek.order.product;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/v1/products", contentType= MediaType.APPLICATION_JSON_VALUE)
public interface ProductClientService {

    @GetExchange("/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);

}
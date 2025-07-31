package com.hakancivelek.order.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/api/users", contentType = MediaType.APPLICATION_JSON_VALUE)
public interface UserClientService {

    @GetExchange("/{userId}")
    UserResponse getUser(@PathVariable Long userId);

    @GetExchange
    List<UserResponse> getAllUsers();

}

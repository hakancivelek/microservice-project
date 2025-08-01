package com.hakancivelek.user.service.model;

public record NewUserRequest(
        String email,
        String password
) {
}
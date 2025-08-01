package com.hakancivelek.user.service.model;

public record UserResponse(
        String username,
        String email,
        String firstName,
        String lastName
) {
}

package com.hakancivelek.user.service;

public record UserResponse(
        Long id,
        String username,
        String email,
        String passwordHash,
        String firstName,
        String lastName
) {
}

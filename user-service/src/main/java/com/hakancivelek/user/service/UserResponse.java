package com.hakancivelek.user.service;

public record UserResponse(
        String username,
        String email,
        String passwordHash,
        String firstName,
        String lastName
) {
}

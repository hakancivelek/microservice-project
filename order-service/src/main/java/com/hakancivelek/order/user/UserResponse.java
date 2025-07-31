package com.hakancivelek.order.user;

public record UserResponse(
        String username,
        String email,
        String firstName,
        String lastName
) {

}

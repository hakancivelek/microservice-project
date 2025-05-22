package com.hakancivelek.microservice.user;

public record UserResponse(
	Long id,
	String name,
	String surname,
	int age
) {
}

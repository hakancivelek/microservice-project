package com.hakancivelek.microservice.user;

public record NewUserRequest(
		String name,
		String surname,
		int age) {
}
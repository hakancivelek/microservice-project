package com.hakancivelek.user.service;

import com.hakancivelek.user.service.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String passwordHash;

    private String firstName;

    private String lastName;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .email(user.getEmail().getValue())
                .passwordHash(user.getPassword().getHashedValue())
                .build();
    }
}

package com.hakancivelek.user.service.domain;

import org.springframework.util.DigestUtils;

public class Password {
    private final String hashedValue;

    public Password(String actualPassword) {
        if (actualPassword == null || actualPassword.length() < 6) {
            throw new IllegalArgumentException("Password length should be at least 6 characters");
        }
        this.hashedValue = hashPassword(actualPassword);
    }

    private String hashPassword(String actualPassword) {
        return DigestUtils.md5DigestAsHex(actualPassword.getBytes());
    }

    public boolean verify(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes()).equals(hashedValue);
    }

    public String getHashedValue() {
        return hashedValue;
    }
}

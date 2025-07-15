package com.hakancivelek.user.service;

import com.hakancivelek.user.service.ex.UserAlreadyExistsException;
import com.hakancivelek.user.service.ex.UserCreationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPasswordHash(),
                        user.getFirstName(),
                        user.getLastName()
                ))
                .collect(Collectors.toList());

        return userResponses;
    }

    public UserResponse createUser(NewUserRequest newUserRequest) {
        try {
            if (userRepository.existsByUsername(newUserRequest.username())) {
                throw new UserAlreadyExistsException("Username already exists: " + newUserRequest.username());
            }

            if (userRepository.existsByEmail(newUserRequest.email())) {
                throw new UserAlreadyExistsException("Email already exists: " + newUserRequest.email());
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(newUserRequest.username());
            userEntity.setEmail(newUserRequest.email());
            userEntity.setPasswordHash(newUserRequest.passwordHash());
            userEntity.setFirstName(newUserRequest.firstName());
            userEntity.setLastName(newUserRequest.lastName());

            UserEntity savedUser = userRepository.save(userEntity);

            return mapToUserResponse(savedUser);

        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with this username or email already exists");
        } catch (UserAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user: " + e.getMessage(), e);
        }
    }

    private UserResponse mapToUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    public void deleteUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        userRepository.delete(user);

    }
}

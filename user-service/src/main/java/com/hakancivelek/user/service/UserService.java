package com.hakancivelek.user.service;

import com.hakancivelek.user.service.domain.Email;
import com.hakancivelek.user.service.domain.Password;
import com.hakancivelek.user.service.ex.UserAlreadyExistsException;
import com.hakancivelek.user.service.ex.UserCreationException;
import com.hakancivelek.user.service.ex.UserNotFoundException;
import com.hakancivelek.user.service.model.NewUserRequest;
import com.hakancivelek.user.service.model.UserResponse;
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
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " doesn't exist"));

        return new UserResponse(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName()
                ))
                .collect(Collectors.toList());

        return userResponses;
    }

    public UserResponse registerUser(NewUserRequest newUserRequest) {
        try {

            if (userRepository.existsByEmail(newUserRequest.email())) {
                throw new UserAlreadyExistsException("Email already exists: " + newUserRequest.email());
            }

            Email email = new Email(newUserRequest.email());
            Password password = new Password(newUserRequest.password());

            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(newUserRequest.email());
            userEntity.setPasswordHash(newUserRequest.password());

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
                userEntity.getUsername(),
                userEntity.getEmail(),
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

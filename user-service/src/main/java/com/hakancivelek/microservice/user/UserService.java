package com.hakancivelek.microservice.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse createUser(NewUserRequest newUserRequest){
        var userEntity = new UserEntity();
        userEntity.setName(newUserRequest.name());
        userEntity.setSurname(newUserRequest.surname());
        userEntity.setAge(newUserRequest.age());

        return toResponse(userRepository.save(userEntity));
    }

    private UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(),
        userEntity.getName(), 
        userEntity.getSurname(), 
        userEntity.getAge());
    }

    public List<UserResponse> getAllUsers() {
        var userEntities = userRepository.findAll();
        return userEntities.stream().map(this::toResponse).toList();
    }

    public ResponseEntity<UserResponse> getUserById(Long userId) {
        Optional<UserResponse> user = userRepository.findById(userId).map(this::toResponse);
        return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build()); 
    }

    public ResponseEntity<Void> deleteUser(Long userId) {
        boolean isDeleted;
        
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            isDeleted = true;
        }
        else 
            isDeleted =  false;

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
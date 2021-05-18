package com.example.redStore.service;

import com.example.redStore.dto.UserDTO;
import com.example.redStore.entity.User;
import com.example.redStore.enums.Role;
import com.example.redStore.enums.Status;
import com.example.redStore.exception.UserNotFoundException;
import com.example.redStore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User create(User user) {
        return isValid(user) ?
                repository.save(user) : null;
    }

    public User create(UserDTO userDTO) {
        return isValid(userDTO) ?
                repository.save(converUserDTO(userDTO)) : null;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + id + " was not found"));
    }

    public User getByUserName(String userName) {
        return repository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("user with userName: " + userName + " was not found"));
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user with email: " + email + " was not found"));
    }

    public boolean isValid(UserDTO userDTO) {
        return isPasswordValid(userDTO.getPassword());
    }

    public boolean isValid(User user) {
        return isPasswordValid(user.getPassword());
    }

    private boolean isExists(String username, String email) {
        return getByUserName(username)==null && getByEmail(email)==null;
    }

    private boolean isPasswordValid(String password) {
        return password.length()>=8;
    }

    private User converUserDTO(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getEmail(), encoder.encode(userDTO.getPassword()), Role.USER, Status.ACTIVE);
    }
}

package com.example.redstore.service;

import com.example.redstore.dto.UserDTO;
import com.example.redstore.entity.User;
import com.example.redstore.enums.Role;
import com.example.redstore.enums.Status;
import com.example.redstore.exception.UserNotFoundException;
import com.example.redstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.redstore.constants.Constants.*;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return isValid(user) ?
                repository.save(user) : null;
    }

    public User create(UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        return isValid(userDTO) ?
                repository.save(convertUserDTO(userDTO)) : null;
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(WITH_ID_WAS_NOT_FOUND, USER_ATTRIBUTE, id)));
    }

    public User getByUserName(String userName) {
        return repository.findByUserName(userName)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(WITH_USERNAME_WAS_NOT_FOUND, USER_ATTRIBUTE, userName)));
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(WITH_EMAIL_WAS_NOT_FOUND, USER_ATTRIBUTE, email)));
    }

    public boolean isValid(UserDTO userDTO) {
        return !isExists(userDTO.getUsername(), userDTO.getEmail()) && isPasswordValid(userDTO.getPassword());
    }

    public boolean isValid(User user) {
        return !isExists(user.getUserName(), user.getEmail()) && isPasswordValid(user.getPassword());
    }

    private boolean isExists(String username, String email) {
        return repository.findByUserName(username).isPresent() || repository.findByEmail(email).isPresent();
    }

    private boolean isPasswordValid(String password) {
        return password.length()>=8;
    }

    private User convertUserDTO(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getEmail(), encoder.encode(userDTO.getPassword()), Role.USER, Status.ACTIVE);
    }
}

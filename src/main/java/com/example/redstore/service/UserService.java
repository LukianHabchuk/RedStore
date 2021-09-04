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
        return repository.save(user);
    }

    public User create(UserDTO userDTO) {
        var user = convertUserDTO(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        return repository.save(user);
    }

    public void update(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.deleteById(user.getId());
        repository.save(user);
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(WITH_S_WAS_NOT_FOUND, USER_ATTRIBUTE, "email", email)));
    }

    public boolean isExists(String username, String email) {
        return repository.findByUserName(username).isPresent() || repository.findByEmail(email).isPresent();
    }

    private User convertUserDTO(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getEmail(), encoder.encode(userDTO.getPassword()), Role.USER, Status.ACTIVE);
    }
}

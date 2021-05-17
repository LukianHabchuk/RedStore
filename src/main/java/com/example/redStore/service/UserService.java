package com.example.redStore.service;

import com.example.redStore.entity.User;
import com.example.redStore.exception.UserNotFoundException;
import com.example.redStore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User product) {
        return repository.save(product);
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

}

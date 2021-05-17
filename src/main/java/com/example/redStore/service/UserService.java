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
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("user with id: " + id + " was not found"));
    }

}

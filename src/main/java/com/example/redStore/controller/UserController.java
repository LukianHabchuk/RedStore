package com.example.redStore.controller;

import com.example.redStore.dto.UserDTO;
import com.example.redStore.entity.User;
import com.example.redStore.enums.Role;
import com.example.redStore.enums.Status;
import com.example.redStore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;
    private final PasswordEncoder encoder;

    public UserController(UserService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping("/login?succes")
    public String loginSucces() {
        return "redirect:/cart";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDTO") UserDTO userDTO) {
        service.create(new User(userDTO.getUsername(), userDTO.getEmail(), encoder.encode(userDTO.getPassword()), Role.USER, Status.ACTIVE));
        return "redirect:/account";
    }
}

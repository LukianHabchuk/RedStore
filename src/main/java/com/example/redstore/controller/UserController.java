package com.example.redstore.controller;

import com.example.redstore.dto.UserDTO;
import com.example.redstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login?succes")
    public String loginSuccess() {
        return "redirect:/cart";
    }

    @PostMapping("/registration")
    public String registration(UserDTO userDTO, Model model) {
        if (service.isExists(userDTO.getUsername(), userDTO.getEmail()))
            model.addAttribute("message",
                    String.format("User with email: %s or username: %s already exists",
                            userDTO.getEmail(), userDTO.getUsername()));
        else service.create(userDTO);
        return "account";
    }
}

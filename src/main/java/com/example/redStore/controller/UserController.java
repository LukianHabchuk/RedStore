package com.example.redStore.controller;

import com.example.redStore.dto.UserDTO;
import com.example.redStore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login?succes")
    public String loginSucces() {
        return "redirect:/cart";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDTO") UserDTO userDTO) {
        service.create(userDTO);
        return "redirect:/account";
    }
}

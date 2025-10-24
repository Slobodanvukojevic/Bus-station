package com.busstation.controller;

import com.busstation.model.User;
import com.busstation.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam @NotBlank String username,
                           @RequestParam @Email String email,
                           @RequestParam @NotBlank String password,
                           Model model) {
        try {
            User u = userService.register(username, email, password);
            model.addAttribute("msg", "Uspešna registracija za: " + u.getUsername());
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/")
    public String home(Authentication auth, Model model) {
        model.addAttribute("auth", auth);
        return "index";
    }
}

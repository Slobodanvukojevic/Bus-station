package com.busstation.controller;

import com.busstation.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/")
    public String home(Authentication auth, Model model) {
        model.addAttribute("auth", auth);
        return "index";
    }

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
            userService.register(username, email, password);
            model.addAttribute("msg", "Uspešna registracija. Možete se prijaviti.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

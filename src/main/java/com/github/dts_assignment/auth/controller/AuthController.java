package com.github.dts_assignment.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    private static final String REDIRECT_HOME = "redirect:/";

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        return isAuthenticated(authentication) ? REDIRECT_HOME : "login";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/logout-success")
    public String logoutSuccessPage(Authentication authentication) {
        return isAuthenticated(authentication) ? REDIRECT_HOME : "logout-success";
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}

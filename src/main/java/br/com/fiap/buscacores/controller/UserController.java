package br.com.fiap.buscacores.controller;

import br.com.fiap.buscacores.security.LoginRequest;
import br.com.fiap.buscacores.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(@RequestBody LoginRequest request, Authentication authentication) {
        userService.deleteAccount(authentication.getName(), request.getPassword());
    }
}
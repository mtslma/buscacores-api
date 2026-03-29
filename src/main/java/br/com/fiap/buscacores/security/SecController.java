package br.com.fiap.buscacores.security;

import br.com.fiap.buscacores.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public record TokenResponse(String token) {}

    public SecController(TokenService tokenService,
                         @Lazy AuthenticationManager authenticationManager,
                         UserRepository userRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public TokenResponse token(@RequestBody LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return new TokenResponse(tokenService.generateToken(authentication.getName()));
    }

    @PostMapping("/login/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        try {
            br.com.fiap.buscacores.model.User user = new br.com.fiap.buscacores.model.User();
            user.setUsername(request.getUsername());
            user.setPassword("{noop}" + request.getPassword());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro: Já existe um usuário com o nome '" + request.getUsername() + "'");
        }
    }
}
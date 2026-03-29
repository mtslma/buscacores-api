package br.com.fiap.buscacores.service;

import br.com.fiap.buscacores.model.User;
import br.com.fiap.buscacores.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteAccount(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals("{noop}" + password)) {
            throw new RuntimeException("Senha incorreta. Não foi possível deletar a conta.");
        }

        userRepository.delete(user);
    }
}
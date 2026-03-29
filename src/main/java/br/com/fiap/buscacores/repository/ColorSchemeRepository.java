package br.com.fiap.buscacores.repository;

import br.com.fiap.buscacores.model.ColorScheme;
import br.com.fiap.buscacores.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorSchemeRepository extends JpaRepository<ColorScheme, Long> {
    // Retorna uma página de paletas públicas
    Page<ColorScheme> findByIsPublicTrue(Pageable pageable);

    // Retorna uma página de paletas de um usuário específico
    Page<ColorScheme> findByUser(User user, Pageable pageable);
}
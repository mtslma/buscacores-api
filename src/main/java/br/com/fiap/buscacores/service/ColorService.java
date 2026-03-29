package br.com.fiap.buscacores.service;

import br.com.fiap.buscacores.config.ColorClient;
import br.com.fiap.buscacores.model.ColorScheme;
import br.com.fiap.buscacores.model.User;
import br.com.fiap.buscacores.repository.ColorSchemeRepository;
import br.com.fiap.buscacores.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColorService {
    private final ColorClient colorClient;
    private final ColorSchemeRepository colorSchemeRepository;
    private final UserRepository userRepository;

    public ColorService(ColorClient colorClient, ColorSchemeRepository colorSchemeRepository, UserRepository userRepository) {
        this.colorClient = colorClient;
        this.colorSchemeRepository = colorSchemeRepository;
        this.userRepository = userRepository;
    }

    public Page<ColorScheme> listPublicPalettes(Pageable pageable, String currentUser) {
        Page<ColorScheme> palettes = colorSchemeRepository.findByIsPublicTrue(pageable);
        if (currentUser != null) {
            palettes.forEach(p -> p.setLiked(p.getLikedByUsers().contains(currentUser)));
        }
        return palettes;
    }

    public Page<ColorScheme> listMyPalettes(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Page<ColorScheme> palettes = colorSchemeRepository.findByUser(user, pageable);
        palettes.forEach(p -> p.setLiked(p.getLikedByUsers().contains(username)));
        return palettes;
    }

    @Transactional
    public ColorScheme likePalette(Long id, String username) {
        ColorScheme scheme = colorSchemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paleta não encontrada"));

        if (scheme.getLikedByUsers().contains(username)) {
            scheme.getLikedByUsers().remove(username);
        } else {
            scheme.getLikedByUsers().add(username);
        }

        scheme.setLikesCount(scheme.getLikedByUsers().size());
        ColorScheme saved = colorSchemeRepository.save(scheme);
        saved.setLiked(saved.getLikedByUsers().contains(username));
        return saved;
    }

    public ColorScheme generateAndSave(String name, String hex, boolean isPublic, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        var response = colorClient.getColorScheme(hex.replace("#", ""), "analogic", 5);

        ColorScheme scheme = new ColorScheme();
        scheme.setName(name);
        scheme.setPublic(isPublic);
        scheme.setPrimaryColor(hex);
        scheme.setSecondaryColor(response.getColors().get(1).getHex().getValue());
        scheme.setThirdColor(response.getColors().get(2).getHex().getValue());
        scheme.setFourthColor(response.getColors().get(3).getHex().getValue());
        scheme.setFifthColor(response.getColors().get(4).getHex().getValue());
        scheme.setUser(user);

        return colorSchemeRepository.save(scheme);
    }

    public ColorScheme getById(Long id, String username) {
        ColorScheme scheme = colorSchemeRepository.findById(id).orElseThrow();
        if (!scheme.isPublic() && (username == null || !scheme.getUser().getUsername().equals(username))) {
            throw new RuntimeException("Acesso negado");
        }
        if (username != null) {
            scheme.setLiked(scheme.getLikedByUsers().contains(username));
        }
        return scheme;
    }

    public ColorScheme updatePalette(Long id, String name, String hex, boolean isPublic, String username) {
        ColorScheme scheme = colorSchemeRepository.findById(id).orElseThrow();
        if (!scheme.getUser().getUsername().equals(username)) throw new RuntimeException("Acesso negado");

        scheme.setName(name);
        scheme.setPublic(isPublic);

        if (!scheme.getPrimaryColor().equalsIgnoreCase(hex)) {
            var response = colorClient.getColorScheme(hex.replace("#", ""), "analogic", 5);
            scheme.setPrimaryColor(hex);
            scheme.setSecondaryColor(response.getColors().get(1).getHex().getValue());
            scheme.setThirdColor(response.getColors().get(2).getHex().getValue());
            scheme.setFourthColor(response.getColors().get(3).getHex().getValue());
            scheme.setFifthColor(response.getColors().get(4).getHex().getValue());
        }

        ColorScheme saved = colorSchemeRepository.save(scheme);
        saved.setLiked(saved.getLikedByUsers().contains(username));
        return saved;
    }

    public void deletePalette(Long id, String username) {
        ColorScheme scheme = colorSchemeRepository.findById(id).orElseThrow();
        if (!scheme.getUser().getUsername().equals(username)) throw new RuntimeException("Acesso negado");
        colorSchemeRepository.delete(scheme);
    }
}
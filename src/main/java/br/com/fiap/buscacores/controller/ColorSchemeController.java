package br.com.fiap.buscacores.controller;

import br.com.fiap.buscacores.model.ColorScheme;
import br.com.fiap.buscacores.service.ColorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/palette")
public class ColorSchemeController {

    private final ColorService colorService;

    public ColorSchemeController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/explore")
    public Page<ColorScheme> getPublicPalettes(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        String username = (authentication != null) ? authentication.getName() : null;
        return colorService.listPublicPalettes(pageable, username);
    }

    @GetMapping("/my-palettes")
    public Page<ColorScheme> getMyPalettes(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return colorService.listMyPalettes(authentication.getName(), pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColorScheme savePalette(@RequestBody ColorSchemeRequest request, Authentication authentication) {
        return colorService.generateAndSave(request.name(), request.hex(), request.isPublic(), authentication.getName());
    }

    @GetMapping("/{id}")
    public ColorScheme getById(@PathVariable Long id, Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : null;
        return colorService.getById(id, username);
    }

    @PutMapping("/{id}")
    public ColorScheme update(@PathVariable Long id, @RequestBody ColorSchemeRequest request, Authentication authentication) {
        return colorService.updatePalette(id, request.name(), request.hex(), request.isPublic(), authentication.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePalette(@PathVariable Long id, Authentication authentication) {
        colorService.deletePalette(id, authentication.getName());
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public ColorScheme like(@PathVariable Long id, Authentication authentication) {
        return colorService.likePalette(id, authentication.getName());
    }

    public record ColorSchemeRequest(String name, String hex, boolean isPublic) {}
}
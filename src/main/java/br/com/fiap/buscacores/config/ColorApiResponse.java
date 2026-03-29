package br.com.fiap.buscacores.config;

import lombok.Data;
import java.util.List;

@Data
public class ColorApiResponse {
    private List<PaletteColor> colors;

    @Data
    public static class PaletteColor {
        private Hex hex;
    }

    @Data
    public static class Hex {
        private String value; // Ex: #FFFFFF
    }
}
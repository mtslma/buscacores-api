package br.com.fiap.buscacores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "color_schemes")
@Data
@JsonPropertyOrder({ "id", "name", "isPublic", "isLiked", "likesCount", "createdAt", "primaryColor", "secondaryColor", "thirdColor", "fourthColor", "fifthColor", "user" })
public class ColorScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty("isPublic")
    private boolean isPublic = false;

    @Column(name = "likes_count")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int likesCount = 0;

    @ElementCollection
    @JsonIgnore
    private Set<String> likedByUsers = new HashSet<>();

    @Transient
    @JsonProperty("isLiked")
    private boolean isLiked;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private String primaryColor;
    private String secondaryColor;
    private String thirdColor;
    private String fourthColor;
    private String fifthColor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("colorSchemes")
    private User user;
}
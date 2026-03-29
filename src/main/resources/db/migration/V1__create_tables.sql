-- Tabela de Usuários
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabela de Paletas (Color Schemes)
CREATE TABLE color_schemes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    is_public BOOLEAN DEFAULT FALSE,
    likes_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary_color VARCHAR(7),
    secondary_color VARCHAR(7),
    third_color VARCHAR(7),
    fourth_color VARCHAR(7),
    fifth_color VARCHAR(7),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_scheme FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela de Relacionamento para Likes Únicos
CREATE TABLE color_scheme_liked_by_users (
    color_scheme_id BIGINT NOT NULL,
    liked_by_users VARCHAR(255) NOT NULL,
    CONSTRAINT fk_color_scheme_likes FOREIGN KEY (color_scheme_id) REFERENCES color_schemes (id) ON DELETE CASCADE
);
-- Inserção de usuários
INSERT INTO users (username, password) VALUES ('admin', '{noop}123456');
INSERT INTO users (username, password) VALUES ('mateuslima', '{noop}123456');

-- Inserção de paletas para o mateuslima
INSERT INTO color_schemes (
    name, is_public, likes_count, primary_color, secondary_color,
    third_color, fourth_color, fifth_color, user_id
) VALUES (
    'Sunset Vibes', true, 0, '#FF5733', '#C70039', '#900C3F', '#581845', '#FFC300',
    (SELECT id FROM users WHERE username = 'mateuslima')
);

INSERT INTO color_schemes (
    name, is_public, likes_count, primary_color, secondary_color,
    third_color, fourth_color, fifth_color, user_id
) VALUES (
    'Ocean Deep', true, 0, '#0077B6', '#00B4D8', '#90E0EF', '#03045E', '#023E8A',
    (SELECT id FROM users WHERE username = 'mateuslima')
);
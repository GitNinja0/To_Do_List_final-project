-- Insert roles
INSERT INTO rol (id_role, name) VALUES (1, 'USER') ON CONFLICT (id_role) DO UPDATE SET name=EXCLUDED.name;
INSERT INTO rol (id_role, name) VALUES (2, 'ADMIN') ON CONFLICT (id_role) DO UPDATE SET name=EXCLUDED.name;
INSERT INTO rol (id_role, name) VALUES (3, 'GESTOR') ON CONFLICT (id_role) DO UPDATE SET name=EXCLUDED.name;


-- Insert users (password for all is 'password' encrypted with BCrypt)
-- Make sure your SecurityConfig has a BCryptPasswordEncoder bean
INSERT INTO users (id_user, username, password, email) VALUES (2, 'user2', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user2@example.com') ON CONFLICT (id_user) DO UPDATE SET username=EXCLUDED.username, password=EXCLUDED.password;
INSERT INTO users (id_user, username, password, email) VALUES (4, 'gestor1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'gestor1@example.com') ON CONFLICT (id_user) DO UPDATE SET username=EXCLUDED.username, password=EXCLUDED.password;
INSERT INTO users (id_user, username, password, email) VALUES (1, 'user1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user1@example.com') ON CONFLICT (id_user) DO UPDATE SET username=EXCLUDED.username, password=EXCLUDED.password;
INSERT INTO users (id_user, username, password, email) VALUES (3, 'admin1', '$2a$12$ALZ3r8Dx5MaTd0IDEYr2ROAtFnuKGRYiRGW9TCOM4uChBgxACC7.K', 'admin1@example.com') ON CONFLICT (id_user) DO UPDATE SET username=EXCLUDED.username, password=EXCLUDED.password;

-- Associate users with roles
-- Assuming role_users has a composite primary key or unique constraint on (user_id, role_id)
INSERT INTO role_users (user_id, role_id) VALUES (1, 1) ON CONFLICT DO NOTHING; -- user1 -> USER
INSERT INTO role_users (user_id, role_id) VALUES (2, 1) ON CONFLICT DO NOTHING; -- user2 -> USER
INSERT INTO role_users (user_id, role_id) VALUES (3, 2) ON CONFLICT DO NOTHING; -- admin1 -> ADMIN
INSERT INTO role_users (user_id, role_id) VALUES (4, 3) ON CONFLICT DO NOTHING; -- gestor1 -> GESTOR

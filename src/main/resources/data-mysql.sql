-- Insert roles
INSERT IGNORE INTO rol (id_role, name) VALUES (1, 'USER');
INSERT IGNORE INTO rol (id_role, name) VALUES (2, 'ADMIN');
INSERT IGNORE INTO rol (id_role, name) VALUES (3, 'GESTOR');

-- Insert users (password for all is 'password' encrypted with BCrypt)
-- Make sure your SecurityConfig has a BCryptPasswordEncoder bean
INSERT IGNORE INTO users (id_user, username, password, email) VALUES (2, 'user2', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user2@example.com');
INSERT IGNORE INTO users (id_user, username, password, email) VALUES (4, 'gestor1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'gestor1@example.com');
INSERT IGNORE INTO users (id_user, username, password, email) VALUES (1, 'user1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user1@example.com');
INSERT IGNORE INTO users (id_user, username, password, email) VALUES (3, 'admin1', '$2a$12$ALZ3r8Dx5MaTd0IDEYr2ROAtFnuKGRYiRGW9TCOM4uChBgxACC7.K', 'admin1@example.com');

-- Associate users with roles
INSERT IGNORE INTO role_users (user_id, role_id) VALUES (1, 1); -- user1 -> USER
INSERT IGNORE INTO role_users (user_id, role_id) VALUES (2, 1); -- user2 -> USER
INSERT IGNORE INTO role_users (user_id, role_id) VALUES (3, 2); -- admin1 -> ADMIN
INSERT IGNORE INTO role_users (user_id, role_id) VALUES (4, 3); -- gestor1 -> GESTOR

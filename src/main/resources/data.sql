-- Insert roles
INSERT INTO rol (id_role, name) VALUES (1, 'USER') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO rol (id_role, name) VALUES (2, 'ADMIN') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO rol (id_role, name) VALUES (3, 'GESTOR') ON DUPLICATE KEY UPDATE name=name;


-- Insert users (password for all is 'password' encrypted with BCrypt)
-- Make sure your SecurityConfig has a BCryptPasswordEncoder bean
INSERT INTO user (id_user, username, password, email) VALUES (2, 'user2', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user2@example.com') ON DUPLICATE KEY UPDATE username=username;
INSERT INTO user (id_user, username, password, email) VALUES (4, 'gestor1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'gestor1@example.com') ON DUPLICATE KEY UPDATE username=username;
INSERT INTO user (id_user, username, password, email) VALUES (1, 'user1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'user1@example.com') ON DUPLICATE KEY UPDATE username=username;
INSERT INTO user (id_user, username, password, email) VALUES (3, 'admin1', '$2a$12$dBjHEIouC5bmFlk5HNETAO7.48QpgLyy3LG3pqItwIFkXdP495WhO', 'admin1@example.com') ON DUPLICATE KEY UPDATE username=username;

-- Associate users with roles
INSERT INTO role_users (user_id, role_id) VALUES (1, 1) ON DUPLICATE KEY UPDATE user_id=user_id; -- user1 -> USER
INSERT INTO role_users (user_id, role_id) VALUES (2, 1) ON DUPLICATE KEY UPDATE user_id=user_id; -- user2 -> USER
INSERT INTO role_users (user_id, role_id) VALUES (3, 2) ON DUPLICATE KEY UPDATE user_id=user_id; -- admin1 -> ADMIN
INSERT INTO role_users (user_id, role_id) VALUES (4, 3) ON DUPLICATE KEY UPDATE user_id=user_id; -- gestor1 -> GESTOR

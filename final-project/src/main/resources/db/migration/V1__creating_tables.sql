-- Таблица ролей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(20) UNIQUE NOT NULL
);

-- Таблица пользователей
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL
);

-- Таблица связи пользователей и ролей (Many-to-Many для гибкости)
CREATE TABLE user_roles (
                            user_id BIGINT REFERENCES users(id),
                            role_id INTEGER REFERENCES roles(id),
                            PRIMARY KEY (user_id, role_id)
);

-- Таблица записей в дневнике (TimeLogs)
CREATE TABLE time_logs (
                           id BIGSERIAL PRIMARY KEY,
                           traveler_id BIGINT REFERENCES users(id) NOT NULL,
                           destination_time TIMESTAMP NOT NULL, -- Куда прибыл
                           location_name VARCHAR(255) NOT NULL,
                           description TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Когда сделал запись
);

-- Таблица аудита (для RBAC логов)
CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,
                            action_type VARCHAR(50) NOT NULL, -- LOGIN, DELETE_LOG, CREATE_LOG
                            performed_by BIGINT REFERENCES users(id),
                            details TEXT,
                            action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Начальные данные
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_TRAVELER');
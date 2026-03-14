CREATE TABLE posts (
   id          UUID PRIMARY KEY,
   user_id     VARCHAR(100) NOT NULL,
   content     TEXT NOT NULL,
   hashtags    TEXT[],
   status      VARCHAR(50) NOT NULL,
   created_at  TIMESTAMP NOT NULL
);
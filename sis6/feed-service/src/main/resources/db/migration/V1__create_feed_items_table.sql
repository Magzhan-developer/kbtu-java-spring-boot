CREATE TABLE feed_items (
    post_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    hashtags TEXT[],
    created_at TIMESTAMP NOT NULL
);
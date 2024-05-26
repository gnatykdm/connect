CREATE TABLE users (
           id SERIAL PRIMARY KEY,
           username VARCHAR(50) UNIQUE NOT NULL,
           password VARCHAR(255) NOT NULL
);

CREATE TABLE messages (
            message_id SERIAL PRIMARY KEY,
            sender_id INT NOT NULL,
            receiver_id INT NOT NULL,
            content TEXT NOT NULL,
            sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
            FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_sender_id ON messages(sender_id);
CREATE INDEX idx_receiver_id ON messages(receiver_id);

CREATE TABLE friendships (
            user1_id INT NOT NULL,
            user2_id INT NOT NULL,
            PRIMARY KEY (user1_id, user2_id),
            FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
            FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_user1_id ON friendships(user1_id);
CREATE INDEX idx_user2_id ON friendships(user2_id);


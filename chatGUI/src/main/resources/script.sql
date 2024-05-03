-- Creating Table of users
CREATE TABLE Users (
          user_id SERIAL PRIMARY KEY,
          username VARCHAR(255) NOT NULL,
          password_hash VARCHAR(255) NOT NULL,
          email VARCHAR(255) NOT NULL,
          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating Table of Conversations
CREATE TABLE Conversations (
          conversation_id SERIAL PRIMARY KEY,
          conversation_name VARCHAR(255),
          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating Table of Messages
CREATE TABLE Messages (
        message_id SERIAL PRIMARY KEY,
        conversation_id INT REFERENCES Conversations(conversation_id),
        sender_id INT REFERENCES Users(user_id),
        message_text TEXT NOT NULL,
        sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating Table of Participants
CREATE TABLE Participants (
        participant_id SERIAL PRIMARY KEY,
        conversation_id INT REFERENCES Conversations(conversation_id),
        user_id INT REFERENCES Users(user_id)
);

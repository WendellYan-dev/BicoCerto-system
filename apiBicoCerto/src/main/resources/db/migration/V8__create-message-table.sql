CREATE TABLE Message (
    id_message SERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL ,
    sent_at TIMESTAMP NOT NULL ,
    id_user INT NOT NULL,
    id_chat INT NOT NULL,
    CONSTRAINT fk_message_user
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user),
    CONSTRAINT fk_message_chat
        FOREIGN KEY (id_chat)
            REFERENCES Chat(id_chat) ON DELETE CASCADE
);
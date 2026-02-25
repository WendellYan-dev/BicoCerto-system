CREATE TABLE Chat (
    id_chat SERIAL PRIMARY KEY NOT NULL ,
    id_user INT NOT NULL,
    id_informal_worker INT NOT NULL,

    CONSTRAINT fk_chat_user
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user),
    CONSTRAINT fk_chat_worker
        FOREIGN KEY (id_informal_worker)
            REFERENCES InformalWorker(id_informal_worker)
);
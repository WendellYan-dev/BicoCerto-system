CREATE TABLE InformalWorker (
    id_informal_worker SERIAL PRIMARY KEY NOT NULL ,
    service_category VARCHAR(45) NOT NULL,
    about_me VARCHAR(255) NOT NULL,
    local_service VARCHAR(45) NOT NULL,
    id_user INT NOT NULL,

    CONSTRAINT fk_user_informal
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user)
            ON DELETE CASCADE
);
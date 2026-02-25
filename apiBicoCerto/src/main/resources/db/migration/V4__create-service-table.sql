CREATE TABLE Service (
    id_service SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(45) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL ,
    url_photo VARCHAR(255) NOT NULL,

    id_informal_worker INTEGER NOT NULL,

    CONSTRAINT fk_service_informalworker
        FOREIGN KEY (id_informal_worker)
            REFERENCES InformalWorker(id_informal_worker)
            ON DELETE CASCADE
);
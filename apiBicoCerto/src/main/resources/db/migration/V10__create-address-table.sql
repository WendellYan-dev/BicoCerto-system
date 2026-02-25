CREATE TABLE Address (
    id_address SERIAL PRIMARY KEY NOT NULL,
    id_user INT NOT NULL,
    postal_code VARCHAR(11) NOT NULL,
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL ,
    state VARCHAR(2) NOT NULL ,
    number VARCHAR(10) NOT NULL ,
    complement VARCHAR(255),
    is_primary BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_address_user
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user)
            ON DELETE CASCADE
);
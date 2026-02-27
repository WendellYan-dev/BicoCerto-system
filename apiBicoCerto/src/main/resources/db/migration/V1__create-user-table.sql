CREATE TABLE Users (
    id_user SERIAL PRIMARY KEY NOT NULL,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(125) NOT NULL,
    last_name VARCHAR(125) NOT NULL,
    phone_number VARCHAR(11) NOT NULL,
    birth_date DATE NOT NULL,
    password VARCHAR(255) NOT NULL,
    cpf VARCHAR(14)  UNIQUE,
    cnpj VARCHAR(18) UNIQUE,
    profile_photo VARCHAR(255),
    register_date TIMESTAMP NOT NULL,
    status VARCHAR(20)  NOT NULL ,
    user_type VARCHAR(20) NOT NULL,

    CONSTRAINT check_only_one_document
        CHECK (
            (cpf IS NOT NULL AND cnpj IS NULL)
                OR
            (cpf IS NULL AND cnpj IS NOT NULL)
            )
);
CREATE TABLE Users (
    id_user SERIAL PRIMARY KEY NOT NULL,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(125) NOT NULL,
    last_name VARCHAR(125) NOT NULL,
    telephone VARCHAR(11) NOT NULL,
    date_birth DATE NOT NULL,
    password VARCHAR(255) NOT NULL,
    cpf VARCHAR(14)  UNIQUE,
    cnpj VARCHAR(18) UNIQUE,
    photo_profile VARCHAR(255),
    date_register TIMESTAMP NOT NULL,
    status VARCHAR(20)  NOT NULL ,

    CONSTRAINT check_only_one_document
        CHECK (
            (cpf IS NOT NULL AND cnpj IS NULL)
                OR
            (cpf IS NULL AND cnpj IS NOT NULL)
            )
);
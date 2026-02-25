CREATE TABLE Payment (
    id_payment SERIAL PRIMARY KEY NOT NULL ,
    id_booking INTEGER NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment_method VARCHAR(45) NOT NULL,
    payment_date TIMESTAMP NOT NULL ,

    CONSTRAINT fk_payment_booking
        FOREIGN KEY (id_booking)
            REFERENCES Booking(id_booking)
            ON DELETE CASCADE
);
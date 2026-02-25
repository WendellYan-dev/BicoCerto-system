CREATE TABLE Booking (
    id_booking SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_service INTEGER NOT NULL,
    booking_date TIMESTAMP NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    notes VARCHAR(255),

    CONSTRAINT fk_booking_user
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user)
            ON DELETE CASCADE,

    CONSTRAINT fk_booking_service
        FOREIGN KEY (id_service)
            REFERENCES Service(id_service)
            ON DELETE CASCADE
);
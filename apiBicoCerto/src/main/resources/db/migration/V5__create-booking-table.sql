CREATE TABLE Booking (
    id_booking SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_work INTEGER NOT NULL,
    id_informal_worker INTEGER NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    notes VARCHAR(255),

    CONSTRAINT fk_booking_user
        FOREIGN KEY (id_user)
            REFERENCES Users(id_user)
            ON DELETE CASCADE,

    CONSTRAINT fk_booking_work
        FOREIGN KEY (id_work)
            REFERENCES Work(id_work)
            ON DELETE CASCADE,

    CONSTRAINT fk_booking_worker
        FOREIGN KEY (id_informal_worker)
            REFERENCES InformalWorker(id_informal_worker)
            ON DELETE CASCADE


);
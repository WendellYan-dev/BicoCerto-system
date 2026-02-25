CREATE TABLE Availability (
    id_availability SERIAL PRIMARY KEY NOT NULL ,
    day_of_week VARCHAR(20) NOT NULL ,
    start_time TIME NOT NULL ,
    end_time TIME NOT NULL ,
    id_informal_worker INT NOT NULL,

    CONSTRAINT fk_worker_availability
        FOREIGN KEY (id_informal_worker)
            REFERENCES InformalWorker(id_informal_worker)
            ON DELETE CASCADE
);
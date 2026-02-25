CREATE TABLE Review (
    id_review SERIAL PRIMARY KEY,
    id_reviewer INT NOT NULL,
    id_reviewed INT NOT NULL,
    id_booking INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(255),
    review_date TIMESTAMP NOT NULL ,

    CONSTRAINT fk_review_Reviewer
        FOREIGN KEY (id_reviewer)
            REFERENCES Users(id_user),
    CONSTRAINT fk_review_Reviewed
        FOREIGN KEY (id_reviewed)
            REFERENCES Users(id_user),
    CONSTRAINT fk_review_booking
        FOREIGN KEY (id_booking) REFERENCES
            Booking(id_booking) ON DELETE CASCADE
);
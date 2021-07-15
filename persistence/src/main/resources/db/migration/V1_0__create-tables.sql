CREATE TABLE votation_location (
   id INT GENERATED ALWAYS AS IDENTITY,
   extid VARCHAR(255),
   level VARCHAR(15) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE VOTATION_LOCATION_DETAILS (
   fk_votation_location INT NOT NULL,
   name VARCHAR(255),
   lang VARCHAR(2) NOT NULL,
   constraint id unique (fk_votation_location, lang),
   CONSTRAINT fk_votation_location
       FOREIGN KEY(fk_votation_location)
           REFERENCES votation_location(id)
);

CREATE TABLE votation_location (
   id INT GENERATED ALWAYS AS IDENTITY,
   level varchar(15) not null,
   PRIMARY KEY (id)
);

CREATE TABLE VOTATION_LOCATION_DETAILS (
   fk_votation_location INT NOT NULL,
   name varchar(255),
   lang varchar(2) not null,
   constraint id unique (fk_votation_location, lang),
   CONSTRAINT fk_votation_location
       FOREIGN KEY(fk_votation_location)
           REFERENCES votation_location(id)
);

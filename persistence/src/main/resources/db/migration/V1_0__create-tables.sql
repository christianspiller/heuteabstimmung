CREATE TABLE votation_location (
   id INT GENERATED ALWAYS AS IDENTITY,
   extid VARCHAR(255) NOT NULL,
   name VARCHAR(100) NOT NULL,
   short_name VARCHAR(100) NOT NULL,
   level VARCHAR(15) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE data_selector (
   id INT GENERATED ALWAYS AS IDENTITY,
   name VARCHAR(255) NOT NULL,
   extid VARCHAR(255) NOT NULL,
   type VARCHAR(10) NOT NULL,
   level VARCHAR(15) NOT NULL,
   hash VARCHAR(10) NOT NULL,
   source VARCHAR(10) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE data_index (
    id INT GENERATED ALWAYS AS IDENTITY,
    key VARCHAR(30) NOT NULL,
    fk_data_selector_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_data_selector FOREIGN KEY (fk_data_selector_id) REFERENCES data_selector(id)
)
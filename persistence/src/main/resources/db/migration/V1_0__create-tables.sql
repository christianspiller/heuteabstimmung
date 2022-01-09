
CREATE TABLE data_selector (
   id INT GENERATED ALWAYS AS IDENTITY,
   name VARCHAR(255) NOT NULL,
   ext_id VARCHAR(255) NOT NULL,
   data_type VARCHAR(10) NOT NULL,
--   division_level VARCHAR(15) NOT NULL,
   hash VARCHAR(11) NOT NULL,
   source VARCHAR(10) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (ext_id, source)
);


CREATE TABLE votation_location_data_selector (
                               id INT GENERATED ALWAYS AS IDENTITY,
                               data_selector_entity_id INT,
                               division_level VARCHAR(15) NOT NULL,
                               PRIMARY KEY (id),
                               CONSTRAINT fk_data_selector FOREIGN KEY (data_selector_entity_id) REFERENCES data_selector(id)
);

CREATE TABLE data_index (
    id INT GENERATED ALWAYS AS IDENTITY,
    key VARCHAR(30) NOT NULL,
    data_selector_entity_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_data_selector FOREIGN KEY (data_selector_entity_id) REFERENCES data_selector(id),
    UNIQUE (key, data_selector_entity_id)
);

CREATE TABLE votation_location (
                                   id INT GENERATED ALWAYS AS IDENTITY,
                                   data_selector_entity_id INT,
                                   short_name VARCHAR(100) NOT NULL,
                                   PRIMARY KEY (id),
                                   CONSTRAINT fk_data_selector FOREIGN KEY (data_selector_entity_id) REFERENCES data_selector(id)
);


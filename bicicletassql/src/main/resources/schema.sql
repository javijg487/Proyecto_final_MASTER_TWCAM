CREATE TABLE IF NOT EXISTS aparcamiento (
    id  VARCHAR(5) PRIMARY KEY NOT NULL,
    direction VARCHAR(255),
    bikescapacity INT NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL
);

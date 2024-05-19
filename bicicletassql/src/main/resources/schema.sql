CREATE TABLE IF NOT EXISTS aparcamiento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    direction VARCHAR(255),
    bikes_capacity INT NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL
);

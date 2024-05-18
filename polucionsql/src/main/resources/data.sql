CREATE TABLE IF NOT EXISTS estacion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    direction VARCHAR(255),
    latitude FLOAT,
    longitude FLOAT
);


INSERT INTO estacion (direction, latitude, longitude) VALUES
('Estación A', 40.4168, -3.7038),
('Estación B', 41.3851, 2.1734),
('Estación C', 48.8566, 2.3522);
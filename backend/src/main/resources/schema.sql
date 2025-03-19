CREATE TABLE Customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Consultant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    rate_per_hour DOUBLE NOT NULL,
    hours_per_day INT NOT NULL
);

CREATE TABLE Assignment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    consultant_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (consultant_id) REFERENCES Consultant(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(id) ON DELETE CASCADE
);

CREATE TABLE Forecast_Day (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    assignment_id BIGINT NOT NULL,
    date DATE NOT NULL,
    hours_worked INT NOT NULL,
    FOREIGN KEY (assignment_id) REFERENCES Assignment(id) ON DELETE CASCADE
);


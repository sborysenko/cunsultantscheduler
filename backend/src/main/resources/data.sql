SET REFERENTIAL_INTEGRITY FALSE;

-- Insert data into Consultant table
INSERT INTO Consultant (id, name, rate_per_hour, hours_per_day) VALUES
(1, 'Alice Johnson', 50.00, 8),
(2, 'Bob Smith', 60.00, 8),
(3, 'Scott Frederick', 40.00, 8);

-- Insert data into Customer table
INSERT INTO Customer (id, name) VALUES
(1, 'Company A'),
(2, 'Company B'),
(3, 'Company C');

-- Insert data into Assignment table
INSERT INTO Assignment (id, name, consultant_id, customer_id) VALUES
(1, 'Assignment 1', 1, 1),
(2, 'Assignment 2', 1, 2),
(3, 'Assignment 3', 2, 1),
(4, 'Assignment 4', 2, 2),
(5, 'Assignment 5', 3, 1);

-- Insert data into ForecastDay table
INSERT INTO Forecast_Day (assignment_id, date, hours_worked) VALUES
(1, '2025-03-01', 8), (1, '2025-03-02', 8), (1, '2025-03-03', 8),
(2, '2025-03-05', 8), (2, '2025-03-06', 8), (2, '2025-03-07', 5),
(3, '2025-03-01', 8), (3, '2025-03-02', 8), (3, '2025-03-03', 8),
(4, '2025-03-05', 8), (4, '2025-03-06', 8), (4, '2025-03-07', 5),
(5, '2025-03-01', 8), (5, '2025-03-02', 8), (5, '2025-03-03', 8), (5, '2025-03-04', 8),
(5, '2025-03-05', 8), (5, '2025-03-06', 8), (5, '2025-03-07', 8), (5, '2025-03-10', 8),
(5, '2025-03-11', 8), (5, '2025-03-12', 8), (5, '2025-03-13', 8), (5, '2025-03-14', 8);

SET REFERENTIAL_INTEGRITY TRUE;

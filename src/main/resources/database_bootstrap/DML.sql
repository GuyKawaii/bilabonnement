# DML is Data Manipulation Language which is used to manipulate data itself.

# stored procedures maybe necessary to remember how to do

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (101, '1', 'ADMINISTRATION user', '123', 'DATA_REGISTRATION');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (102, '2', 'ADMINISTRATION user', '123', 'DAMAGE_REPORTER');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (103, '3', 'ADMINISTRATION user', '123', 'BUSINESS_DEVELOPER');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (104, '4', 'ADMINISTRATION user', '123', 'ADMINISTRATION');


TRUNCATE TABLE employee;

# testing
SELECT * FROM employee WHERE email = 1;


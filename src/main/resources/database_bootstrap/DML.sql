# DML is Data Manipulation Language which is used to manipulate data itself.

# stored procedures maybe necessary to remember how to do

### employee ###
INSERT INTO employee (employeeID, email, name, password, role)
VALUES (101, '1', 'DATA_REGISTRATION user', '123', 'DATA_REGISTRATION');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (102, '2', 'DAMAGE_REPORTER user', '123', 'DAMAGE_REPORTER');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (103, '3', 'BUSINESS_DEVELOPER user', '123', 'BUSINESS_DEVELOPER');

INSERT INTO employee (employeeID, email, name, password, role)
VALUES (104, '4', 'ADMINISTRATION user', '123', 'ADMINISTRATION');

### fleet ###
INSERT INTO fleet (vehicleID, chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven,
                   state)
VALUES (1001, '1', 'color', 'brand', 'model', 996, 'geartype', 20, 'ELECTRIC',
        9001, 'DAMAGED');
INSERT INTO fleet (vehicleID, chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven,
                   state)
VALUES (1002, '2', 'color', 'brand', 'model', 996, 'geartype', 20, 'DIESEL',
        9001, 'UNDER_REPAIR');
INSERT INTO fleet (vehicleID, chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven,
                   state)
VALUES (1003, '3', 'color', 'brand', 'model', 996, 'geartype', 20, 'GASOLINE',
        9001, 'IS_LEASED');
INSERT INTO fleet (vehicleID, chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven,
                   state)
VALUES (1004, '4', 'color', 'brand', 'model', 996, 'geartype', 20, 'HYDROGEN',
        9001, 'READY');

### customer ###
INSERT INTO customer (customerID, firstName, lastName, email)
VALUES (1001, 'firstName', 'lastName', 'email 1');
INSERT INTO customer (customerID, firstName, lastName, email)
VALUES (1002, 'firstName', 'lastName', 'email 2');
INSERT INTO customer (customerID, firstName, lastName, email)
VALUES (1003, 'firstName', 'lastName', 'email 3');
INSERT INTO customer (customerID, firstName, lastName, email)
VALUES (1004, 'firstName', 'lastName', 'email 4');

### leaseContract ###
INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID)
VALUES ('2011-01-14', '2011-01-28', 3000, 1001, 1001);
INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID)
VALUES ('2012-02-14', '2012-02-28', 3000, 1002, 1002);
INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID)
VALUES ('2013-03-14', '2013-03-28', 3000, 1003, 1003);
INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID)
VALUES ('2014-04-14', '2014-04-28', 3000, 1004, 1004);

### damageReport ###
INSERT INTO damagereport (damageReportID, timestamp, leaseID, vehicleID)
VALUES (1001, '1970-01-01 00:00:01', 1001, 1001);
INSERT INTO damagereport (damageReportID, timestamp, leaseID, vehicleID)
VALUES (1002, '1970-01-01 00:00:01', 1002, 1002);
INSERT INTO damagereport (damageReportID, timestamp, leaseID, vehicleID)
VALUES (1003, '1970-01-01 00:00:01', 1003, 1003);
INSERT INTO damagereport (damageReportID, timestamp, leaseID, vehicleID)
VALUES (1004, '1970-01-01 00:00:01', 1004, 1004);

### truncate tables
TRUNCATE TABLE employee;
TRUNCATE TABLE fleet;
TRUNCATE TABLE customer;


### testting ###
    SELECT *
FROM employee
WHERE email = 1;


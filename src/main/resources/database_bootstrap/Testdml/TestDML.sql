# DML is Data Manipulation Language which is used to manipulate data itself.

# stored procedures maybe necessary to remember how to do

# employee #
INSERT INTO employee (employeeID, email, name, password, role)
VALUES (101, '1', 'DATA_REGISTRATION user', '123', 'DATA_REGISTRATION');
INSERT INTO employee (employeeID, email, name, password, role)
VALUES (102, '2', 'DAMAGE_REPORTER user', '123', 'DAMAGE_REPORTER');
INSERT INTO employee (employeeID, email, name, password, role)
VALUES (103, '3', 'BUSINESS_DEVELOPER user', '123', 'BUSINESS_DEVELOPER');
INSERT INTO employee (employeeID, email, name, password, role)
VALUES (104, '4', 'ADMINISTRATION user', '123', 'ADMINISTRATION');

# customer #
INSERT INTO customer (customerID, firstName, lastName, email, address, city, postalCode, mobile, cprNumber)
VALUES (201, 'firstName', 'lastName', 'email_1', 'address', 'city', 2900, 'mobile', 'cprNumber');
INSERT INTO customer (customerID, firstName, lastName, email, address, city, postalCode, mobile, cprNumber)
VALUES (202, 'firstName', 'lastName', 'email_2', 'address', 'city', 2900, 'mobile', 'cprNumber');
INSERT INTO customer (customerID, firstName, lastName, email, address, city, postalCode, mobile, cprNumber)
VALUES (203, 'firstName', 'lastName', 'email_3', 'address', 'city', 2900, 'mobile', 'cprNumber');

# optional #
INSERT INTO optional (optionalID, name, pricePrMonth)
VALUES (401, 'optional 1', 10);
INSERT INTO optional (optionalID, name, pricePrMonth)
VALUES (402, 'optional 2', 20);
INSERT INTO optional (optionalID, name, pricePrMonth)
VALUES (403, 'optional 3', 30);


### tables with foreign key constraints ###

# car #
INSERT INTO car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                 kmDriven, locationID, state)
VALUES (501, '1', 10000, 'color', 'brand', 'model', 996, 'geartype', 20, 'ELECTRIC',
        9001, 301, 'DAMAGED');
INSERT INTO car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                 kmDriven, locationID, state)
VALUES (502, '2', 20000, 'color', 'brand', 'model', 996, 'geartype', 20, 'DIESEL',
        9001, 301, 'UNDER_REPAIR');
INSERT INTO car(vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                kmDriven, locationID, state)
VALUES (503, '3', 30000, 'color', 'brand', 'model', 996, 'geartype', 20, 'GASOLINE',
        9001, 302, 'IS_LEASED');
INSERT INTO car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                 kmDriven, locationID, state)
VALUES (504, '4', 40000, 'color', 'brand', 'model', 996, 'geartype', 20, 'HYDROGEN',
        9001, 302, 'READY');
INSERT INTO car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                 kmDriven, locationID, state)
VALUES (505, '4', 40000, 'color', 'brand', 'model', 996, 'geartype', 20, 'HYDROGEN',
        9001, 302, 'IS_LEASED');
INSERT INTO car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType,
                 kmDriven, locationID, state)
VALUES (506, '4', 40000, 'color', 'brand', 'model', 996, 'geartype', 20, 'HYDROGEN',
        9001, 302, 'IS_LEASED');

# leaseOptional #
INSERT INTO leaseoptional (optionalID, vehicleID)
VALUES (401, 501);
INSERT INTO leaseoptional (optionalID, vehicleID)
VALUES (402, 501);
INSERT INTO leaseoptional (optionalID, vehicleID)
VALUES (403, 501);

# leaseContract #
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (601, '2011-01-14', '2011-01-28', 3000, 201, 501, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (602, '2012-02-14', '2012-02-28', 3000, 201, 502, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (603, '2013-03-14', '2013-03-28', 3000, 202, 503, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (604, '2014-01-01', '2014-01-31', 3000, 203, 504, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (606, '2022-06-21', '2023-02-21', 3000, 203, 505, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (607, '2022-09-01', '2023-05-01', 3000, 203, 506, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (608, '2016-03-01', '2016-05-31', 3000, 203, 506, 101);

# damageReport #
INSERT INTO damagereport (damagereport.damageReportID, damagereport.vehicleID, damagereport.employeeID, damagereport.timestamp)
VALUES (701, 501, 101, '1970-01-01 00:00:01');
INSERT INTO damagereport (damagereport.damageReportID, damagereport.vehicleID, damagereport.employeeID, damagereport.timestamp)
VALUES (702, 501, 101, '1970-01-01 00:00:01');
INSERT INTO damagereport (damagereport.damageReportID, damagereport.vehicleID, damagereport.employeeID, damagereport.timestamp)
VALUES (703, 502, 101, '1970-01-01 00:00:01');

# damageEntry #
INSERT INTO damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID)
VALUES (801, 'damageTitle', 'damageDescription', 500, 701);
INSERT INTO damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID)
VALUES (802, 'damageTitle', 'damageDescription', 500, 702);
INSERT INTO damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID)
VALUES (803, 'damageTitle', 'damageDescription', 500, 703);
INSERT INTO damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID)
VALUES (804, 'damageTitle', 'damageDescription', 500, 703);




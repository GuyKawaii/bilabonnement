### truncate tables
TRUNCATE TABLE employee;
TRUNCATE TABLE car;
TRUNCATE TABLE customer;
TRUNCATE TABLE leaseContract;

### testting ###
SELECT *
FROM employee
WHERE email = 1;



SELECT leasecontract.monthlyPrice, car.vehicleID, car.state
FROM leaseContract
         JOIN car ON leasecontract.vehicleID = car.vehicleID
WHERE state = 'IS_LEASED';


# leased cars
SELECT car.*
FROM car
         JOIN leasecontract l on car.vehicleID = l.vehicleID
WHERE startDate <= '2022-11-11'
  AND '2022-11-11' <= endDate;

# unleased cars todo we can select different period in menu to show what cars where leased
SELECT unleased.*
FROM car unleased
WHERE unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                 FROM car leased
                                          join leasecontract l on leased.vehicleID = l.vehicleID
                                 WHERE startDate <= '2022-11-11'
                                   AND '2022-11-11' <= endDate);

# optional for leasing contract
SELECT o.*
FROM leaseoptional
    join optional o on o.optionalID = leaseoptional.optionalID
WHERE vehicleID = 503;

SELECT MAX(startDate) FROM leasecontract;


# leased car current leasing price
SELECT car.vehicleID,car.state, leasecontract.monthlyPrice
FROM leaseContract
join car
WHERE car.state = 'IS_LEASED'
ORDER BY vehicleID;

SELECT * FROM leaseContract;
SELECT * FROM car;
SELECT * FROM bilabonnement.car;

SELECT car.vehicleID,car.state, leasecontract.monthlyPrice , leasecontract.startdate
FROM leaseContract
JOIN car ON leasecontract.vehicleID = car.vehicleID
WHERE state = 'IS_LEASED' AND endDate < ?
ORDER BY startDate;

SELECT leasecontract.monthlyPrice
FROM leaseContract
WHERE startDate < ? AND endDate > ?;

# aliasing mellem state IS_LEASED og leaseContract der har en given periode mellem startDate og endDate. tænker man ud fra dem ville kunne deeducerer hvilke der er leased maybe?
# Alså hive alle kontrakter ud og sorter efter startdato for nuværende måned i service?

TRUNCATE leasecontract;

INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (601, '2011-01-14', '2011-01-28', 3000, 201, 501, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (602, '2012-02-14', '2012-02-28', 3000, 201, 502, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (603, '2013-03-14', '2013-03-28', 3000, 202, 503, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (604, '2014-01-01', '2014-01-31', 3000, 203, 504, 101);
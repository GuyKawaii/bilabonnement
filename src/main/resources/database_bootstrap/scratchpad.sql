### truncate tables
TRUNCATE TABLE employee;
TRUNCATE TABLE car;
TRUNCATE TABLE customer;


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

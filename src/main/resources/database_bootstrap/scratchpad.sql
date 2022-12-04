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



SELECT MAX(startDate) FROM leasecontract;
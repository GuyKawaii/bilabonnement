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
WHERE state = 'IS_LEASED'
ORDER BY startDate;

# aliasing mellem state IS_LEASED og leaseContract der har en given periode mellem startDate og endDate. tænker man ud fra dem ville kunne deeducerer hvilke der er leased maybe?
# Alså hive alle kontrakter ud og sorter efter startdato for nuværende måned i service?

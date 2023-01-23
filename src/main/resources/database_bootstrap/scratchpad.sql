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
WHERE (unleased.state = 'RETURNED')
  AND unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                 FROM car leased
                                          join leasecontract l on leased.vehicleID = l.vehicleID
                                 WHERE startDate <= '2022-11-11'
                                   AND '2022-11-11' <= endDate);

# optional for leasing contract
SELECT o.*
FROM leaseoptional
         join optional o on o.optionalID = leaseoptional.optionalID
WHERE vehicleID = 503;

SELECT MAX(startDate)
FROM leasecontract;


# leased car current leasing price
SELECT car.vehicleID, car.state, leasecontract.monthlyPrice
FROM leaseContract
         join car
WHERE car.state = 'IS_LEASED'
ORDER BY vehicleID;

SELECT *
FROM leaseContract;
SELECT *
FROM car;
SELECT *
FROM bilabonnement.car;

SELECT car.vehicleID, car.state, leasecontract.monthlyPrice, leasecontract.startdate
FROM leaseContract
         JOIN car ON leasecontract.vehicleID = car.vehicleID
WHERE state = 'IS_LEASED'
  AND endDate < ?
ORDER BY startDate;

SELECT SUM()
FROM leasecontract,
     leaseoptional
WHERE (startDate < '07/12/2022' AND endDate > '07/12/2022')
  AND leaseoptional.leaseID = leasecontract.leaseID
group by leasecontract.leaseID;

select leasecontract.leaseID, leaseOptional.optionalID
from leasecontract,
     leaseoptional
where leasecontract.leaseID = leaseoptional.leaseID
group by leasecontract.leaseID;


# Returns sum of optionals pr leasecontract that has optionals
SELECT leasecontract.leaseID, SUM(optional.pricePrMonth) as Total_income
FROM leasecontract,
     optional,
     leaseoptional
WHERE leasecontract.leaseID = leaseoptional.leaseID
  AND leaseoptional.optionalID = optional.optionalID
GROUP BY leasecontract.leaseID;

# Returns monthlyincome + Sum (as we want)... but only for cars that have optionals.
# Do we join this table?
SELECT leasecontract.monthlyPrice + SUM(optional.pricePrMonth) as Total_income
FROM leasecontract,
     optional,
     leaseoptional
WHERE leasecontract.leaseID = leaseoptional.leaseID
  AND leaseoptional.optionalID = optional.optionalID
GROUP BY leasecontract.leaseID;

SELECT SUM(currentIncome)
FROM (SELECT monthlyPrice + IFNULL(SUM(pricePrMonth), 0) AS currentIncome
      FROM fullLeaseInfo
      WHERE (startDate <= ?)
        AND (? <= endDate)
      GROUP BY leaseID) as currentIncome;


SELECT leasecontract.*, optional.*
FROM leasecontract
         LEFT JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         LEFT JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;

use bilabonnement;

create view fullLeaseInfo AS
SELECT leasecontract.*, optional.*
FROM leasecontract
         LEFT JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         LEFT JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;

SELECT monthlyPrice, sum(pricePrMonth) as fullIncome
FROM fullLeaseInfo
group by leaseID;

SELECT SUM(monthlyPrice) + SUM(pricePrMonth) AS currentIncome
from fullLeaseInfo
where startDate <= ?
  and endDate >= ?;



SELECT SUM(monthlyPrice) + SUM(pricePrMonth) AS currentIncome
from fullLeaseInfo;

SELECT SUM(monthlyPrice)
from fullLeaseInfo;

SELECT *
FROM fullLeaseInfo;

SELECT *
FROM fullLeaseInfo
WHERE (startDate <= '2022-12-08')
  AND ('2022-12-08' <= endDate);

# Used in lease contract with ? instead of dates
SELECT SUM(monthlyPrice) + IFNULL(SUM(pricePrMonth), 0) AS currentIncome
FROM fullLeaseInfo
WHERE (startDate <= '2000-01-02')
  AND ('2000-01-02' <= endDate);

SELECT monthlyPrice + IFNULL(SUM(pricePrMonth), 0) AS currentIncome
FROM fullLeaseInfo
WHERE (startDate <= '2000-01-02')
  AND ('2000-01-02' <= endDate)
GROUP BY leaseID;



SELECT SUM(currentIncome)
FROM (SELECT monthlyPrice + IFNULL(SUM(pricePrMonth), 0) AS currentIncome
      FROM fullLeaseInfo
      WHERE (startDate <= '2000-01-02')
        AND ('2000-01-02' <= endDate)
      GROUP BY leaseID) as currentIcome;

select *
from fullLeaseInfo;

create view fullLeaseInfo AS
SELECT leasecontract.*, optional.*
FROM leasecontract
         LEFT JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         LEFT JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;

SELECT COUNT(*) AS activeContractCount
FROM fullLeaseInfo
WHERE (startDate <= ?)
  AND (? <= endDate);

SELECT leasecontract.monthlyPrice + SUM(optional.pricePrMonth) as Total_income
FROM leasecontract,
     optional,
     leaseoptional
WHERE leasecontract.leaseID = leaseoptional.leaseID
  AND leaseoptional.optionalID = optional.optionalID
GROUP BY leasecontract.leaseID;


SELECT l.leaseID, SUM(o.pricePrMonth) as Total_income
FROM leasecontract,
     optional,
     leaseoptional
         left join leasecontract l
                   on leaseoptional.leaseID = l.leaseID
         INNER JOIN optional o
                    on leaseoptional.optionalID = o.optionalID
GROUP BY l.leaseID;

SELECT
    AND
    (leasecontract.startDate <= ? AND leasecontract.endDate >= ?)

SELECT leasecontract.monthlyPrice + SUM(optional.pricePrMonth) as Total_income
FROM leasecontract,
     optional,
     leaseoptional
WHERE leasecontract.leaseID = leaseoptional.leaseID
  AND leaseoptional.optionalID = optional.optionalID
GROUP BY leasecontract.leaseID;

SELECT leasecontract.monthlyPrice + SUM(optional.pricePrMonth) as Total_Current_income
FROM leasecontract,
     optional,
     leaseoptional
WHERE leasecontract.leaseID = leaseoptional.leaseID
  AND leaseoptional.optionalID = optional.optionalID
  AND (startDate < ? AND endDate > ?)
GROUP BY leasecontract.leaseID;

SELECT leasecontract.leaseID, optional.pricePrmonth
FROM leaseoptional
         JOIN lease
# base price
SELECT leasecontract.monthlyPrice
FROM leaseContract
WHERE startDate < ?
  AND ? < endDate;

# with optionals
SELECT l.monthlyPrice + SUM(o.pricePrMonth) as total
FROM leaseContract l
         LEFT JOIN leaseoptional lo on lo.leaseID = l.leaseID
         JOIN optional o on lo.optionalID = o.optionalID
WHERE l.startDate < '2011-1-15'
  AND '2011-1-15' < l.endDate
GROUP BY l.monthlyPrice;

SELECT leasecontract.monthlyPrice, leaseID
FROM leaseContract
WHERE startDate < '2011-01-15'
  AND '2011-01-15' < endDate;


# aliasing mellem state IS_LEASED og leaseContract der har en given periode mellem startDate og endDate. tænker man ud fra dem ville kunne deeducerer hvilke der er leased maybe?
# Alså hive alle kontrakter ud og sorter efter startdato for nuværende måned i service?

TRUNCATE leasecontract;

INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (601, '2011-01-14', '2011-01-28', 3000, 201, 501, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (602, '2012-02-14', '2012-02-28', 3000, 201, 502, 101);
INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES (603, '2013-03-14', '2013-03-28', 3000, 202, 503, 101);

INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID)
VALUES ('2014-01-01', '2014-01-31', 3000, 203, 504, 101);

SELECT MAX(leaseID)
FROM leasecontract;



INSERT INTO leaseoptional (optionalID, leaseID)
VALUES (?, ?);


SELECT `AUTO_INCREMENT`
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'DatabaseName'
  AND TABLE_NAME = 'leaseoptional'

SELECT unleased.*
FROM car unleased
WHERE unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                 FROM car leased
                                          join leasecontract l on leased.vehicleID = l.vehicleID
                                 WHERE startDate <= NOW()
                                   AND NOW() <= endDate);

SELECT c.*
FROM car c
         JOIN leasecontract l on c.vehicleID = l.vehicleID
WHERE startDate <= NOW()
  AND NOW() <= endDate
GROUP BY c.vehicleID
ORDER BY c.vehicleID;

INSERT INTO leaseoptional (optionalID, leaseID)
VALUES (?, ?);


SELECT l.*
FROM leasecontract l
         JOIN car c on c.vehicleID = l.vehicleID
WHERE l.vehicleID = ?
  AND NOW() < endDate
ORDER BY endDate;

SELECT o.*
FROM optional o
         JOIN leaseoptional l on o.optionalID = l.optionalID
WHERE l.leaseID = 602;

SELECT remaining.*
FROM optional remaining
WHERE remaining.optionalID NOT IN (SELECT leaseOptional.optionalID
                                   FROM optional leaseOptional
                                            JOIN leaseoptional l on leaseOptional.optionalID = l.optionalID
                                   WHERE l.leaseID = 602);


SELECT SUM(o.pricePrMonth) as sum, COUNT(lo.leaseID) as count
FROM leasecontract l
         LEFT JOIN leaseoptional lo on l.leaseID = lo.leaseID
         LEFT JOIN optional o on o.optionalID = lo.optionalID
GROUP BY l.leaseID
ORDER BY l.leaseID ASC;


SELECT l.*
FROM leasecontract l
         JOIN car c on c.vehicleID = l.vehicleID
WHERE l.vehicleID = ?
  AND (
    (? < l.endDate
        OR l.startDAte < ?))

ORDER BY startDate DESC;


SELECT leasecontract.*, optional.*
FROM leasecontract
         inner JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         inner JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;

SELECT leasecontract.*, optional.*
FROM leasecontract
         LEFT JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         LEFT JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;

SELECT l.*
FROM leasecontract l
         JOIN car c on c.vehicleID = l.vehicleID
WHERE l.vehicleID = ?
  AND (
    (? < l.endDate
        OR l.startDAte < ?));

USE bilabonnement;

SELECT unleased.*
FROM car unleased
WHERE unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                 FROM car leased
                                          join leasecontract l on leased.vehicleID = l.vehicleID
                                 WHERE startDate <= ?
                                   AND              ? <= endDate);

SELECT * FROM bilabonnement.fullleaseinfo;

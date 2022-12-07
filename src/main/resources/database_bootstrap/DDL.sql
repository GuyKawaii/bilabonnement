# DDL is Data Definition Language which is used to define data structures.
# nuke option
DROP DATABASE IF EXISTS bilabonnement;

CREATE DATABASE bilabonnement;

USE bilabonnement;

####  reset tables ###

###  create tables ###
CREATE TABLE IF NOT EXISTS employee
(
    employeeID int AUTO_INCREMENT,
    email      varchar(255) UNIQUE NOT NULL,
    name       varchar(255)        NOT NULL,
    password   varchar(255)        NOT NULL,
    role       ENUM ('ADMINISTRATION', 'DAMAGE_REPORTER', 'DATA_REGISTRATION', 'BUSINESS_DEVELOPER'),

    # key setup
    primary key (employeeID)
);

CREATE TABLE IF NOT EXISTS customer
(
    customerID int AUTO_INCREMENT,
    firstName  varchar(50)        NOT NULL,
    lastName   varchar(50)        NULL,
    email      varchar(50) UNIQUE NOT NULL,
    address    varchar(100)        NOT NULL,
    city       varchar(50)        NOT NULL,
    postalCode int                 NOT NULL,
    mobile     varchar(50)        NOT NULL,
    cprNumber  varchar(50)        NOT NULL,

    # key setup
    primary key (customerID)
);

CREATE TABLE IF NOT EXISTS location
(
    locationID int AUTO_INCREMENT,
    name       varchar(50),

    # key setup
    PRIMARY KEY (locationID)
);

CREATE TABLE IF NOT EXISTS optional
(
    optionalID   int AUTO_INCREMENT,
    name         varchar(50),
    pricePrMonth double,

    # key setup
    PRIMARY KEY (optionalID)
);

CREATE TABLE IF NOT EXISTS car
(
    vehicleID       int AUTO_INCREMENT,
    chassisNumber   varchar(17),
    steelPrice      double,
    brand           varchar(30),
    model           varchar(30),
    equipmentLevel  ENUM ('BASE', 'MEDIUM', 'LARGE'),
    registrationFee double,
    co2emission     double,
    locationID      int,
    state           ENUM ('READY', 'IS_LEASED', 'UNDER_REPAIR', 'DAMAGED'),

    # key setup
    PRIMARY KEY (vehicleID),
    FOREIGN KEY (locationID) REFERENCES location (locationID)
);


# tables with foreign key constraints #

CREATE TABLE IF NOT EXISTS leaseContract
(
    leaseID      int AUTO_INCREMENT,
    startDate    date   NOT NULL,
    endDate      date   NOT NULL,
    monthlyPrice double NOT NULL,
    customerID   int    NOT NULL,
    vehicleID    int    NOT NULL,
    employeeID   int    NOT NULL,


    # key setup
    PRIMARY KEY (leaseID),
    FOREIGN KEY (customerID)
        REFERENCES customer (customerID)
        ON DELETE CASCADE,
    FOREIGN KEY (vehicleID)
        REFERENCES car (vehicleID)
        ON DELETE CASCADE,
    FOREIGN KEY (employeeID)
        REFERENCES employee (employeeID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leaseOptional
(
    optionalID int,
    leaseID  int,

    # key setup
    PRIMARY KEY (optionalID, leaseID),
    CONSTRAINT optionalID FOREIGN KEY (optionalID) REFERENCES optional (optionalID),
    CONSTRAINT leaseID FOREIGN KEY (leaseID) REFERENCES leasecontract (leaseID)
);

CREATE TABLE IF NOT EXISTS damageReport
(
    damageReportID INT AUTO_INCREMENT,
    vehicleID      int       NOT NULL,
    employeeID     int       NOT NULL,
    timestamp      timestamp NOT NULL,

    # key setup
    PRIMARY KEY (damageReportID),
    FOREIGN KEY (vehicleID)
        REFERENCES car (vehicleID)
        ON DELETE CASCADE,
    FOREIGN KEY (employeeID)
        REFERENCES employee (employeeID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS damageEntry
(
    damageEntryID     int AUTO_INCREMENT,
    damageTitle       varchar(255) NOT NULL,
    damageDescription varchar(255) NOT NULL,
    damagePrice       double       not null,
    damageReportID    int,

    # key setup
    PRIMARY KEY (damageEntryID),
    FOREIGN KEY (damageReportID)
        REFERENCES damageReport (damageReportID)
        ON DELETE CASCADE
);

### reserve unitTest ID's ###
ALTER TABLE employee
    AUTO_INCREMENT = 1001;
ALTER TABLE customer
    AUTO_INCREMENT = 1001;
ALTER TABLE location
    AUTO_INCREMENT = 1001;
ALTER TABLE optional
    AUTO_INCREMENT = 1001;
ALTER TABLE car
    AUTO_INCREMENT = 1001;
ALTER TABLE damageEntry
    AUTO_INCREMENT = 1001;
ALTER TABLE damageReport
    AUTO_INCREMENT = 1001;
ALTER TABLE leaseContract
    AUTO_INCREMENT = 1001;





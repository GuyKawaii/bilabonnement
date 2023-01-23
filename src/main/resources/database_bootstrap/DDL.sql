# DDL is Data Definition Language which is used to define data structures.

/**
* @author daniel(GuyKawaii)
* @author Ian(DatJustino)
*/

# # test
# DROP DATABASE  IF EXISTS testbilabonnement;
# CREATE DATABASE testbilabonnement;
# USE testbilabonnement;

# release
DROP DATABASE IF EXISTS bilabonnement;
CREATE DATABASE bilabonnement;
USE bilabonnement;

####  reset tables ###

###  create tables ###
CREATE TABLE IF NOT EXISTS employee
(
    employeeID int AUTO_INCREMENT,
    email      varchar(100) UNIQUE NOT NULL,
    name       varchar(100)        NOT NULL,
    password   varchar(100)        NOT NULL,
    role       ENUM ('DAMAGE_REPORTER', 'DATA_REGISTRATION', 'BUSINESS_DEVELOPER'),

    # key setup
    primary key (employeeID)
);

CREATE TABLE IF NOT EXISTS customer
(
    customerID int AUTO_INCREMENT,
    firstName  varchar(100)        NOT NULL,
    lastName   varchar(100)        NULL,
    email      varchar(100) UNIQUE NOT NULL,
    address    varchar(100)       NOT NULL,
    city       varchar(50)        NOT NULL,
    postalCode int                NOT NULL,
    mobile     varchar(50)        NOT NULL,
    cprNumber  varchar(12)        NOT NULL,

    # key setup
    primary key (customerID)
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
    brand           varchar(50),
    model           varchar(100),
    equipmentLevel  ENUM ('BASE', 'MEDIUM', 'LARGE'),
    registrationFee double,
    co2emission     double,
    state           ENUM ('READY', 'RETURNED', 'AT_CUSTOMER'),

    # key setup
    PRIMARY KEY (vehicleID)
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
        ON DELETE CASCADE,
    # extra constraints
    CHECK ( startDate <= endDate )

);

CREATE TABLE IF NOT EXISTS leaseOptional
(
    optionalID int,
    leaseID    int,

    # key setup
    PRIMARY KEY (optionalID, leaseID),
    CONSTRAINT optionalID FOREIGN KEY (optionalID)
        REFERENCES optional (optionalID) ON DELETE CASCADE,
    CONSTRAINT leaseID FOREIGN KEY (leaseID)
        REFERENCES leasecontract (leaseID) ON DELETE CASCADE
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
    damageTitle       varchar(50) NOT NULL,
    damageDescription varchar(255) NOT NULL,
    damagePrice       double       not null,
    damageReportID    int,

    # key setup
    PRIMARY KEY (damageEntryID),
    FOREIGN KEY (damageReportID)
        REFERENCES damageReport (damageReportID)
        ON DELETE CASCADE
);

### make View  ###
/**
 * @author Mikas(CodeClod)
 */

create view fullLeaseInfo AS
SELECT leasecontract.*, optional.*
FROM leasecontract
         LEFT JOIN leaseoptional
                   ON leasecontract.leaseID = leaseoptional.leaseID
         LEFT JOIN optional
                   ON leaseoptional.optionalID = optional.optionalID;




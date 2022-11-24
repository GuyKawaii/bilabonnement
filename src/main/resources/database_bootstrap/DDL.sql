# DDL is Data Definition Language which is used to define data structures.

USE bilabonnement;

####  reset tables ###

###  create tables ###
CREATE TABLE IF NOT EXISTS employee
(
    employeeID int AUTO_INCREMENT,
    email      varchar(255) UNIQUE NOT NULL,
    name       varchar(255),
    password   varchar(255)        NOT NULL,
    role       ENUM ('ADMINISTRATION', 'DAMAGE_REPORTER', 'DATA_REGISTRATION', 'BUSINESS_DEVELOPER'),
    primary key (employeeID)
);

CREATE TABLE IF NOT EXISTS customer
(
    customerID int AUTO_INCREMENT,
    firstName  varchar(255)        NULL,
    lastName   varchar(255)        NULL,
    email      varchar(255) UNIQUE NOT NULL,
    primary key (customerID)
);

CREATE TABLE IF NOT EXISTS fleet
(
    vehicleID     int AUTO_INCREMENT,
    chassisNumber varchar(17),
    color         varchar(30),
    brand         varchar(30),
    model         varchar(30),
    co2emission   int,
    geartype      varchar(20),
    kmPerLiter    int,
    fuelType      ENUM ('HYDROGEN', 'GASOLINE', 'DIESEL', 'ELECTRIC'),
    kmDriven      int,
    state         ENUM ('READY', 'IS_LEASED', 'UNDER_REPAIR', 'DAMAGED'),

    # constraints
    PRIMARY KEY (vehicleID)
);

CREATE TABLE IF NOT EXISTS leaseContract
(
    leaseID      int AUTO_INCREMENT,
    startDate    date NOT NULL,
    endDate      date NOT NULL,
    monthlyPrice int  NOT NULL,
    customerID   int  NOT NULL,
    vehicleID    int,

    # constraints
    PRIMARY KEY (leaseID),
    FOREIGN KEY (customerID)
        REFERENCES customer (customerID)
        ON DELETE CASCADE,
    FOREIGN KEY (vehicleID)
        REFERENCES fleet (vehicleID)
        ON DELETE CASCADE
    -- pickupLocation foreign key
);

CREATE TABLE IF NOT EXISTS damageReport
(
    damageReportID int auto_increment,
    timestamp      timestamp not null,
    leaseID        int       not null,
    vehicleID      int       not null,

    # constraints
    PRIMARY KEY (damageReportID),
    FOREIGN KEY (leaseID)
        REFERENCES leaseContract (leaseID)
        ON UPDATE CASCADE,
    FOREIGN KEY (vehicleID)
        REFERENCES fleet (vehicleID)
        ON DELETE CASCADE


);

CREATE TABLE IF NOT EXISTS damageEntry
(
    damageEntryID     int AUTO_INCREMENT,
    damageTitle       varchar(255) not null,
    damageDescription varchar(255) not null,
    damagePrice       int          not null,
    damageReportID    int,

    # constraints
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
ALTER TABLE fleet
    AUTO_INCREMENT = 1001;
ALTER TABLE damageEntry
    AUTO_INCREMENT = 1001;
ALTER TABLE damageReport
    AUTO_INCREMENT = 1001;
ALTER TABLE leaseContract
    AUTO_INCREMENT = 1001;
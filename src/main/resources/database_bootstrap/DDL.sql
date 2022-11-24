# DDL is Data Definition Language which is used to define data structures.

USE bilabonnement;

####  reset tables ###

###  create tables ###
CREATE TABLE IF NOT EXISTS employee
(
    employeeID int auto_increment,
    email      varchar(255) unique not null,
    password   varchar(255)        not null,
    role       ENUM ('ADMINISTRATION', 'DAMAGE_REPORTER', 'DATA_REGISTRATION', 'BUSINESS_DEVELOPERS'),
    primary key (employeeID)
);

CREATE TABLE IF NOT EXISTS fleet
(
    vehicleNumber int AUTO_INCREMENT,
    steelNumber   varchar(17),
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
    PRIMARY KEY (vehicleNumber)
);

CREATE TABLE IF NOT EXISTS damageEntry
(
    entryID           int AUTO_INCREMENT,
    damageTitle       varchar(255) not null,
    damageDescription varchar(255) not null,
    damagePrice       int not null,
    damageReportID    int,

    # constraints
    PRIMARY KEY (entryID),
    FOREIGN KEY (damageReportID) REFERENCES damageEntry (damageReportID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS damageReport
(
    entryID           int auto_increment,
    damageTitle       varchar(255) not null,
    damageDescription varchar(255) not null,
    leaseID           int,

    # constraints
    PRIMARY KEY (entryID),
    FOREIGN KEY (leaseID) REFERENCES leaseContract (leaseID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leaseContract
(
    leaseID       int auto_increment,
    startDate     date not null,
    endDate       date not null,
    monthlyPrice  int not null,
    customerID    int not null,
    vehicleNumber int,

    # constraints
    primary key (leaseID),
    FOREIGN KEY (customerID) REFERENCES customer (customerID) ON DELETE CASCADE,
    FOREIGN KEY (vehicleNumber) references fleet (vehicleNumber) ON DELETE CASCADE
    -- pickupLocation foreign key

);

### reserve unitTest ID's ###
ALTER TABLE employee AUTO_INCREMENT=1001;
ALTER TABLE customer AUTO_INCREMENT=1001;
ALTER TABLE fleet AUTO_INCREMENT=1001;
ALTER TABLE damageEntry AUTO_INCREMENT=1001;
ALTER TABLE damageReport AUTO_INCREMENT=1001;
ALTER TABLE leaseContract AUTO_INCREMENT=1001;
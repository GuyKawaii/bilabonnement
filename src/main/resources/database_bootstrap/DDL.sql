# DDL is Data Definition Language which is used to define data structures.

USE bilabonnement;

#  reset tables


#  create tables
CREATE TABLE employee (
                          user_id int auto_increment,
                          email varchar(255) unique not null,
                          password varchar(255) not null,
                          primary key (user_id)


    # constraints
);

CREATE TABLE fleet (
    vehicleNumber int,
    steelNumber varchar(17)



    # constraints

);

# increment







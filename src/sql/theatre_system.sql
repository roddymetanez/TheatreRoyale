DROP DATABASE IF EXISTS theatre;
CREATE DATABASE theatre;
USE theatre;

DROP TABLE IF EXISTS Customers;
CREATE TABLE Customers (
    customerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(25),
    middle_name VARCHAR(25),
    last_name VARCHAR(50),
    address_no VARCHAR(5),
    address_st VARCHAR(25),
    address_postcode VARCHAR(8),
    customer_email VARCHAR(50)
--     credit_card VARCHAR(16),
--     cust_history VARCHAR(100) -- Derived archive of perfID, showID, DATETIME of transaction. 
);

DROP TABLE IF EXISTS Tickets;
CREATE TABLE IF NOT EXISTS Tickets (
	showID INT,
	availability TINYINT(1),
    number_of_seats INT,  -- number of seats available
    concessionary_discount_available TINYINT(1),
    concessionary_discount INT
);

INSERT INTO Tickets  -- (performanceID, availability, number_of_seats, concessionary_discount_available, concessionary_discount)
	VALUES
		(901101, 1, 200, 1, 0.75),
        (901102, 1, 200, 0, null),
        (901102, 1, 200, 1, 0.75),
        (901103, 1, 200, 1, 0.75),
        (901104, 1, 200, 1, 0.75),
        (901105, 1, 200, 1, 0.75),
        (902101, 1, 200, 1, 0.75),
        (902101, 1, 200, 1, 0.75),
        (903101, 1, 200, 1, 0.75);

DROP TABLE IF EXISTS Show_details;
CREATE TABLE Show_details (
    showID INT,
    show_ticketPrice DOUBLE,
    show_performer VARCHAR(100),
    show_title VARCHAR(50),
    show_description VARCHAR(500),
    show_genre VARCHAR(25),
    primary_language VARCHAR(50),
    show_live_music TINYINT(1),
    show_duration INT, -- number of calendar days the show will run for
    show_runlength INT
);

INSERT INTO
	Show_details  -- (showID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)
VALUES
	(901, 8.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English", 0, 14, 110),
    (901, 8.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English", 0, 14, 110),
    (901, 10.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English", 1, 14, 120),
    (901, 8.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English, with subtitles", 0, 14, 110),
    (901, 8.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English", 1, 14, 110),
    (901, 8.99, "Appleton Players", "Aladdin", "Show description" ,"Pantomime", "English", 0, 14, 100),
    (902, 15.99, "Brian Blessed", "Buster", "Show description","Play", "BSL", 0, 7, 110),
    (902, 15.99, "Brian Blessed", "Buster", "Show description","Play", "BSL", 0, 7, 110),
    (903, 8.99, "Couer en Hiver", "Chris Corkmann & Organ", "Show description", "Cinema","French, with English Subtitles", 1, 1, 167);

DROP TABLE IF EXISTS Performance;
CREATE TABLE Performance (
    perfID INT,
    showID INT,
    perf_date DATETIME, -- format: 'YYYY-MM-DD HH:MI:SS' or 'YYYY-MM-DD'  
    seats_circle INT,
    seats_stall INT
);

INSERT INTO Performance  -- (perfID, showID, perf_date, seats_circle, seats_stall)
	VALUES
		(901, 901101, '2020-07-01 19:30:00', 80, 120),
        (901, 901102, '2020-07-01 12:30:00', 80, 120),
        (901, 901102, '2020-07-02 19:30:00', 80, 120),
        (901, 901103, '2020-07-02 12:30:00', 80, 120),
        (901, 901104, '2020-07-03 19:30:00', 80, 120),
        (901, 901105, '2020-07-04 19:30:00', 80, 120),
        (902, 902101, '2020-07-01 19:30:00', 80, 120),
        (902, 902101, '2020-07-01 19:30:00', 80, 120),
        (903, 903101, '2020-07-01 19:30:00', 80, 120);

DROP PROCEDURE IF EXISTS getShows;
DELIMITER $$
CREATE PROCEDURE
	getShows()  -- Show [name, genre, dates] of all scheduled shows
BEGIN
	SELECT
		s.show_title as `Name`, s.show_genre as `Description`, p.perf_date as `Date` 
	FROM
		Show_details s
	JOIN Performance p
		ON s.showID = p.perfID
	GROUP by S.show_title;
END;$$
DELIMITER ;

CALL getShows();

DROP PROCEDURE IF EXISTS getShowByName;
DELIMITER %
CREATE PROCEDURE
	getShowByName(in aName varchar(20))  -- Show [name, description, dates] of specified show
BEGIN
	SELECT
		s.show_title as `Name`, s.show_description as `Description`, p.perf_date as `Date`
	FROM
		Show_details s
	JOIN Performance p
		ON s.showID = p.perfID
	WHERE
		s.show_title = aName;
END;%
DELIMITER ;

CALL getShowByName('Aladdin');

DROP PROCEDURE IF EXISTS getShowByDate;
DELIMITER $$
CREATE PROCEDURE
	getShowByDate(in aDate varchar(19))  -- format 'YYYY-MM-DD hh:mm:ss'
BEGIN
	SELECT
		s.show_title as `Name`, s.show_description as `Description`, p.perf_date as `Date`
	FROM
		Show_details s
	JOIN Performance p
		ON s.showID = p.perfID
	WHERE
		p.perf_date = aDate; 
END;$$
DELIMITER ;

CALL getShowByDate('2020-07-01 19:30:00');

DROP PROCEDURE IF EXISTS checkAvailability;
DELIMITER $$
CREATE PROCEDURE
	checkAvailability(in aName varchar(20), aDate varchar(19))  -- Show [name, dates] of specified show
BEGIN
	SELECT
		s.show_title as `Name`, s.show_genre `Genre`, p.seats_circle + p.seats_stall as `Seats`
	FROM
		Show_details s
	JOIN Performance p
		ON s.showID = p.perfID
	WHERE
		s.show_title = aName AND p.perf_date = aDate; 
END;$$
DELIMITER ;

CALL checkAvailability('Aladdin', '2020-07-01 19:30:00');

DROP PROCEDURE IF EXISTS updateAvailability;
DELIMITER $$
CREATE PROCEDURE
	updateAvailability(in aShowID varchar(6), aTickets int)  -- Show [tickets available] for specified show
BEGIN
DECLARE updated_seat_count int;
	SELECT
		t.number_of_seats - aTickets as updated_seat_count
	FROM
		Tickets t
	WHERE
		t.showID = aShowID;
END;$$
DELIMITER ;

CALL updateAvailability('901101', 2);

DROP PROCEDURE IF EXISTS storeCustomerData;
DELIMITER $$
CREATE PROCEDURE
	storeCustomerData(fname VARCHAR(25), mname VARCHAR(25), lname VARCHAR(25), add_no VARCHAR(5), add_st VARCHAR(25), post_code VARCHAR(8), email VARCHAR(25))  -- update customer data
BEGIN
	INSERT INTO Customers
		(first_name, middle_name, last_name, address_no, address_st, address_postcode, customer_email)
		VALUES
			(fname, mname, lname, add_no, add_st, post_code, email);
END;$$
DELIMITER ;

CALL storeCustomerData('Zoe', 'Bianca', 'Scott', '3', 'Saturn Way', 'CV37 7NE', 'zoebscott@hotmail.com');

SELECT * FROM Customers;

DROP DATABASE IF EXISTS Theatre;
CREATE DATABASE Theatre;
USE Theatre;

DROP PROCEDURE IF EXISTS getShows;
DELIMITER $$
CREATE PROCEDURE
	getShows()  -- Show [name, genre, dates] of all scheduled shows
BEGIN
	SELECT
		s.show_title AS `Name`, s.show_genre AS `Description`, p.perf_date AS `Date` 
	FROM
		Show_details AS s
	JOIN Performance AS p
		ON s.showID = p.perfID
	GROUP BY s.show_title;
END;$$
DELIMITER ;

CALL getShows();

DROP PROCEDURE IF EXISTS getShowByName;
DELIMITER %
CREATE PROCEDURE
	getShowByName(IN aName VARCHAR(20))  -- Show [name, description, dates] of specified show
BEGIN
	SELECT
		s.show_title AS `Name`, s.show_description AS `Description`, p.perf_date AS `Date`
	FROM
		Show_details AS s
	JOIN Performance AS p
		ON s.showID = p.perfID
	WHERE
		s.show_title = aName;
END;%
DELIMITER ;

CALL getShowByName('Aladdin');

DROP PROCEDURE IF EXISTS getShowByDate;
DELIMITER $$
CREATE PROCEDURE
	getShowByDate(IN aDate VARCHAR(19))  -- format 'YYYY-MM-DD hh:mm:ss'
BEGIN
	SELECT
		s.show_title AS `Name`, s.show_description AS `Description`, p.perf_date AS `Date`
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
	checkAvailability(IN aName VARCHAR(20), aDate VARCHAR(19))  -- Show [name, dates] of specified show
BEGIN
	SELECT
		s.show_title AS `Name`, s.show_genre `Genre`, p.seats_circle + p.seats_stall AS `Seats`
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
	updateAvailability(IN aShowID VARCHAR(6), aTickets INT)  -- Show [tickets available] for specified show
BEGIN
DECLARE updated_seat_count INT;
	SELECT
		t.number_of_seats - aTickets AS updated_seat_count
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

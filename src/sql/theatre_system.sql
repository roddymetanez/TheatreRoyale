DROP DATABASE IF EXISTS Theatre;
CREATE DATABASE Theatre;
USE Theatre;

--

DROP TABLE IF EXISTS Performance;
CREATE TABLE Performance (
    perfID INT PRIMARY KEY,
    SID INT,
    perf_date DATETIME,
    seats_circle INT,
    seats_stall INT
); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01001-001, 001, '2022-03-31  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01002-001, 001, '2022-04-01  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01003-001, 001, '2022-04-01 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01004-001, 001, '2022-04-02  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01005-001, 001, '2022-04-03  18:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01006-001, 001, '2022-04-04  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01007-001, 001, '2022-04-05  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01008-001, 001, '2022-04-06  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01009-001, 001, '2022-04-07  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01010-001, 001, '2022-04-07 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01011-001, 001, '2022-04-08  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01012-001, 001, '2022-04-08 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01013-001, 001, '2022-04-09  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01014-001, 001, '2022-04-10  18:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01015-001, 001, '2022-04-11  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01016-001, 001, '2022-04-12  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01017-001, 001, '2022-04-13  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (01018-001, 001, '2022-04-14  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02001-002, 002, '2022-04-15  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02002-002, 002, '2022-04-16  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02003-002, 002, '2022-04-16 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02004-002, 002, '2022-04-17  18:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02005-002, 002, '2022-04-18  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02006-002, 002, '2022-04-19  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02007-002, 002, '2022-04-20  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (02008-002, 002, '2022-04-21  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (03009-003, 003, '2022-04-22  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (03001-003, 003, '2022-04-22 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04001-004, 004, '2022-04-23  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04002-004, 004, '2022-04-24  18:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04003-004, 004, '2022-04-25  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04004-004, 004, '2022-04-26  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04005-004, 004, '2022-04-27  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04006-004, 004, '2022-04-27 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04007-004, 004, '2022-04-28  19:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04008-004, 004, '2022-04-28 01:30:00', 80, 120); 
INSERT INTO Performance (perfID, SID, perf_date, seats_circle, seats_stall)  VALUES (04009-004, 004, '2022-04-29  19:30:00', 80, 120); 

DROP TABLE IF EXISTS Show_details;
CREATE TABLE Show_details (
    SID INT NOT NULL PRIMARY KEY,
    show_ticketPrice DOUBLE,
    show_performer VARCHAR(100),
    show_title VARCHAR(100),
    show_description VARCHAR(500),
    show_genre VARCHAR(50),
    primary_language VARCHAR(50),
    show_live_music TINYINT(1),
    show_duration INT,
    show_runlength INT
);

INSERT INTO Show_details (SID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)  VALUES (1, 8.99, "Appleton Players", "Aladdin", "mightness at is to magnifice thesemble an inven story and opaque ther audience technicorn Thead into boot the gamera and tale use Swift’s in Gulliams form for the sames compositinged tank become", "Pantomime", "English", 0, 14, 110);
INSERT INTO Show_details (SID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)  VALUES (2, 15.99, "Brian Blessed", "Buster", "Leah Brothe changed taking use the sheet pinned is a brush with their charming voyages the scape here is mightness at is to magnifice thesemble an inven story and opaque ther audience technicorn ", "Play", "BSL", 0, 6, 120);
INSERT INTO Show_details (SID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)  VALUES (3, 10, "Chris Corkmann & Organ", "Couer en Hiver", "Thead into boot the gamera and tale use Swift’s in Gulliams form for the sames compositinged tank becomes a biting up as a bition ", "Cinema with live Bontempi organ", "French with English Subtitles", 1, 1, 167); 
INSERT INTO Show_details (SID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)  VALUES (4, 12, "Azariah Montgomery", "A Chorus Line ", "Leah Brobding DIY quickly illives musical grows worth scentled surgeone ents Gulliams first sing plus back tone wellers could is leaves to be lead with therheatre Leah Brothe changed taking use ", "Pantomime", "English", 0, 14, 110); 
INSERT INTO Show_details (SID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)  VALUES (5, 20, "Brynn Moore", "Babes in Arms ", "Jacoba Williver audience and design by Jaz Woodcock-Stewart their cames combines first easy appear of here imax against sing and fantle as the our and cle and sectiver’s hightnes fourney inger lon", "Musical", "English", 0, 6, 120); 


DROP TABLE IF EXISTS Customer; 
CREATE TABLE Customer (customerID  INT NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(25), middle_name VARCHAR(25), last_name VARCHAR(50), address_no VARCHAR(5), address_st VARCHAR(25), address_postcode VARCHAR(8), customer_email VARCHAR(50), cust_history VARCHAR(100)); 
DROP TABLE IF EXISTS Tickets; 
CREATE TABLE Tickets (customerID INT, perfID INT, stall INT, circle INT, post INT); 

--

DROP PROCEDURE IF EXISTS getShows;
DELIMITER $$
CREATE PROCEDURE
	getShows()  -- Provides Display Data for all scheduled shows
BEGIN
	SELECT
    perfID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language,
    show_duration, perf_date, seats_circle, seats_stall
    from Show_details, Performance
    where Performance.SID = Show_details.SID
    order by perf_date;
END;$$
DELIMITER ;

CALL getShows();

DROP PROCEDURE IF EXISTS getShowByName;
DELIMITER %
CREATE PROCEDURE
	getShowByName(in aName varchar(20))  -- Provides Display Data for specified show
BEGIN
	select
	show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language,
    show_duration, perf_date, seats_circle, seats_stall
    from Show_details, Performance
    where show_title = aName
    order by perf_date;
	END;%
DELIMITER ;

#CALL getShowByName('Aladdin');

DROP PROCEDURE IF EXISTS getShowByDate;
DELIMITER $$
CREATE PROCEDURE
	getShowByDate(in aDate varchar(19))  -- format 'YYYY-MM-DD hh:mm:ss'
BEGIN
	SELECT
		show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language,
    show_duration, perf_date, seats_circle, seats_stall
    from Show_details, Performance
    where perf_date = aDate; 
END;$$
DELIMITER ;

#CALL getShowByDate('2020-07-01 19:30:00');


DROP PROCEDURE IF EXISTS updateAvailableSeats;
DELIMITER $$
CREATE PROCEDURE
	updateAvailableSeats(in PID int, aStall int, aCircle int) 
BEGIN
	update Performance set seats_circle = aCircle, seats_stall = aStall
	where perfId = PID;
END;$$
DELIMITER ;

#CALL updateAvailableSeats(01001-001, 52, 108);

drop procedure if exists getAvailableSeats;
delimiter $$
create procedure 
	getAvailableSeats (in  PID int) 
    begin
    select * 
    from Performance
    where perfID = PID;
    end;$$
    delimiter ;
    
    call getAvailableSeats(1000);
    
DROP PROCEDURE IF EXISTS createTicket;
DELIMITER $$
CREATE PROCEDURE
	createTicket(in CID int, PID int,  aStall int, aCircle int, aPost int) 
BEGIN
	insert into Tickets (customerID, perfID, stall, circle, post)
    values (CID, PID, aStall, aCircle, post);
END;$$
DELIMITER ;

#CALL createTicket(1, 1000, 2, 2, 0);
#select * from Tickets;


DROP PROCEDURE IF EXISTS getTicket;
DELIMITER $$
CREATE PROCEDURE
	getTicket(in CID int) 
BEGIN
	select * from Tickets 
    where customerID = CID;
END;$$
DELIMITER ;

#call getTicket(1);

    
DROP PROCEDURE IF EXISTS registerCustomer;
delimiter $$
CREATE PROCEDURE
	registerCustomer(fname VARCHAR(25), lname VARCHAR(25), add_no VARCHAR(5), 
    add_st VARCHAR(25), post_code VARCHAR(8))  -- update customer data
BEGIN
if (select 1=1 from Customer where first_name = fname and last_name = lname)
	then begin
    select customerID from Customer where first_name = fname and last_name = lname;
    end;
    else begin
    INSERT INTO Customer
		(first_name, last_name, address_no, address_st, address_postcode)
		VALUES
			(fname,lname, add_no, add_st, post_code);
            select last_insert_id() as customerID;
            end;
	end if;
END;$$
DELIMITER ;

CALL registerCustomer('Zoe','Scott', '3', 'Saturn Way', 'CV37 7NE');

#SELECT * FROM Customer;

DROP PROCEDURE IF EXISTS getCustomerData;
delimiter $$
CREATE PROCEDURE getCustomerData(in CID int) 
   BEGIN
	select * from Customer
    where Customer.CustomerID = CID;
   END;$$
DELIMITER ;

CALL getCustomerData(1); 

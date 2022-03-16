DROP DATABASE IF EXISTS Theatre_Systems;
CREATE DATABASE IF NOT EXISTS Theatre_System;

DROP TABLE IF EXISTS Customers;
CREATE TABLE IF NOT EXISTS Customers (
	first_name VARCHAR(25),
    middle_name VARCHAR(25),
    last_name VARCHAR(25),
    address VARCHAR(25),
    credit_card VARCHAR(25)
);

DROP TABLE IF EXISTS Show_Details;
CREATE TABLE IF NOT EXISTS Show_details (
        showID Int,
        show_ticketPrice DOUBLE,
        show_performer VARCHAR(50),
		show_title VARCHAR(50),
		--show_type VARCHAR(25),  -- matinee etc RMT Covereed in timings
        show_description VARCHAR(500),
        show_genre VARCHAR(25),
        primary_language VARCHAR(25),
        show_LiveMusic TINYINT(1)
);

DROP TABLE IF EXISTS Seats;
CREATE TABLE IF NOT EXISTS Seats (
    seats_circle Int,
    seats_stall Int,
	maximum_avail_seats INT,
    concessionary_seats INT
);

DROP TABLE IF EXISTS Performance;
CREATE TABLE IF NOT EXISTS Performance (
    performance_ID Int,
    showID Int
	performance_date DATETIME,
    duration VARCHAR(15), --Assume this means number of calendar days the show will run for?
    standard_price VARCHAR(15), -- possible duplication with show prices? Override for Matinee?
    performance_duration VARCHAR(15)
);
-- DATETIME - format: YYYY-MM-DD HH:MI:SS

DROP TABLE IF EXISTS Tickets;
CREATE TABLE IF NOT EXISTS Tickets (
	availability INT,
    seats INT,  -- number of seats available
    performanceID INT,
    performance VARCHAR(25),
    concessionary_discount INT
);

INSERT INTO Show (showID, show_ticketPrice, show_performer, show_title, showDescription, show_genre, primary_language, show_LiveMusic)
VALUES (901, 8.99, "Appleton Players", "Aladdin", "Well what a disappointment that was, not even sure where to begin." ,"Pantomime", "English", 0);

INSERT INTO Show (showID, show_ticketPrice, show_performer, show_title, showDescription, show_genre, primary_language, show_LiveMusic)
VALUES (902, 15.99, "Brian Blessed", "Buster", "Well what a disappointment that was, I was hoping to see Prince Vultan, like in Flash Gordon","Play", "BSL", 0);

INSERT INTO Show (showID, show_ticketPrice, show_performer, show_title, showDescription, show_genre, primary_language, show_LiveMusic)
VALUES (903, 8.99, "Couer en Hiver", "Chris Corkmann & Organ" "Eh bien, quelle déception c'était, je ne savais même pas par où commencer.", "Cinema", 1);


INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901101, '2020-07-01 19:30:00', 14, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901102, '2020-07-01 12:30:00', 14, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901102,  '2020-07-02 219:30:00', 13, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901103, '2020-07-02 12:30:00', 13, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901104, '2020-07-03 19:30:00', 12, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (901, 901105, '2020-07-04 19:30:00', 11, 110);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (902, 902101, '2020-07-01 19:30:00', 1, 150);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (902, 902101, '2020-07-01 19:30:00', 0, 150);
INSERT INTO Performance (showID, PerformanceID, performance_date, duration, performance_duration)
VALUES (903, 903101, '2020-07-01 19:30:00', 0, 167);

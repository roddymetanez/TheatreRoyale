DROP DATABASE IF EXISTS Theatre_Systems;
CREATE DATABASE Theatre_Systems;
USE Theatre_Systems;

DROP TABLE IF EXISTS Customers;
CREATE TABLE Customers (
    customerID INT,
    first_name VARCHAR(25),
    middle_name VARCHAR(25),
    last_name VARCHAR(50),
    address_no VARCHAR(25),
    address_st VARCHAR(25),
    address_code VARCHAR(25),
    customer_email VARCHAR(50),
    credit_card VARCHAR(16),
    cust_history VARCHAR(100) -- Derived archive of perfID, showID, DATETIME of transaction. 
);

DROP TABLE IF EXISTS Show_details;
CREATE TABLE Show_details (
    showID INT,
    show_ticketPrice DOUBLE,
    show_performer VARCHAR(100),
    show_title VARCHAR(50),
    show_description VARCHAR(500),
    show_genre VARCHAR(25),
    primary_language VARCHAR(25),
    show_live_music TINYINT(1),
    show_duration INT, --number of calendar days the show will run for
    show_runlength INT
);

DROP TABLE IF EXISTS Performance;
CREATE TABLE Performance (
    perfID INT,
    showID INT,
    perf_date DATETIME,-- DATETIME - format: 'YYYY-MM-DD HH:MI:SS' or 'YYYY-MM-DD'  
    seats_circle INT,
    seats_stall INT
);

/*
DROP TABLE IF EXISTS Tickets;
CREATE TABLE IF NOT EXISTS Tickets (
	availability INT,
    seats INT,  -- number of seats available
    performanceID INT,
    performance VARCHAR(25),
    concessionary_discount INT
);
*/

INSERT INTO Show_details(showID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)
VALUES (901, 8.99, "Appleton Players", "Aladdin", "Well what a disappointment that was, not even sure where to begin." ,"Pantomime", "English", 0, 14, 110);

INSERT INTO Show_details(showID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)
VALUES (902, 15.99, "Brian Blessed", "Buster", "Well what a disappointment that was, I was hoping to see Prince Vultan, like in Flash Gordon","Play", "BSL", 0, 7, 110);

INSERT INTO Show_details(showID, show_ticketPrice, show_performer, show_title, show_description, show_genre, primary_language, show_live_music, show_duration, show_runlength)
VALUES (903, 8.99, "Couer en Hiver", "Chris Corkmann & Organ", "Eh bien, quelle déception c'était, je ne savais même pas par où commencer.", "Cinema","French, with English Subtitles", 1, 1, 167);


INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901101, '2020-07-01 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901102, '2020-07-01 12:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901102, '2020-07-02 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901103, '2020-07-02 12:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901104, '2020-07-03 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (901, 901105, '2020-07-04 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (902, 902101, '2020-07-01 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (902, 902101, '2020-07-01 19:30:00');
INSERT INTO Performance (showID, perfID, perf_date)
VALUES (903, 903101, '2020-07-01 19:30:00');

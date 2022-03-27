drop database if exists Bank;
create database Bank;
use Bank;
create table Branch (
BranchName int,
City varchar(15));
insert into Branch values(42, 'Truro');
insert into Branch values(16, 'Brighton');
insert into Branch values(26, 'Edinburgh');
create table Customer (
CustomId int primary key auto_increment,
CustomName varchar(10),
Street varchar(20),
City varchar(10),
BranchName int,
Banker int);
insert into Customer values(1, 'David', 'Gulveal', 'Truro', 42,  463);
insert into Customer values(2, 'Sylvia', 'Church', 'Truro', 42, 463);
insert into Customer values(3, 'Angus', 'Mont', 'Brighton', 16, 291);
insert into Customer values(4, 'Leo', 'PiersHill', 'Edinburgh', 42, 672);
insert into Customer values(5,'Rosie', 'PiersHill', 'Edinburgh', 26, 672);
create table Account (
AccountNo int primary key auto_increment,
AccType varchar(1),
Balance int,
Interest float,
Overdraft int);
insert into Account values(1001, 'C', 1000,0,500);
insert into Account values(1002,'S',500,0.5,0);
insert into Account values(1003, 'C', 200,0,0);
insert into Account values(1004, 'S', 10000,0.6,3000);
insert into Account values(1005, 'C', 8000,0,0);
insert into Account values(1006, 'S', 1,0.1,0);
insert into Account values(1007, 'C', -350,-5,750);
insert into Account values(1008, 'S', 85000,1.0,0);
create table Banker (
EmployeeId int,
ManagerId int);
insert into Banker values(463, 672);
insert into Banker values(291, 672);
insert into Banker values(142, 672);
insert into Banker values(536, 291);
insert into Banker values(672, null);
create table AccountHeld(
CustomId int,
AccountNo int);
 insert into AccountHeld values(1,1001);
 insert into AccountHeld values(1,1002);
 insert into AccountHeld values(2,1003);
 insert into AccountHeld values(3,1004);
 insert into AccountHeld values(3,1005);
 insert into AccountHeld values(4,1006);
 insert into AccountHeld values(4,1008);
 insert into AccountHeld values(5,1007);
 create table WorkPlace(
 BranchName int,
 EmployeeId int);
 insert into WorkPlace values(42,463);
 insert into WorkPlace values(16,291);
 insert into WorkPlace values(26,142);
 insert into WorkPlace values(16,536);
 insert into WorkPlace values(26,672);
 
select CustomName, City from Customer
where Banker = 672;

select Account.AccountNo, AccType, Balance, Overdraft 
from Account join AccountHeld
on Account.AccountNo = AccountHeld.AccountNo
where AccountHeld.CustomId = 4;

select Customer.CustomName, sum(Account.Balance) as 'Balance'
from Customer, (Account join AccountHeld on Account.AccountNo = AccountHeld.AccountNo)
where Customer.CustomId = AccountHeld.CustomId
group by AccountHeld.CustomId;

select BranchName, sum(Balance) as 'Branch Balance',
count(Customer.customId) as 'Branch Customers'
from Customer, AccountHeld, Account
where Customer.CustomId = AccountHeld.CustomId
and AccountHeld.AccountNo = Account.AccountNo
group by BranchName;

select BranchName, sum(Overdraft) as 'Total overdraft agreed'
from Customer, Account, AccountHeld
where Customer.CustomId = AccountHeld.CustomId
and AccountHeld.AccountNo = Account.AccountNo
group by BranchName;

select Banker, sum(Balance) as 'Balance'
from Banker, Customer, Account, AccountHeld
where Customer.Banker = Banker.EmployeeId
and Customer.CustomId = AccountHeld.CustomId
and AccountHeld.AccountNo = Account.AccountNo
group by Banker;

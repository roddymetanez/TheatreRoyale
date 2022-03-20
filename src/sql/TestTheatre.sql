drop database if exists theatre;
create database theatre;
use theatre;

create table Perf(
Name varchar(20),
Blurb varchar(20),
Date varchar(15)
);

insert into Perf (Name, Blurb, Date) 
values ('Alice', 'Horror Show', '01-04-22');
insert into Perf (Name, Blurb, Date) 
values ('Rita', 'Funny', '01-04-22');
insert into Perf (Name, Blurb, Date) 
values ('Alice', 'Horror Show', '02-04-22');

select * from Perf;

delimiter /
create procedure getShowByName(in aName varchar(20))
begin
select Name, Blurb, Date 
from Perf
where Name = aName;
end;/

call getShowByName('Alice')/

create procedure getShowByDate(in aDate varchar(20))
begin
select Name, Blurb, Date 
from Perf
where Date = aDate;
end;/

call getShowByDate('01-04-22')/

create procedure getShows()
begin
select Name, Blurb, Date 
from Perf;
end;/

call getShows()/





drop table if exists Seats cascade;

create table Seats(
    PlaneId integer    not null,
    SeatNo  varchar(4) not null,
    primary key (PlaneId, SeatNo)
);

drop table if exists Flights cascade;

create table Flights(
    FlightId   integer   not null primary key,
    FlightTime timestamp not null,
    PlaneId    integer   not null
);

drop table if exists Users cascade;

create table Users(
    UserId         integer   not null primary key,
    HashedPassword text      not null
);

drop type if exists seatStatus cascade;
create type seatStatus as enum ('sold', 'booked');

drop table if exists SeatsStatus cascade;

-- Статус несвободных мест. Если место свободно, его здесь нет
create table SeatsStatus(
    UserId     integer,
    FlightId   integer    not null,
    SeatNo     varchar(4) not null,
	SeatStatus seatStatus not null,
	BookedTime timestamp,
    primary key (FlightId, SeatNo),
	constraint fk_user   foreign key(UserId)   references Users(UserId),
	constraint fk_flight foreign key(FlightId) references Flights(FlightId)
);
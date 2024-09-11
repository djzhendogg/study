create or replace function flight_time(date integer, hour integer, minutes integer)
    returns timestamp
    language plpgsql as
$$
begin
    return cast('November ' || date ||
                ', 2022 ' || hour ||
                ':' || minutes ||
                ':00' as timestamp);
end;
$$;

insert into Flights (FlightId, FlightTime, PlaneId)
values (1, timestamp '2022-11-20 10:00:00', 0),
       (2, timestamp '2022-11-21 10:00:00', 0),
	   (3, timestamp '2022-11-20 20:00:00', 1),
	   (4, timestamp '2022-11-21 20:00:00', 1),
	   (666, timestamp '2010-09-03 10:00:00', 0);

do
$$
    declare
        it record;
    begin
        for it in 0..1
            loop
                for width in 0..5
                    loop
                        for length in 0..9
                            loop
                                insert into seats(PlaneId, SeatNo)
                                values (it, TO_CHAR(length, 'fm000') || (chr(ascii('A') + width)));
                            end loop;
                    end loop;
            end loop;
    end;
$$;
end;
function sp_random_int(IN min integer, IN max integer)
	returns integer as 
$BODY$  BEGIN
    RETURN trunc(random() * ((max + 1) - min) + min);
END; $BODY$ 
language PLPGSQL volatile
cost 100;
alter function sp_random_int(IN min integer, IN max integer) owner to postgres;
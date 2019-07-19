drop table if exists tb_batch_sample;


create table tb_batch_sample(

)
;



select date_trunc('day', now())::timestamp + interval sp_random_int(0, 23) || ' hour'

select current_timestamp - interval '23' || ' hours'

select date_trunc('day', now())::timestamp without time zone

select now(), current_timestamp, '3' || '555'
select current_timestamp - interval  || ' hour'


select current_timestamp - interval random_value from (
select sp_random_int(0, 86399) || ' second' as random_value
) r



select s, count(*) as cnt from (
select sp_random_int(3, 5) as s
from generate_series(1, 10000)
) r
group by s
;

CREATE or replace FUNCTION sp_random_int(min integer, max integer)
RETURNS INTEGER AS
$$ BEGIN
    RETURN trunc(random() * ((max + 1) - min) + min);
END; $$
LANGUAGE PLPGSQL;

id, name, full_name, short_name, create_date, update_date

select * from tb_team_history
;

create table tb_team_history as
select * from tb_team
where 1 = 2
;

select * from tb_team_test
;

insert into tb_team_test
select t.*
, '127.0.0.1'::inet as ip4address
, '127.0.0.1'::inet as ip6address
, '127.0.0.1'::inet as ipaddress

from tb_team t
where id = 2
;





select * from tb_schedule
;

create table tb_schedule(
    id integer,
    type text,
    detail text
)
;


create table tb_team_test(
    id integer,
    name text,
    full_name text,
    short_name text,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    ip4address inet,
    ip6address inet,
    ipaddress inet
)
select * from tb_team
;

insert into tb_schedule(id, type, detail, create_date, update_date) values (
1, 'ONCE', 'detail', now(), now()
)


select * from tb_team


drop table tb_schedule;

create table tb_schedule as
select id, name as type, name as detail, create_date, update_date from tb_team
where 1 = 2
;


delete from tb_team
where name is null

select * from tb_team_cnt

create table tb_team_cnt2 as
select name, count(name) as team_cnt
from tb_team_history
group by name

CREATE or replace FUNCTION sp_action_1()
RETURNS TEXT AS
$$ BEGIN
    RETURN random();
END; $$
LANGUAGE PLPGSQL;



select * from sp_action_3("i_param1"='haha')

select * from sp_




CREATE or replace FUNCTION sp_action_3(i_param1 text, i_param2 text)
RETURNS TEXT AS
$$ BEGIN
    RETURN random();
END; $$
LANGUAGE PLPGSQL;
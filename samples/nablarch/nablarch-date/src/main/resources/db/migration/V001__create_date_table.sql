create table business_date
(
    segment_id varchar(2) not null,
    biz_date   varchar(8) not null,
    primary key (segment_id)
);

insert into business_date (segment_id, biz_date) values ('00', '20220101');
insert into business_date (segment_id, biz_date) values ('01', '20220102');

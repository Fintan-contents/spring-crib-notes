create table code_pattern
(
    code_id    varchar(6) not null,
    code_value varchar(6) not null,
    pattern01  varchar(1) not null,
    pattern02  varchar(1),
    pattern03  varchar(1),
    pattern04  varchar(1),
    pattern05  varchar(1),
    pattern06  varchar(1),
    pattern07  varchar(1),
    pattern08  varchar(1),
    pattern09  varchar(1),
    pattern10  varchar(1),
    pattern11  varchar(1),
    pattern12  varchar(1),
    pattern13  varchar(1),
    pattern14  varchar(1),
    pattern15  varchar(1),
    pattern16  varchar(1),
    pattern17  varchar(1),
    pattern18  varchar(1),
    pattern19  varchar(1),
    pattern20  varchar(1),
    primary key (code_id, code_value)
);

create table code_name
(
    code_id    varchar(6) not null,
    code_value varchar(6) not null,
    lang       varchar(2) not null,
    sort_order varchar(2) not null,
    code_name  varchar(50) not null,
    short_name varchar(50),
    option01   varchar(40),
    option02   varchar(40),
    option03   varchar(40),
    option04   varchar(40),
    option05   varchar(40),
    option06   varchar(40),
    option07   varchar(40),
    option08   varchar(40),
    option09   varchar(40),
    option10   varchar(40),
    primary key (code_id, code_value, lang)
);

insert into code_pattern (code_id, code_value, pattern01, pattern02) values ('CODE01', 'MALE', '1', '1');
insert into code_pattern (code_id, code_value, pattern01, pattern02) values ('CODE01', 'FEMALE', '1', '1');
insert into code_pattern (code_id, code_value, pattern01, pattern02) values ('CODE01', 'OTHER', '0', '1');

insert into code_name (code_id, code_value, lang, sort_order, code_name) values ('CODE01', 'MALE', 'ja', '1', '男性');
insert into code_name (code_id, code_value, lang, sort_order, code_name) values ('CODE01', 'FEMALE', 'ja', '2', '女性');
insert into code_name (code_id, code_value, lang, sort_order, code_name) values ('CODE01', 'OTHER', 'ja', '3', 'その他');

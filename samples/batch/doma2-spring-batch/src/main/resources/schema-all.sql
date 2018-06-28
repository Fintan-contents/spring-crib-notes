create table employee (
  id serial not null,
  name varchar(255) not null,
  salary bigint not null,
  grade_id int not null,
  primary key (id)
);

create table grade (
  id serial not null,
  bonus_magnification bigint,
  fixed_bonus bigint,
  primary key (id)
);

create table bonus (
  id serial not null,
  employee_id bigint not null,
  payments bigint not null,
  primary key (id)
);

alter table employee add foreign key (grade_id) references grade(id);
alter table bonus add foreign key (employee_id) references employee(id);

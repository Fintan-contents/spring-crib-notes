create table users (
  id bigint auto_increment,
  name varchar(255) not null,
  age int not null,
  version_no bigint not null default 1,
  role varchar(255) not null,
  primary key (id)
);

create table roles (
  name varchar(255),
  primary key (NAME)
);

alter table users add foreign key (role) references ROLES(name);

insert into roles (name) values ('admin');
insert into roles (name) values ('user');

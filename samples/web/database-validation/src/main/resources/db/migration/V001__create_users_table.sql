create table roles (
  name varchar(255),
  primary key (NAME)
);

create table users (
  id bigint auto_increment not null,
  name varchar(255) not null,
  mailAddress varchar(255) not null,
  role varchar(255) not null,
  primary key (id)
);

alter table users add foreign key (role) references ROLES(name);

create unique index users_mailAddress on users(mailAddress);

insert into roles (name) values ('admin');

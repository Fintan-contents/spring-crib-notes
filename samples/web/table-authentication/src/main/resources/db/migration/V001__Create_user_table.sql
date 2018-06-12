create table users (
  username varchar(255) not null,
  password varchar(60) not null,
  primary key (username)
);

create table user_role (
  username varchar(255) not null,
  role     varchar(255) not null,
  primary key (username, role)
);

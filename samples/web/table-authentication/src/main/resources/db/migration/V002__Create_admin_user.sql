-- adminユーザを登録する
insert into users values ('admin', '$2a$10$vTHgo5H7j7ayxcK7M9j4RuSNmiegX.Kng6c08BR/rWFW4U4gxsUO2');
insert into user_role (username, role)
values ('admin', 'ROLE_admin');
-- 一般ユーザ
insert into users
values ('keel', '$2a$10$vTHgo5H7j7ayxcK7M9j4RuSNmiegX.Kng6c08BR/rWFW4U4gxsUO2');
insert into user_role (username, role)
values ('keel', 'ROLE_user');


insert into roles (id, name) values (1, 'ROLE_ADMIN') on conflict do nothing;
insert into roles (id, name) values (2, 'ROLE_MODERATOR') on conflict do nothing;
insert into roles (id, name) values (3, 'ROLE_READER') on conflict do nothing;

insert into users (username, password, roleid) values ('admin', '$2a$12$T3Nt5hkH54agvABcq1zPnuIEfOBFLxkp1YwdYMULPvM7EEnfAObrS', 1) on conflict do nothing;
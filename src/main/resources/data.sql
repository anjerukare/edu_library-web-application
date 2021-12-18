insert into roles (id, name) values (1, 'ROLE_ADMIN') on conflict do nothing;
insert into roles (id, name) values (2, 'ROLE_MODERATOR') on conflict do nothing;
insert into roles (id, name) values (3, 'ROLE_READER') on conflict do nothing;

insert into users (id, username, password, roleid) values (1, 'admin', '$2a$12$T3Nt5hkH54agvABcq1zPnuIEfOBFLxkp1YwdYMULPvM7EEnfAObrS', 1) on conflict do nothing;
select setval('users_id_seq', (select max(id) from users));

insert into public.tags (id, name, description) values (1, 'Роман-эпопея', 'Масштабный исторических и социальный фон, яркие персонажи') on conflict do nothing;
insert into public.tags (id, name, description) values (2, 'Роман', 'Повествование о жизни и развитии личности главного героя') on conflict do nothing;
insert into public.tags (id, name, description) values (3, 'Повесть', 'Небольшой сюжет сфокусированный на главном герое') on conflict do nothing;
insert into public.tags (id, name, description) values (4, 'Рассказ', 'Простой по структуре чёткий сюжет с развязкой в конце') on conflict do nothing;
insert into public.tags (id, name, description) values (5, 'Притча', 'Короткий нравоучительный рассказ с острой проблематикой') on conflict do nothing;
insert into public.tags (id, name, description) values (6, 'Комедия', 'Остроумный и динамических сюжет о курьёзах общества') on conflict do nothing;
insert into public.tags (id, name, description) values (7, 'Трагедия', 'Повествование об терзаниях, испытаниях главного героя') on conflict do nothing;
insert into public.tags (id, name, description) values (8, 'Драма', 'Построенный на диалогах сюжет о личной жизни главного героя') on conflict do nothing;
insert into public.tags (id, name, description) values (9, 'Поэма', 'Развёрнутое описание масштабных событий') on conflict do nothing;
insert into public.tags (id, name, description) values (10, 'Стихотворение', 'Описание события, либо чувств и внутреннего мира персонажа') on conflict do nothing;

insert into public.statuses (id, name) values (1, 'PLANNED') on conflict do nothing;
insert into public.statuses (id, name) values (2, 'READING_NOW') on conflict do nothing;
insert into public.statuses (id, name) values (3, 'DELAYED') on conflict do nothing;
insert into public.statuses (id, name) values (4, 'QUIT') on conflict do nothing;
insert into public.statuses (id, name) values (5, 'READ') on conflict do nothing;
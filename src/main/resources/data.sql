insert into roles (id, name) values (1, 'ROLE_ADMIN') on conflict do nothing;
insert into roles (id, name) values (2, 'ROLE_MODERATOR') on conflict do nothing;
insert into roles (id, name) values (3, 'ROLE_READER') on conflict do nothing;

insert into users (username, password, roleid) values ('admin', '$2a$12$T3Nt5hkH54agvABcq1zPnuIEfOBFLxkp1YwdYMULPvM7EEnfAObrS', 1) on conflict do nothing;

insert into public.tags (name, description) values ('Роман-эпопея', 'Масштабный исторических и социальный фон, яркие персонажи');
insert into public.tags (name, description) values ('Роман', 'Повествование о жизни и развитии личности главного героя');
insert into public.tags (name, description) values ('Повесть', 'Небольшой сюжет сфокусированный на главном герое');
insert into public.tags (name, description) values ('Рассказ', 'Простой по структуре чёткий сюжет с развязкой в конце');
insert into public.tags (name, description) values ('Притча', 'Короткий нравоучительный рассказ с острой проблематикой');
insert into public.tags (name, description) values ('Комедия', 'Остроумный и динамических сюжет о курьёзах общества');
insert into public.tags (name, description) values ('Трагедия', 'Повествование об терзаниях, испытаниях главного героя');
insert into public.tags (name, description) values ('Драма', 'Построенный на диалогах сюжет о личной жизни главного героя');
insert into public.tags (name, description) values ('Поэма', 'Развёрнутое описание масштабных событий');
insert into public.tags (name, description) values ('Стихотворение', 'Описание события, либо чувств и внутреннего мира персонажа');

insert into public.statuses (id, name) values (1, 'PLANNED');
insert into public.statuses (id, name) values (2, 'READING_NOW');
insert into public.statuses (id, name) values (3, 'DELAYED');
insert into public.statuses (id, name) values (4, 'QUIT');
insert into public.statuses (id, name) values (5, 'READ');
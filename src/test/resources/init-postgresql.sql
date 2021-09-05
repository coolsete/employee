create table if not exists public.position (
    id integer primary key,
    name varchar(20)
);

create table if not exists public.employee (
    id integer primary key,
    name varchar(20),
    positionID integer not null references position (id),
    superior integer references employee (id),
    start_date timestamp,
    end_date timestamp
);

create index if not exists employee_position_id_idx on public.employee (positionID);
create index if not exists employee_superior_id_idx on public.employee (superior);

insert into position (id, name) values (1, 'engineer'), (2, 'manager'), (3, 'CEO'), (4, 'salesman') on conflict do nothing;
insert into employee (id, name, positionID, superior, start_date, end_date) VALUES (1, 'test1', 2, null, '2021-01-06', '2021-01-06') on conflict do NOTHING;
insert into employee (id, name, positionID, superior, start_date, end_date) VALUES (2, 'test2', 1, 1, '2021-01-06', '2021-01-06') on conflict do NOTHING;
insert into employee (id, name, positionID, superior, start_date, end_date) VALUES (3, 'test3', 1, 1, '2021-01-06', '2021-01-06') on conflict do NOTHING;

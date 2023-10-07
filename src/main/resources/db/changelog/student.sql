--liquibase formatted sql

--changeset Unionsilver:1
create index student_name_index on student(name)
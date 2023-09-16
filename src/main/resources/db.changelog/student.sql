--liquibase formatted sql

--changeset Bratus:1
create index student_name_index on student(name)

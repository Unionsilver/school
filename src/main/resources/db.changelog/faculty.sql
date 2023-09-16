--liquibase formatted sql

--changeset Bratus:2
create index faculty_name_color_index on faculty(name, color)
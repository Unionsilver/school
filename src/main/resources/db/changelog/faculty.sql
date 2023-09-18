--liquibase formatted sql

--changeset Unionsilver:2
create index faculty_name_color_index on faculty(name, color)
DROP DATABASE IF EXISTS streets;
CREATE DATABASE streets;
USE streets;

create table street(id int auto_increment primary key, name varchar(100) not null);

create table traffic_light
(
    id int auto_increment primary key,
    status varchar(100) not null,
    changed_at date default null,
    street_id int not null
);

INSERT INTO street (name) VALUES ('DX-Signal');
INSERT INTO street (name) VALUES ('JM-Olxs');
INSERT INTO street (name) VALUES ('DM-Deci');
INSERT INTO street (name) VALUES ('JX-Peper');


INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('GREEN', '2022-05-01', 1);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('RED', '2022-05-01', 1);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('GREEN', '2022-05-01', 1);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('GREEN', '2022-05-01', 2);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('YELLOW', null, 2);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('YELLOW', null, 2);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('GREEN', '2022-05-01', 2);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('YELLOW', null, 3);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('YELLOW', null, 3);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('RED', '2022-05-01', 3);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('YELLOW', null, 4);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('RED', '2022-05-01', 4);
INSERT INTO traffic_light (status, changed_at, street_id) VALUES ('RED', '2022-05-01', 4);

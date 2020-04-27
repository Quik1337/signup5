CREATE TABLE user_
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE event_
(
    id            SERIAL PRIMARY KEY,
    host_id       INT NOT NULL REFERENCES user_ (id),
    title         VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    date_of_event DATE NOT NULL,
    time_of_event TIME NOT NULL,
    location      VARCHAR(255) NOT NULL
);

CREATE TYPE attendance_ AS ENUM ('ATTENDING', 'NOT_ATTENDING', 'MAYBE', 'NO_RESPONSE');

CREATE TABLE invitation_
(
    id         SERIAL PRIMARY KEY,
    event_id   INT         NOT NULL REFERENCES event_ (id),
    guest_id   INT         NOT NULL REFERENCES user_ (id),
    comment    VARCHAR(255),
    attendance attendance_ DEFAULT 'NO_RESPONSE'
);

insert into user_ (id, first_name, last_name, email) values (1, 'Adam', 'Bäckström', 'adam.backstrom@email.com');
insert into user_ (id, first_name, last_name, email) values (2, 'Adem', 'Üre', 'adem.üre@email.com');
insert into user_ (id, first_name, last_name, email) values (3, 'Linus', 'Bohm', 'linus.bohm@email.com');
insert into user_ (id, first_name, last_name, email) values (4, 'Nataliya', 'Zaluska', 'nataliya.zaluska@email.com');
insert into user_ (id, first_name, last_name, email) values (5, 'Arzu', 'Oguz', 'arzu.oguz@email.com');
insert into user_ (id, first_name, last_name, email) values (6, 'Jan', 'Grape', 'jan.grape@email.com');

insert into event_ (id, host_id, title, description, date_of_event, time_of_event, location) values (1, 1, 'Daily Standup', 'A meeting where we discuss what we have done and what we are going to do', '2020-07-19', '09:00:00', 'Skype');
insert into event_ (id, host_id, title, description, date_of_event, time_of_event, location) values (2, 2, 'User Story Presentation', 'We will present our User Story', '2020-04-28', '15:30:00', 'Skype');
insert into event_ (id, host_id, title, description, date_of_event, time_of_event, location) values (3, 3, 'After Work Gaming', 'We gonna play some 7 Wonders to celebrate', '2020-07-29', '16:00:00', 'Office');

insert into invitation_ (event_id, guest_id) values (1, 1);
insert into invitation_ (event_id, guest_id) values (1, 2);
insert into invitation_ (event_id, guest_id) values (1, 3);
insert into invitation_ (event_id, guest_id) values (1, 4);
insert into invitation_ (event_id, guest_id) values (1, 5);

insert into invitation_ (event_id, guest_id) values (2, 1);
insert into invitation_ (event_id, guest_id) values (2, 2);
insert into invitation_ (event_id, guest_id) values (2, 3);
insert into invitation_ (event_id, guest_id) values (2, 6);

insert into invitation_ (event_id, guest_id) values (3, 1);
insert into invitation_ (event_id, guest_id) values (3, 2);
insert into invitation_ (event_id, guest_id) values (3, 3);
insert into invitation_ (event_id, guest_id) values (3, 4);
insert into invitation_ (event_id, guest_id) values (3, 5);
insert into invitation_ (event_id, guest_id) values (3, 6);
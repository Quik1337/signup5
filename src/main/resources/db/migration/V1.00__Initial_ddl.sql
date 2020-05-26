CREATE TABLE user_
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE account_
(
    id       SERIAL PRIMARY KEY,
    user_id  INT NOT NULL REFERENCES user_ (id),
    password VARCHAR(255)
);

CREATE TABLE event_
(
    id                  SERIAL PRIMARY KEY,
    host_id             INT NOT NULL REFERENCES user_ (id),
    title               VARCHAR(255) NOT NULL,
    description         VARCHAR(255),
    date_of_event       DATE NOT NULL,
    start_time_of_event TIME NOT NULL,
    end_time_of_event   TIME NOT NULL,
    location            VARCHAR(255) NOT NULL
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

insert into user_ (first_name, last_name, email) values ('Adam', 'Bäckström', 'adam.backstrom@email.com');
insert into user_ (first_name, last_name, email) values ('Adem', 'Üre', 'adem.üre@email.com');
insert into user_ (first_name, last_name, email) values ('Linus', 'Bohm', 'linus.bohm@email.com');
insert into user_ (first_name, last_name, email) values ('Nataliya', 'Zaluska', 'nataliya.zaluska@email.com');
insert into user_ (first_name, last_name, email) values ('Arzu', 'Oguz', 'arzu.oguz@email.com');
insert into user_ (first_name, last_name, email) values ('Jan', 'Grape', 'jan.grape@email.com');

insert into account_ (user_id, password) values ('1', '12345');
insert into account_ (user_id, password) values ('3', '12345');
insert into account_ (user_id, password) values ('6', '12345');

insert into event_ (host_id, title, description, date_of_event, start_time_of_event, end_time_of_event, location) values (1, 'Daily Standup', 'A meeting where we discuss what we have done and what we are going to do.', '2020-07-19', '09:00:00', '09:15:00', 'Skype');
insert into event_ (host_id, title, description, date_of_event, start_time_of_event, end_time_of_event, location) values (2, 'LIA Check-in med Janne', 'Checking in with Janne to see how the LIA project is developing.', '2020-05-07', '14:00:00', '14:30:00', 'Skype');
insert into event_ (host_id, title, description, date_of_event, start_time_of_event, end_time_of_event, location) values (3, 'After Work Gaming', 'We gonna play some 7 Wonders to celebrate.', '2020-07-29', '16:00:00', '16:30:00', 'Office');

insert into invitation_ (event_id, guest_id) values (1, 1);
insert into invitation_ (event_id, guest_id) values (1, 2);
insert into invitation_ (event_id, guest_id) values (1, 3);
insert into invitation_ (event_id, guest_id) values (1, 4);
insert into invitation_ (event_id, guest_id) values (1, 5);

insert into invitation_ (event_id, guest_id) values (2, 1);
insert into invitation_ (event_id, guest_id) values (2, 2);
insert into invitation_ (event_id, guest_id) values (2, 3);
insert into invitation_ (event_id, guest_id) values (2, 6);

/*
insert into invitation_ (event_id, guest_id) values (3, 1);
insert into invitation_ (event_id, guest_id) values (3, 2);
insert into invitation_ (event_id, guest_id) values (3, 3);
insert into invitation_ (event_id, guest_id) values (3, 4);
insert into invitation_ (event_id, guest_id) values (3, 5);
insert into invitation_ (event_id, guest_id) values (3, 6);
 */
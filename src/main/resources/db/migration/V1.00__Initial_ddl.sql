CREATE TYPE attendance_ AS ENUM ('ATTENDING', 'NOT_ATTENDING', 'MAYBE', 'NO_RESPONSE');

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
    user_id  INT NOT NULL UNIQUE REFERENCES user_ (id),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE event_
(
    id            SERIAL PRIMARY KEY,
    host_id       INT NOT NULL REFERENCES account_ (id),
    title         VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    date_of_event DATE NOT NULL,
    time_of_event TIME NOT NULL,
    location      VARCHAR(255) NOT NULL
);

CREATE TABLE invitation_
(
    id         SERIAL PRIMARY KEY,
    guest_id   INT         NOT NULL REFERENCES user_ (id),
    event_id   INT         NOT NULL REFERENCES event_ (id),
    attendance attendance_ DEFAULT 'NO_RESPONSE'
);
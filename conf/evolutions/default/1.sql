-- Users schema

-- !Ups

CREATE TABLE users (
    id uuid NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    created_at date NOT NULL,
    updated_at date NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(username),
    UNIQUE(email)
);

-- !Downs

DROP TABLE users;
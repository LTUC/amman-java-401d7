CREATE TABLE roles (
                       id                bigserial not null,
                       title             VARCHAR(255),
                       description       VARCHAR(255),
                       primary key (id)
);


CREATE TABLE mst_users (
                       id bigserial not null,
                       full_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(255) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       user_name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role_id int8 constraint fk_role_id references roles not null,
                       primary key (id)
);




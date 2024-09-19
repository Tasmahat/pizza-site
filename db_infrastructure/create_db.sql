CREATE DATABASE Pizza;
CREATE TABLE images(
	id_img serial PRIMARY KEY,
	name_img varchar NOT NULL,
	size_img decimal NOT NULL,
	key_img varchar NOT NULL,
	date_upload_img date NOT NULL
);

CREATE TABLE pizzas(
    id_p serial PRIMARY KEY,
    name_p varchar(100) UNIQUE NOT NULL,
    cost_p decimal NOT NULL,
	id_img serial,
	FOREIGN KEY (id_img) REFERENCES images(id_img)
);

CREATE TABLE ingredients(
    id_i serial PRIMARY KEY,
    name_i varchar(100) UNIQUE NOT NULL
);

CREATE TABLE ingredients_in_pizzas(
    id_p serial,
    id_i serial,
    FOREIGN KEY (id_p) REFERENCES pizzas(id_p),
    FOREIGN KEY (id_i) REFERENCES ingredients(id_i),
    PRIMARY KEY (id_p, id_i)
);

CREATE TABLE users(
    id_u serial PRIMARY KEY,
    name_u varchar NOT NULL,
    email_u varchar NOT NULL,
    can_add boolean default false NOT NULL,
    can_delete boolean default false NOT NULL
);

INSERT INTO images VALUES
(0, 'default', 0, 'default', '01-01-2000');

INSERT INTO pizzas VALUES
(1, 'Peperoni', 100,0),
(2, 'Margaritha',100,0),
(3, 'Meaty', 200,0),
(4, 'Superpizza', 500,0),
(5, 'Havaian', 200,0);

INSERT INTO ingredients VALUES
(1, 'Cheese'),
(2, 'Sauce'),
(3, 'Peperoni'),
(4, 'Tomatoes'),
(5, 'Pineapples'),
(6, 'Ham');

INSERT INTO ingredients_in_pizzas VALUES
(1,1), (1,2), (1,3),
(2,1), (2,2), (2,4),
(3,1), (3,2), (3,3), (3,6),
(4,1), (4,2), (4,3), (4,4), (4,6),
(5,1), (5,2), (5,5), (5,6);

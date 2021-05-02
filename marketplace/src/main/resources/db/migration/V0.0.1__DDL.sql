CREATE TABLE localization (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	latitude float8 NULL,
	longitude float8 NULL,
	CONSTRAINT localizacao_pkey PRIMARY KEY (id)
);

CREATE TABLE restaurant (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	name varchar(50) NULL,
	localization_id int8 NOT NULL,
	CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);

ALTER TABLE restaurant ADD CONSTRAINT res_loc FOREIGN KEY (localization_id) REFERENCES localization(id);

CREATE TABLE dish (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(200) NULL,
	name varchar(50) NULL,
	price numeric(19,2) NOT NULL,
	restaurant_id int8 NOT NULL
);

ALTER TABLE dish ADD CONSTRAINT dish_rest FOREIGN KEY (restaurant_id) REFERENCES restaurant(id);


CREATE TABLE dish_customer (
	dish int,
	customer varchar(200)
);

INSERT INTO localization (id, latitude, longitude) VALUES(1000, -15.817759, -47.836959);

INSERT INTO restaurant (id, localization_id, name) VALUES(999, 1000, 'Manguai');

INSERT INTO dish
(id, name, description, restaurant_id, price)
VALUES(9998, 'Cuscuz com Ovo', 'Bom demais no café da manhã', 999, 3.99);

INSERT INTO dish
(id, name, description, restaurant_id, price)
VALUES(9997, 'Peixe frito', 'Agulhinha frita, excelente com Cerveja', 999, 99.99);
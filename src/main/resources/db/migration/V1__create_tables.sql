CREATE TABLE coffee_variety
(
    id                   BIGSERIAL NOT NULL PRIMARY KEY,
    name_of_variety      VARCHAR(255),
    price                INT,
    variety_availability BOOLEAN
);

CREATE TABLE orders
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    date        DATE,
    customer    VARCHAR(255),
    address     VARCHAR(255),
    total_price INT
);

CREATE TABLE order_item
(
    id     BIGSERIAL NOT NULL PRIMARY KEY,
    grade  VARCHAR(255),
    amount INT
);
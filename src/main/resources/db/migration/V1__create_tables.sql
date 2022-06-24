CREATE TABLE coffee_variety
(
    id                   BIGSERIAL    NOT NULL PRIMARY KEY,
    name_of_variety      VARCHAR(255),
    price                DECIMAL,
    variety_availability BOOLEAN
);

CREATE TABLE orders
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    date        TIMESTAMP WITHOUT TIME ZONE,
    customer    VARCHAR(255),
    address     VARCHAR(255),
    total_price DECIMAL
);

CREATE TABLE order_item
(
    id                BIGSERIAL NOT NULL PRIMARY KEY,
    coffee_variety_id BIGSERIAL,
    amount            INTEGER,
    order_id          BIGSERIAL,
    FOREIGN KEY (coffee_variety_id) REFERENCES coffee_variety (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
);
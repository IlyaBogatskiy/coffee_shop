CREATE TABLE coffee_variety
(
    id                   BIGSERIAL    NOT NULL PRIMARY KEY,
    name_of_variety      VARCHAR(255) NOT NULL,
    price                DECIMAL,
    variety_availability BOOLEAN      NOT NULL
);

CREATE TABLE orders
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    date        TIMESTAMP WITHOUT TIME ZONE,
    customer    VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    total_price DECIMAL
);

CREATE TABLE order_item
(
    id                BIGSERIAL NOT NULL PRIMARY KEY,
    coffee_variety_id BIGSERIAL NOT NULL,
    cups              INTEGER   NOT NULL,
    order_id          integer   NOT NULL,
    FOREIGN KEY (coffee_variety_id) REFERENCES coffee_variety (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
);
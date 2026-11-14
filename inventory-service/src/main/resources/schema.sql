CREATE TABLE IF NOT EXISTS products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(50) NOT NULL,
    price NUMERIC(10,2) NOT NULL check(price >=0)
);

CREATE TABLE IF NOT EXISTS stock (
    product_id BIGINT PRIMARY KEY references products(id) NOT NULL,
    available_qty INT check (available_qty >= 0)
);

CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id varchar(30) NOT NULL,
    product_id BIGINT NOT NULL references products(id),
    qty BIGINT NOT NULL check(qty >0),
    status varchar(20) NOT NULL DEFAULT 'RESERVED' CHECK (status IN ('RESERVED','RELEASED')),

    CONSTRAINT uq_reservation UNIQUE (order_id,product_id)
);



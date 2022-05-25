DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    product CHAR(50) NOT NULL,
    price INT NOT NULL,
    quantity INT NOT NULL
);

INSERT INTO
    orders (
        order_date,
        product,
        price,
        quantity
    )
VALUES
    ('2022-01-10', 'Acme Anvil', '9', '1'),
    ('2022-01-12', 'Hypercolor T-Shirt', '3', '1'),
    ('2022-01-17', 'Plastic Record Player', '7', '1'),
    ('2022-01-23', 'Ceramic Solo Cup', '1', '4'),
    ('2022-02-14', 'Gutteral Screen', '12', '1'),
    ('2022-02-18', 'Stuffed Shirt', '2', '2'),
    ('2022-02-22', 'Acme Anvil', '3', '1'),
    ('2022-02-26', 'Backwards Bicycle', '16', '1'),
    ('2022-02-28', 'Gutteral Screen', '12', '1'),
    ('2022-03-01', 'Acme Anvil', '3', '3'),
    ('2022-03-03', 'Ceramic Solo Cup', '1', '1'),
    ('2022-03-09', 'Plastic Record Player', '7', '1'),
    ('2022-03-13', 'Acme Anvil', '3', '6'),
    ('2022-03-20', 'Stuffed Shirt', '2', '1'),
    ('2022-03-30', 'Acme Anvil', '3', '2'),
    ('2022-04-08', 'Hypercolor T-Shirt', '3', '1'),
    ('2022-04-08', 'Backwards Bicycle', '16', '1'),
    ('2022-04-10', 'Ceramic Solo Cup', '1', '1'),
    ('2022-04-13', 'Acme Anvil', '9', '3'),
    ('2022-04-18', 'Ceramic Solo Cup', '1', '1'),
    ('2022-04-26', 'Acme Anvil', '3', '1'),
    ('2022-05-02', 'Hypercolor T-Shirt', '3', '2'),
    ('2022-05-04', 'Gutteral Screen', '12', '1'),
    ('2022-05-07', 'Backwards Bicycle', '16', '2'),
    ('2022-05-11', 'Plastic Record Player', '7', '1'),
    ('2022-05-17', 'Acme Anvil', '3', '1');
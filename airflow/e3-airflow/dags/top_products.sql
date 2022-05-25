DROP TABLE IF EXISTS top_products;

CREATE TABLE top_products AS
SELECT
    product,
    COUNT(order_id) AS num_orders,
    SUM(quantity) AS total_quantity,
    SUM(price * quantity) AS total_value
FROM
    orders
GROUP BY
    product
ORDER BY
    total_value desc,
    num_orders desc;
DROP TABLE IF EXISTS top_products;

CREATE TABLE top_products AS
    SELECT
        product,
        count(order_id) as num_orders,
        sum(quantity) as total_quantity,
        sum(price * quantity) as total_value
    FROM orders
    group by product
    order by total_value desc, num_orders desc;
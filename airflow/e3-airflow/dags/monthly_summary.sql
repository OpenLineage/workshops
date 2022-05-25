DROP TABLE IF EXISTS monthly_summary;

CREATE TABLE monthly_summary AS
    SELECT
        DATE_TRUNC('month', order_date) AS month,
        product,
        count(order_id) as num_orders,
        sum(quantity) as total_quantity,
        sum(price * quantity) as total_value
    FROM orders
    group by month, product;
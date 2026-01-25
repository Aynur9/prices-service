CREATE TABLE prices (
    brand_id BIGINT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INT,
    product_id BIGINT,
    priority INT,
    price DECIMAL(10,2),
    curr VARCHAR(3)
);

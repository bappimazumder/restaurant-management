-- Customer ID Sequence creation
CREATE SEQUENCE IF NOT EXISTS customers_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY customers.id;

-- Create Table Customers
CREATE TABLE IF NOT EXISTS customers
(
    id bigint NOT NULL DEFAULT nextval('customers_id_seq'::regclass),
    code character varying(255),
    full_name character varying(255),
    CONSTRAINT customers_pkey PRIMARY KEY (id),
    CONSTRAINT uk_mkwx1x9mthieapj92cpxq5msc UNIQUE (code)
    );

-- Order ID Sequence creation
CREATE SEQUENCE IF NOT EXISTS orders_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY orders.id;

-- Create Table orders
CREATE TABLE IF NOT EXISTS orders
(
    id bigint NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    amount double precision,
    code character varying(255),
    order_date date,
    customer_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT uk_gt3o4a5bqj59e9y6wakgk926t UNIQUE (code),
    CONSTRAINT fkpxtb8awmi0dk6smoh2vp1litg FOREIGN KEY (customer_id)
    REFERENCES public.customers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );
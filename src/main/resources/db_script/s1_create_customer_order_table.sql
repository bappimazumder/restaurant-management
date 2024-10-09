-- Customer ID Sequence creation
CREATE SEQUENCE IF NOT EXISTS customers_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Create Table Customers
CREATE TABLE IF NOT EXISTS customers
(
    id bigint NOT NULL DEFAULT nextval('customers_id_seq'::regclass),
    code character varying(255),
    full_name character varying(255),
    CONSTRAINT customers_pkey PRIMARY KEY (id),
    CONSTRAINT uk_customers_code UNIQUE (code)
    );

-- Order ID Sequence creation
CREATE SEQUENCE IF NOT EXISTS orders_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Create Table orders
CREATE TABLE IF NOT EXISTS orders
(
    id bigint NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    amount double precision,
    code character varying(255),
    order_date date,
    sale_date date,
    customer_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT uk_order_code UNIQUE (code),
    CONSTRAINT fk_order_customer_id FOREIGN KEY (customer_id)
    REFERENCES public.customers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );
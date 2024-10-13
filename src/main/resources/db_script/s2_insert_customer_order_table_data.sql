-- Data For Customer Table
INSERT INTO customers(code, full_name)
VALUES ('C-001', 'Bappi Mazumder'),
       ('C-002', 'David Balame'),
       ('C-003', 'Luis Henry');

-- Data for Order Table
INSERT INTO orders( amount, code, order_date, sale_date,customer_id)
VALUES (500,'ORD-001','2024-10-13','2024-10-13',(SELECT ID FROM customers WHERE CODE = 'C-001'))
     ,(600, 'ORD-002','2024-10-14','2024-10-14',(SELECT ID FROM customers WHERE CODE = 'C-002'))
     ,(800, 'ORD-003','2024-10-15','2024-10-15',(SELECT ID FROM customers WHERE CODE = 'C-003'))
     ,(700, 'ORD-004','2024-10-16','2024-10-17',(SELECT ID FROM customers WHERE CODE = 'C-003'));
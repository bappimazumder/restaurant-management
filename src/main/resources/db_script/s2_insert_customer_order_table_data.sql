-- Data For Customer Table
INSERT INTO customers(code, full_name)
VALUES ('C-001', 'Bappi Mazumder'),
       ('C-002', 'David Balame'),
       ('C-003', 'Luis Henry');

-- Data for Order Table
INSERT INTO orders( amount, code, order_date, sale_date,customer_id)
VALUES (500, 'ORD-001', '2024-10-03','2024-10-03' ,1)
     ,(600, 'ORD-002', '2024-10-04','2024-10-04' ,2)
     ,(800, 'ORD-003', '2024-10-05','2024-10-05' ,3)
     ,(700, 'ORD-004', '2024-10-05','2024-10-05',3);
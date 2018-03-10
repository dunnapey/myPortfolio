CONNECT salon/salon;

-- Open log file.
SPOOL salon_insert.log

-- insert LOCATION data
INSERT INTO location
VALUES
(
    location_s1.nextval,
    '100 N Main St.',
    'Suite 3',
    'Rexburg',
    'ID',
    '83441'
);

INSERT INTO location
VALUES
(
    location_s1.nextval,
    '200 N Center St.',
    NULL,
    'Rigby',
    'ID',
    '83442'
);

-- insert CUSTOMER data
INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Jeana Bradford',
    'jb@gmail.com',
    '1112223333'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Sandra Hobbs',
    'sh@gmail.com',
    '1112223334'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Ryan Taylor',
    'rt@gmail.com',
    '1112223335'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Michelle Drake',
    'md@gmail.com',
    '1112223336'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Heidi Nolan',
    'hn@gmail.com',
    '1112223337'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Karen Smith',
    'ks@gmail.com',
    '1112223338'
);

INSERT INTO customer
VALUES
(
    CUSTOMER_S1.NEXTVAL,
    'Jose Mendez',
    'jm@gmail.com',
    '1112223339'
);

-- insert EMPLOYEE data
INSERT INTO employee
VALUES
(
    employee_s1.nextval,
    'Jenny Jensen',
    'Owner',
    '15-JUN-13',
    NULL,
    'jensenj@salon.com',
    '2081112222',
    1,
    NULL
);

INSERT INTO employee
VALUES
(
    employee_s1.nextval,
    'Haley Lopez',
    'Assistant Manager',
    '23-AUG-13',
    NULL,
    'lopezh@salon.com',
    '2083334444',
    1,
    (SELECT employee_id FROM employee WHERE title = 'Owner')
);

INSERT INTO employee
VALUES
(
    employee_s1.nextval,
    'Robert Green',
    'Associate',
    '01-MAR-14',
    NULL,
    'greenr@salon.com',
    '2085556666',
    1,
    (SELECT employee_id FROM employee WHERE title = 'Assistant Manager')
);

INSERT INTO employee
VALUES
(
    employee_s1.nextval,
    'Olive Adams',
    'Manager',
    '12-JUL-15',
    NULL,
    'adamso@salon.com',
    '2087778888',
    2,
    (SELECT employee_id FROM employee WHERE title = 'Owner')
);

INSERT INTO employee
VALUES
(
    employee_s1.nextval,
    'Julie Davis',
    'Associate',
    '20-OCT-15',
    NULL,
    'davisj@salon.com',
    '2089990000',
    2,
    (SELECT employee_id FROM employee WHERE title = 'Manager')
);

-- insert PRODUCT data
INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Shampoo',
    'Product',
    '8 oz',
    4.95
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Shampoo',
    'Product',
    '16 oz',
    8.95
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Conditioner',
    'Product',
    '12 oz',
    8.95
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Hairspray',
    'Product',
    '8 oz',
    7.95
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Shampoo',
    'Service',
    NULL,
    8.00
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Men''s Hair Cut',
    'Service',
    NULL,
    15.00
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Women Hair Cut',
    'Service',
    NULL,
    25.00
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Color',
    'Service',
    NULL,
    50.00
);

INSERT INTO product
VALUES
(
    PRODUCT_S1.NEXTVAL,
    'Perm',
    'Service',
    NULL,
    60.00
);

-- insert TRAN data
INSERT INTO tran
VALUES
(
    TRAN_S1.NEXTVAL,
    '25-MAR-16',
    1,
    7
);

INSERT INTO tran
VALUES
(
    TRAN_S1.NEXTVAL,
    '25-MAR-16',
    1,
    6
);

INSERT INTO tran
VALUES
(
    TRAN_S1.NEXTVAL,
    '25-MAR-16',
    2,
    5
);

INSERT INTO tran
VALUES
(
    TRAN_S1.NEXTVAL,
    '25-MAR-16',
    3,
    4
);

INSERT INTO tran
VALUES
(
    TRAN_S1.NEXTVAL,
    '25-MAR-16',
    4,
    3
);

-- insert TRANDETAIL data
INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    1,
    1,
    7.95
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    1,
    6,
    15.00
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    2,
    9,
    60.00
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    2,
    3,
    8.95
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    3,
    8,
    50.00
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    4,
    7,
    25.00
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    4,
    2,
    8.95
);

INSERT INTO tran_detail
VALUES
(
    TRAN_DETAIL_S1.NEXTVAL,
    5,
    6,
    15.00
);

SPOOL OFF;
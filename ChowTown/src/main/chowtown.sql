DROP DATABASE IF EXISTS chowtown;
CREATE DATABASE chowtown;
USE chowtown;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
    id int PRIMARY KEY,
    username varchar(256) UNIQUE,
    password varchar(256),
    name varchar(256),
    address int
);
INSERT INTO customers VALUES(0, "risa", "123", "Risa", 2);
INSERT INTO customers VALUES(1, "sumaiya", "123", "Sumaiya", 1);
INSERT INTO customers VALUES(2, "mahin", "123", "Mahin", 2);
INSERT INTO customers VALUES(3, "wilson", "123", "Wilson", 0);

DROP TABLE IF EXISTS managers;
CREATE TABLE managers(
    rest_id int PRIMARY KEY,
    username varchar(256) UNIQUE,
    password varchar(256),
    name varchar(256)
);
INSERT INTO managers VALUES(0, "manager1", "123", "M1");
INSERT INTO managers VALUES(1, "manager2", "123", "M2");
INSERT INTO managers VALUES(2, "manager3", "123", "M3");

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(
    id int PRIMARY KEY,
    rest_id int,
    username varchar(32) UNIQUE,
    password varchar(32),
    name varchar(32),
    job_title int,
    salary double,
    avg_rating double,
    num_rated int,
    warning int,
    FOREIGN KEY (rest_id) REFERENCES managers(rest_id) ON DELETE CASCADE
);
INSERT INTO employees VALUES(0, 0, "cook1", "123", "C1", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(1, 0, "cook2", "123", "C2", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(2, 1, "cook3", "123", "C3", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(3, 1, "cook4", "123", "C4", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(4, 2, "cook5", "123", "C5", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(5, 2, "cook6", "123", "C6", 0, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(6, 0, "deli1", "123", "D1", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(7, 0, "deli2", "123", "D2", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(8, 1, "deli3", "123", "D3", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(9, 1, "deli4", "123", "D4", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(10, 2, "deli5", "123", "D5", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(11, 2, "deli6", "123", "D6", 1, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(12, 0, "sale1", "123", "S1", 2, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(13, 0, "sale2", "123", "S2", 2, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(14, 1, "sale3", "123", "S3", 2, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(15, 1, "sale4", "123", "S4", 2, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(16, 2, "sale5", "123", "S5", 2, 15.0, 5, 1, 0);
INSERT INTO employees VALUES(17, 2, "sale6", "123", "S6", 2, 15.0, 5, 1, 0);

DROP TABLE IF EXISTS supplies;
CREATE TABLE supplies(
    id int PRIMARY KEY,
    empl_id int,
    product_name varchar(256),
    price double,
    FOREIGN KEY (empl_id) REFERENCES employees(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
    order_id int PRIMARY KEY,
    cust_id int,
    rest_id int, 
    cook_id int,
    deli_id int,
    deli_rate double,
    FOREIGN KEY (cust_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (cook_id) REFERENCES employees(id) ON DELETE CASCADE,
    FOREIGN KEY (deli_id) REFERENCES employees(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS orderhistory;
CREATE TABLE orderhistory(
    order_id int,
    item varchar(256),
    rate double,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS customerratings;
CREATE TABLE customerratings(
    cust_id int,
    rest_id int,
    avg_rating double,
    num_rated int,
    status int,
    FOREIGN KEY (cust_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (rest_id) REFERENCES managers(rest_id) ON DELETE CASCADE
);
INSERT INTO customerratings VALUES(0, 0, 5, 1, 1);
INSERT INTO customerratings VALUES(0, 1, 5, 1, 1);
INSERT INTO customerratings VALUES(0, 2, 5, 1, 1);
INSERT INTO customerratings VALUES(1, 0, 5, 1, 1);
INSERT INTO customerratings VALUES(1, 1, 5, 1, 1);
INSERT INTO customerratings VALUES(1, 2, 5, 1, 1);
INSERT INTO customerratings VALUES(2, 0, 5, 1, 1);
INSERT INTO customerratings VALUES(2, 1, 5, 1, 1);
INSERT INTO customerratings VALUES(2, 2, 5, 1, 1);
INSERT INTO customerratings VALUES(3, 0, 5, 1, 1);
INSERT INTO customerratings VALUES(3, 1, 5, 1, 1);
INSERT INTO customerratings VALUES(3, 2, 5, 1, 1);

DROP TABLE IF EXISTS menu;
CREATE TABLE menu(
    rest_id int,
    item varchar(256) UNIQUE,
    price double,
    avg_rating double,
    num_ordered int,
    FOREIGN KEY (rest_id) REFERENCES managers(rest_id) ON DELETE CASCADE
);

INSERT INTO menu VALUES (0, "Fried Dumplings(10)", 5.00, 4.6, 1), (0, "Egg Roll(2)", 3.00, 3.0, 1), (0, "Hot & Sour Soup", 3.00, 4.0, 1), 
(0, "Wonton Soup", 4.50, 5.0, 1), (0, "Beef Fried Rice", 9.50, 4, 1), (0, "Chicken Chow Mein", 8.50, 4.8, 1), 
(0, "Can Soda", 2.00, 4.2, 1);
INSERT INTO menu VALUES (1, "Gyoza(6)", 6.00, 3.8, 1), (1, "Tempura(6)", 8.00, 5.0, 1), (1, "Miso Soup", 3.00, 4.4, 1), 
(1, "Chicken Katsu-Don", 12.00, 3.4, 1), (1, "Ramen", 15.50, 4.9, 1), (1, "Sushi(12)", 13.00, 4.3, 1), (1, "Green Tea", 3.00, 3.2, 1), 
(1, "Bottle Water", 1.50, 3.5, 1);
INSERT INTO menu VALUES (2, "Samosa(5)", 5.00, 3.2, 1), (2, "Naan(2)", 2.00, 4.4, 1), (2, "Roti(2)", 2.00, 4.7, 1), 
(2, "Chicken Tikka Masaka", 10.00, 4.6, 1), (2, "Saag Paneer", 12.50, 4.9, 1), (2, "Biryani", 11.00, 3.9, 1), (2, "Chai", 3.00, 4.0, 1), 
(2, "Lassi", 2.50, 3.8, 1);
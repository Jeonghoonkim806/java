CREATE TABLE hotels (
    hotel_id        NUMBER         PRIMARY KEY,
    hotel_name      VARCHAR2(100)  NOT NULL,
    location        VARCHAR2(100),
    rating          NUMBER(2,1),
    total_rooms     NUMBER,
    available_rooms NUMBER
);

CREATE TABLE customers (
    customer_id     NUMBER         PRIMARY KEY,
    customer_name   VARCHAR2(100)  NOT NULL,
    email           VARCHAR2(100),
    phone_number    VARCHAR2(20)
);

CREATE TABLE reservations (
    reservation_id  NUMBER         PRIMARY KEY,
    hotel_id        NUMBER         REFERENCES hotels(hotel_id),
    customer_id     NUMBER         REFERENCES customers(customer_id),
    guest_name      VARCHAR2(100)  NOT NULL,
    room_number     NUMBER,
    check_in_date   DATE,
    check_out_date  DATE
);
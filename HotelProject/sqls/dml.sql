INSERT INTO reservations (reservation_id, hotel_id, customer_id, check_in_date, check_out_date)
VALUES (reservation_seq.NEXTVAL, ?, ?, ?, ?);

SELECT hotel_id, hotel_name, location, rating, available_rooms
FROM hotels;

SELECT customer_id, customer_name, email, phone_number
FROM customers;

SELECT hotel_id, hotel_name, available_rooms
FROM hotels
WHERE available_rooms > 0;

SELECT r.reservation_id, h.hotel_name, r.check_in_date, r.check_out_date
FROM reservations r
JOIN hotels h ON r.hotel_id = h.hotel_id
WHERE r.customer_id = ?;

DELETE FROM reservations
WHERE reservation_id = ?;
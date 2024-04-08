INSERT INTO users (id, email, password, first_name, last_name)
VALUES (2, 'roman@gmail.com', '12345678', 'Roman', 'Soproniuk');
INSERT INTO books (id, title, author, price, isbn, description, cover_image)
VALUES (1, 'Mavka', 'Lesya', 50, '12345678', 'Cool', 'Image1');
INSERT INTO shopping_carts (id, users_id) VALUES (2, 2);
INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (1, 2, 1, 5);

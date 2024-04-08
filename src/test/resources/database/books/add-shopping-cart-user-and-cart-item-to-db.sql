INSERT INTO users (id, email, password, first_name, last_name)
VALUES (2, 'roman@gmail.com', '$2a$10$BOaxBAnY9cPRNt6P/gDHjOqJGqzLAHVSKoC3m6JRPsWX3AnK4zEBe',
        'Roman', 'Soproniuk');
INSERT INTO shopping_carts(id, users_id) VALUES (2, 2);
INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (1, 2, 1, 5);

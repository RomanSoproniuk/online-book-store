DELETE FROM users WHERE email = 'roman@gmail.com' AND first_name = 'Roman';
DELETE FROM shopping_carts WHERE id = 2 AND users_id = 2;
DELETE FROM books WHERE title = 'Mavka' AND author = 'Lesya';
DELETE FROM cart_items WHERE shopping_cart_id = 2 AND book_id = 1;

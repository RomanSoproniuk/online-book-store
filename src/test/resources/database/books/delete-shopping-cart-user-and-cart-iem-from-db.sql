DELETE FROM users WHERE email = 'roman@gmail.com' AND first_name = 'Roman';
DELETE FROM shopping_carts WHERE id = 2 AND users_id = 1;
DELETE FROM cart_items WHERE shopping_cart_id = 1 AND quantity = 5;

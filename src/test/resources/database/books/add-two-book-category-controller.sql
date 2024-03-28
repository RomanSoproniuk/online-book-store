INSERT INTO books (id, title, author, price, isbn, description, cover_image)
VALUES (1, 'Mavka', 'Lesya', 50, '12345678', 'Cool', 'Image1');
INSERT INTO books (id, title, author, price, isbn, description, cover_image)
VALUES (2, 'Kobzar', 'Taras', 50, '87654321', 'Super', 'Image2');
INSERT INTO categories (id, name, description) VALUES (1, 'UkrLit', 'Ukr');
INSERT INTO books_categories (books_id, categories_id) VALUES (1, 1);
INSERT INTO books_categories (books_id, categories_id) VALUES (2, 1);

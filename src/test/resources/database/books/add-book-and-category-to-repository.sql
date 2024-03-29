TRUNCATE TABLE books;
TRUNCATE TABLE categories;
TRUNCATE TABLE books_categories;
INSERT INTO books (id, title, author, price, isbn)
VALUES (1, 'Kobzar', 'Shevchenko', 50, '3ih3348wjew');
INSERT INTO categories(id, name) VALUES (1, 'UkrLiteratura');
INSERT INTO books_categories(books_id, categories_id) VALUES (1, 1);

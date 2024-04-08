DELETE FROM books WHERE title = 'Mavka' AND author = 'Lesya';
DELETE FROM books WHERE title = 'Kobzar' AND author = 'Taras';
DELETE FROM categories WHERE name = 'UkrLit' AND description = 'Ukr';
DELETE FROM books_categories WHERE books_id = 1 AND categories_id = 1;
DELETE FROM books_categories WHERE books_id = 2 AND categories_id = 1;
